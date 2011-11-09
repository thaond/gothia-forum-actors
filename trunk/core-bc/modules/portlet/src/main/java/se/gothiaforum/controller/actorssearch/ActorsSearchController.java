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

package se.gothiaforum.controller.actorssearch;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import se.gothiaforum.actorsarticle.domain.model.ActorArticle;
import se.gothiaforum.actorsarticle.util.ActorsConstants;
import se.gothiaforum.settings.service.SettingsService;
import se.gothiaforum.settings.util.ExpandoConstants;
import se.gothiaforum.solr.ActroSolrQuery;
import se.gothiaforum.util.Constants;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalService;

/**
 * This controller class performs a search for actors.
 * 
 * @author simgo3
 */

@Controller
@RequestMapping(value = "VIEW")
public class ActorsSearchController {

    private static Log log = LogFactory.getLog(ActorsSearchController.class);
    private static final int SEARCH_ROWS = 100;

    @Autowired
    private JournalArticleLocalService articleService;

    @Autowired
    private GroupLocalService groupService;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private ActroSolrQuery actroSolrQuery;

    /**
     * This render renders the view of the portlet. It's also creates the search query and performs the search for
     * actors.
     * 
     * @param request
     *            the request
     * @param response
     *            the response
     * @param model
     *            the model
     * @return the search view.
     */
    @RenderMapping
    public String showFormView(RenderRequest request, RenderResponse response, Model model) {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

        long companyId = themeDisplay.getCompanyId();
        long groupId = themeDisplay.getScopeGroupId();

        String searchTerm = request.getParameter("searchTerm");
        String viewAll = request.getParameter("viewAll");

        if (searchTerm != null || viewAll != null) {

            if (viewAll != null) {
                actroSolrQuery.findAllActorQuery();
                actroSolrQuery.setRows(SEARCH_ROWS);
            } else if (searchTerm != null) {
                actroSolrQuery.actorQuery(searchTerm.toLowerCase(Constants.LOCALE));
                actroSolrQuery.setStart(0);
                actroSolrQuery.setRows(SEARCH_ROWS);
            }

            try {
                actroSolrQuery.filterActors();

                QueryResponse queryResponse = actroSolrQuery.query();

                List<ActorArticle> actorArticles = new ArrayList<ActorArticle>();
                Iterator<SolrDocument> resultIter = null;
                if (queryResponse != null) {
                    if (queryResponse.getResults() != null) {

                        resultIter = queryResponse.getResults().iterator();

                        while (resultIter.hasNext()) {
                            SolrDocument doc = resultIter.next();
                            ActorArticle actorArticle = new ActorArticle();
                            actorArticle.setArticleId((String) doc
                                    .getFieldValue(ActorsConstants.ACTORS_ARTICLE_PK));

                            if (doc.getFieldValue(ActorsConstants.GROUP_ID) != null) {
                                actorArticle.setGroupId(Long.valueOf((String) doc
                                        .getFieldValue(ActorsConstants.GROUP_ID)));
                            }

                            // In case of faulty solr index take care of not adding incorrect articles.
                            try {
                                JournalArticle journalArticle = articleService.getArticle(
                                        actorArticle.getGroupId(), actorArticle.getArticleId());
                                actorArticle.setContent(articleService.getArticleContent(journalArticle,
                                        ActorsConstants.GLOBAL_LIST_TEMPLATEID, null,
                                        themeDisplay.getLanguageId(), themeDisplay));

                                String namePrefix = groupService.getGroup(actorArticle.getGroupId())
                                        .getFriendlyURL();

                                actorArticle.setProfileURL(ActorsConstants.PROFILE_RIDERECT_URL
                                        + namePrefix.substring(1));

                                actorArticles.add(actorArticle);
                            } catch (NoSuchArticleException e) {
                                log.warn("Warning: " + e.getMessage() + "may reindex.");
                            }

                        }
                    }
                }

                if (actorArticles.size() == 0) {

                    try {
                        String searchNoHitsArticleId = settingsService.getSetting(
                                ExpandoConstants.GOTHIA_SEARCH_NO_HITS_ARTICLE, companyId, groupId);
                        String searchNoHitsArticleContent = articleService.getArticleContent(groupId,
                                searchNoHitsArticleId, null, themeDisplay.getLanguageId(), themeDisplay);
                        model.addAttribute("searchNoHitsArticleContent", searchNoHitsArticleContent);
                    } catch (PortalException e) {
                        log.info("no article for thin client search portlet found");
                    } catch (SystemException e) {
                        log.info("no article for thin client search portlet found");
                    }

                }

                model.addAttribute("hits", actorArticles);

            } catch (SearchException e) {
                throw new RuntimeException("TODO: Handle this exception better", e);
            } catch (PortalException e) {
                throw new RuntimeException("TODO: Handle this exception better", e);
            } catch (SystemException e) {
                throw new RuntimeException("TODO: Handle this exception better", e);
            }
        } else { // Fist time = no search made.

            try {
                String searchFirstTimeArticleId = settingsService.getSetting(
                        ExpandoConstants.GOTHIA_SEARCH_FITST_TIME_ARTICLE, companyId, groupId);
                String searchFirstTimeArticleContent = articleService.getArticleContent(groupId,
                        searchFirstTimeArticleId, null, themeDisplay.getLanguageId(), themeDisplay);
                model.addAttribute("searchFirstTimeArticleContent", searchFirstTimeArticleContent);
            } catch (PortalException e) {
                log.info("no article for thin client search portlet found");
            } catch (SystemException e) {
                log.info("no article for thin client search portlet found");
            }

        }

        // This is for pinking up the articles in the portlet.

        try {
            String searchArticleId = settingsService.getSetting(ExpandoConstants.GOTHIA_SEARCH_ARTICLE, companyId,
                    groupId);
            String searchArticleContent = articleService.getArticleContent(groupId, searchArticleId, null,
                    themeDisplay.getLanguageId(), themeDisplay);
            model.addAttribute("searchArticleContent", searchArticleContent);
        } catch (PortalException e) {
            log.info("no article for thin client search portlet found");
        } catch (SystemException e) {
            log.info("no article for thin client search portlet found");
        }

        return "searchView";
    }

