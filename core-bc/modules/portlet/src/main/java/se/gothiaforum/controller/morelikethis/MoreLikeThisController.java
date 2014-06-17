/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 *
 */

package se.gothiaforum.controller.morelikethis;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.StringQueryImpl;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.gothiaforum.actorsarticle.domain.model.ActorArticle;
import se.gothiaforum.actorsarticle.util.ActorsConstants;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the controller for the more like this portlet for actor presentations.
 * 
 * @author simgo3
 */

@Controller
@RequestMapping("VIEW")
public class MoreLikeThisController {
    private static final Logger LOG = LoggerFactory.getLogger(MoreLikeThisController.class);
    private static final int MORE_LIKE_THIS_SIZE = 5;

    @Autowired
    private AssetEntryLocalService assetEntryService;

    @Autowired
    private JournalArticleLocalService articleService;

    @Autowired
    private GroupLocalService groupService;

    /**
     * The main render for the more like this portlet for providing actor that reminds of the actor the user
     * currently viewing.
     * 
     * @param request
     *            the request
     * @param model
     *            the model
     * @return the more like this view.
     */
    @RenderMapping
    public String showMoreLikeThis(RenderRequest request, Model model) {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        PortletPreferences prefs = request.getPreferences();
        String tagsStr = prefs.getValue("tags", "tags");

        StringQueryImpl query = new StringQueryImpl("");

        if (!tagsStr.equals("")) {

            String searchTags = tagsStr.replaceAll(",", " ");

            query =
                    new StringQueryImpl(
                            "(assetTagNames:"
                                    + searchTags
                                    + ") AND (entryClassName:com.liferay.portlet.journal.model.JournalArticle AND type:"
                                    + ActorsConstants.TYPE_ACTOR + ") sort=title asc");

        } else {
            String friendlyURL = request.getParameter("friendlyURL");
            if (friendlyURL != null) {

                Group group;
                try {
                    group =
                            GroupLocalServiceUtil.getFriendlyURLGroup(themeDisplay.getCompanyId(), "/"
                                    + friendlyURL);
                    JournalArticle journalArticle = null;
                    List<JournalArticle> articles;

                    articles = articleService.getArticles();

                    // Find the Journal Article for the current group.
                    for (JournalArticle jA : articles) {
                        if (jA.getGroupId() == group.getGroupId()
                                && jA.getType().equals(ActorsConstants.TYPE_ACTOR)) {
                            journalArticle = jA;
                        }
                    }

                    List<AssetTag> tags =
                            getTags(Long.valueOf(group.getGroupId()), journalArticle.getArticleId());

                    if (!tags.isEmpty()) {

                        StringBuffer searchTags = new StringBuffer("");
                        for (AssetTag t : tags) {
                            searchTags.append(t.getName() + " ");
                        }

                        query =
                                new StringQueryImpl(
                                        "(assetTagNames:"
                                                + searchTags
                                                + ") AND (entryClassName:com.liferay.portlet.journal.model.JournalArticle"
                                                + " AND type:" + ActorsConstants.TYPE_ACTOR
                                                + ") AND !(articleId:" + journalArticle.getArticleId()
                                                + ") sort=title asc");

                    }

                } catch (NoSuchGroupException e1) {
                    LOG.warn("No such group found");
                } catch (PortalException e) {
                    e.printStackTrace();
                } catch (SystemException e) {
                    e.printStackTrace();
                }

            }

        }

        // Search for more like this.
        List<ActorArticle> actorArticles = search(themeDisplay, query);

        model.addAttribute("hitsIsNotEmpty", !actorArticles.isEmpty());
        model.addAttribute("hits", actorArticles);

        return "moreLikeThisView";

    }

    private List<ActorArticle> search(ThemeDisplay themeDisplay, StringQueryImpl query) {

        long companyId = themeDisplay.getCompanyId();

        int start = 0;
        int end = MORE_LIKE_THIS_SIZE;

        List<ActorArticle> actorArticles = new ArrayList<ActorArticle>();

        try {
            // Searching for more like this actors articles
            Hits hits = SearchEngineUtil.search(companyId, query, start, end);

            // Populate the actorsArticles object (which we return) with the
            // content from the hits object
            if (hits.getDocs() != null) {

                for (Document d : hits.getDocs()) {
                    ActorArticle actorArticle = new ActorArticle();
                    actorArticle.setArticleId(d.get(ActorsConstants.ACTORS_ARTICLE_PK));
                    actorArticle.setGroupId(Long.valueOf(d.get(ActorsConstants.GROUP_ID)));
                    JournalArticle tempJournalArticle =
                            articleService.getArticle(actorArticle.getGroupId(), actorArticle.getArticleId());

                    String content =
                            articleService.getArticleContent(tempJournalArticle,
                                    ActorsConstants.GLOBAL_MORE_LIKE_THIS_TEMPLATEID, null,
                                    themeDisplay.getLanguageId(), themeDisplay);

                    actorArticle.setContent(content);
                    String namePrefix = groupService.getGroup(actorArticle.getGroupId()).getFriendlyURL();
                    actorArticle
                            .setProfileURL(ActorsConstants.PROFILE_REDIRECT_URL + namePrefix.substring(1));
                    actorArticles.add(actorArticle);
                }
            }
        } catch (PortalException e1) {
            throw new RuntimeException("An error occurred when attempt to search was performed.", e1);
        } catch (SystemException e1) {
            throw new RuntimeException("An error occurred when attempt to search was performed.", e1);
        }
        return actorArticles;
    }

    /*
     * A help method to the showMoreLikeThis method, It returns the assettags to an article in parameters is group
     * id and article id for identifying the tags.
     */
    private List<AssetTag> getTags(long groupId, String articleId) {

        List<AssetTag> tags = null;

        try {
            long classPK = articleService.getArticle(groupId, articleId).getResourcePrimKey();
            AssetEntry assetEntry = assetEntryService.getEntry(JournalArticle.class.getName(), classPK);
            tags = assetEntry.getTags();

        } catch (PortalException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }

        return tags;

    }

}