    /**
     * This method receives an parameter (searchTerm) and just send it to the render using an public render
     * parameter. Where the render performs the search on that searchTerm.
     * 
     * @param request
     *            the request
     * @param response
     *            the response
     * @param sessionStatus
     *            the session status
     * @param searchTerm
     *            the search term
     * @param model
     *            the model
     */
    @ActionMapping(params = "action=search")
    public void search(ActionRequest request, ActionResponse response, SessionStatus sessionStatus,
            @RequestParam("searchTerm") String searchTerm, Model model) {

        try {
            String redirect = ActorsConstants.SEARCH_RIDERECT_URL
                    + URLEncoder.encode(searchTerm, ActorsConstants.UTF_8);
            response.sendRedirect(redirect);
        } catch (IOException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        }

    }

    /**
     * This method send a parameter to the render using an public render. That will performs a search that will
     * give back all actors in the search index.
     * 
     * @param request
     *            the request
     * @param response
     *            the response
     * @param sessionStatus
     *            the session status
     * @param model
     *            the model
     */
    @ActionMapping(params = "action=searchForAll")
    public void searchForAll(ActionRequest request, ActionResponse response, SessionStatus sessionStatus,
            Model model) {

        try {
            String redirect = ActorsConstants.SEARCH_RIDERECT_URL + "view-all";
            response.sendRedirect(redirect);
        } catch (IOException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        }

    }

    /**
     * This method providing the actors search portlet whit matching tags while auto completing. Uses the parameter
     * searchTerm to find tags and paring them to json objects for the java script.
     * 
     * @param searchFor
     *            the string the method is searching on.
     * @param request
     *            the request
     * @param response
     *            the response
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws PortletException
     *             the portlet exception
     */
    @ResourceMapping
    public void serveResource(@RequestParam("searchTerm") String searchFor, ResourceRequest request,
            ResourceResponse response) throws IOException, PortletException {

        try {

            List<AssetTag> matchingTags = getMatchingTags(searchFor);

            JSONArray jsonResult = com.liferay.portal.kernel.json.JSONFactoryUtil.createJSONArray();

            for (AssetTag tag : matchingTags) {
                JSONObject jsonRow = JSONFactoryUtil.createJSONObject();
                jsonRow.put("key", tag.getTagId());
                jsonRow.put("name", tag.getName());
                jsonResult.put(jsonRow);

            }

            JSONObject jsonResponse = JSONFactoryUtil.createJSONObject();
            jsonResponse.put("results", jsonResult);

            response.getWriter().append(jsonResponse.toString());

            response.setContentType("application/json");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * This is an help method to serveResource it helps by providing matching tags to the current search term.
     * Returns a list of tags.
     */
    private List<AssetTag> getMatchingTags(String searchWord) {

        ArrayList<AssetTag> emptyList = new ArrayList<AssetTag>();
        List<AssetTag> tagsList = ListUtil.fromCollection(emptyList);

        DynamicQuery dq = DynamicQueryFactoryUtil.forClass(AssetTag.class, PortalClassLoaderUtil.getClassLoader());

        Criterion searchTermCriterion = RestrictionsFactoryUtil.ilike("name", searchWord + "%");

        dq.add(searchTermCriterion);

        try {
            tagsList = AssetTagLocalServiceUtil.dynamicQuery(dq);

            log.info("Number of matching tags: " + tagsList.size());

        } catch (SystemException e) {
            log.error(e, e);
        }

        return tagsList;
    }
}
