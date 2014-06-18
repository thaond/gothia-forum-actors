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

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.service.JournalArticleLocalService;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import se.gothiaforum.actorsarticle.domain.model.Tag;
import se.gothiaforum.actorsarticle.util.ActorsConstants;
import se.gothiaforum.settings.service.SettingsService;
import se.gothiaforum.settings.util.ExpandoConstants;
import se.gothiaforum.solr.ActroSolrQuery;
import se.gothiaforum.util.Constants;
import se.gothiaforum.util.model.PageIterator;

import javax.portlet.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This controller class performs a search for actors.
 * 
 * @author simgo3
 */

@Controller
@RequestMapping(value = "VIEW")
public class ActorsSearchController {

    private static Logger log = LoggerFactory.getLogger(ActorsSearchController.class);
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

        String isViewAll = "false";

        if (searchTerm == null) {
            actroSolrQuery.findAllActorQuery();
            actroSolrQuery.setRows(SEARCH_ROWS);
            isViewAll = "true";
        } else {
            actroSolrQuery.actorQuery(searchTerm.toLowerCase(Constants.LOCALE));
            actroSolrQuery.setStart(0);
            actroSolrQuery.setRows(SEARCH_ROWS);
        }

        request.setAttribute("isViewAll", isViewAll);

        try {
            actroSolrQuery.filterActors();

            QueryResponse queryResponse = actroSolrQuery.query();

            List<ActorArticle> actorArticles = new ArrayList<ActorArticle>();
            Iterator<SolrDocument> resultIter = null;
            if (queryResponse != null) {
                if (queryResponse.getResults() != null) {

                    resultIter = queryResponse.getResults().iterator();

                    while (resultIter.hasNext()) {
                        SolrDocument solrArticleEntry = resultIter.next();
                        ActorArticle actorArticle = new ActorArticle();

                        String actorsArticlePk = extractFieldValue(solrArticleEntry, ActorsConstants.ACTORS_ARTICLE_PK);
                        actorArticle.setArticleId(actorsArticlePk);

                        if (solrArticleEntry.getFieldValue(ActorsConstants.GROUP_ID) != null) {
                            actorArticle.setGroupId(Long.valueOf(extractFieldValue(solrArticleEntry, ActorsConstants.GROUP_ID)));
                        }

                        // In case of faulty solr index take care of not adding incorrect articles.
                        try {
                            setArticleContentAndTitle(themeDisplay, queryResponse, solrArticleEntry, actorArticle);
                            
                            long articleGroupId = actorArticle.getGroupId();
                            
                            try {
                            	Group actorGroup = groupService.getGroup(articleGroupId);
                            	
                                String namePrefix = actorGroup.getFriendlyURL();

                                actorArticle.setProfileURL(ActorsConstants.PROFILE_REDIRECT_URL
                                        + namePrefix.substring(1));

                                actorArticles.add(actorArticle);
                            } catch (NoSuchGroupException nsge) {
                            	// Do nothing for now
                            }

                        } catch (NoSuchArticleException e) {
                            log.warn("Warning: " + e.getMessage() + "may reindex.");
                        }

                    }
                }
            }

            if (actorArticles.size() == 0) {

                try {
                    String searchNoHitsArticleId =
                            settingsService.getSetting(ExpandoConstants.GOTHIA_SEARCH_NO_HITS_ARTICLE,
                                    companyId, groupId);
                    String searchNoHitsArticleContent =
                            articleService.getArticleContent(groupId, searchNoHitsArticleId, null,
                                    themeDisplay.getLanguageId(), themeDisplay);
                    model.addAttribute("searchNoHitsArticleContent", searchNoHitsArticleContent);
                } catch (PortalException e) {
                    log.warn("no article for thin client search portlet found", e);
                } catch (SystemException e) {
                    log.warn("no article for thin client search portlet found", e);
                }

            }

            //
            // Paginator
            //
            PortletPreferences prefs = request.getPreferences();

            int entryCount = Integer.valueOf(prefs.getValue("numberOfHitsToShow", "3"));
            int currentPage = ParamUtil.getInteger(request, "pageNumber", 1);
            int start = (currentPage - 1) * entryCount;
            int end = currentPage * entryCount;

            if (end > actorArticles.size()) {
                end = actorArticles.size();
            }

            List<ActorArticle> sublist = actorArticles.subList(start, end);

            setupTags(sublist);

            model.addAttribute("hits", sublist);

            PageIterator pageIterator = new PageIterator(actorArticles.size(), currentPage, entryCount, 10);
            request.setAttribute("pageIterator", pageIterator);

        } catch (Exception e) {
            throw new RuntimeException("An error occurred when the search was attempted to performed", e);
        }

        // This is for picking up the articles in the portlet.

        try {
            String searchArticleId =
                    settingsService.getSetting(ExpandoConstants.GOTHIA_SEARCH_ARTICLE, companyId, groupId);
            String searchArticleContent =
                    articleService.getArticleContent(groupId, searchArticleId, null,
                            themeDisplay.getLanguageId(), themeDisplay);
            model.addAttribute("searchArticleContent", searchArticleContent);
        } catch (Exception e) {
            log.info("no article for thin client search portlet found");
        }

        return "searchView";
    }

    private String extractFieldValue(SolrDocument solrArticleEntry, String fieldName) {
        Object fieldValue = solrArticleEntry.getFieldValue(fieldName);
        if (fieldValue instanceof String) {
            return (String) fieldValue;
        } else if (fieldValue instanceof List) {
            List list = (List) fieldValue;
            if (list.size() != 1) {
                throw new IllegalStateException("Unexpected size. Expected 1. Got " + list.size());
            }
            return (String) list.get(0);
        } else {
            throw new IllegalStateException("Unexpected type. Expected String or List. Got " + fieldValue.getClass().getName());
        }
    }

    private void setupTags(List<ActorArticle> sublist) {
        for (ActorArticle actorArticle : sublist) {

            ArrayList<Tag> tagsList = new ArrayList<Tag>();
            String[] tags = actorArticle.getTagsStr().split(",");

            for (String tagStr : tags) {
                Tag tag = new Tag();
                tag.setName(tagStr);
                tag.setLink(ActorsConstants.SEARCH_REDIRECT_URL
                        + tagStr.replace("<span class=\"hit\">", "").replace("</span>", ""));
                tagsList.add(tag);
            }
            actorArticle.setTags(tagsList);
        }
    }

    private void setArticleContentAndTitle(ThemeDisplay themeDisplay, QueryResponse queryResponse,
            SolrDocument doc, ActorArticle actorArticle) throws PortalException, SystemException {
        StringBuilder sb = new StringBuilder();

        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

        String highlightTitle = null;
        String highlightName = null;
        String highlightOrg = null;
        List<String> highlightDetailedDescriptionList = null;
        List<String> highlightIntroList = null;
        List<String> highlightTagsList = null;

        // System.out.println("highlighting " + highlighting);

        if (highlighting != null) {

            Map<String, List<String>> highlightedFields = highlighting.get(doc.get("uid"));

            // Title highlighting
            List<String> title = highlightedFields.get(Field.TITLE);
            if (title != null && title.size() > 0) {
                highlightTitle = title.get(0); // Probably rare with more than one but first should always be enough.
            }

            // Name highlighting
            List<String> name = highlightedFields.get(ActorsConstants.ARTICLE_XML_COMPANY_NAME);
            if (name != null && name.size() > 0) {
                highlightName = name.get(0); // Probably rare with more than one but first should always be
                // enough.
            }

            // Organisation name highlighting
            List<String> orgName = highlightedFields.get(ActorsConstants.ARTICLE_XML_ORGANIZATION_NAME);

            if (orgName != null && orgName.size() > 0) {
                highlightOrg = orgName.get(0); // Probably rare with more than one but first should always be
                // enough.
            }

            // Detailed Description highlighting
            highlightDetailedDescriptionList =
                    highlightedFields.get(ActorsConstants.ARTICLE_XML_DETAILED_DISCRIPTION);

            // Detailed Description highlighting
            highlightIntroList = highlightedFields.get(ActorsConstants.ARTICLE_XML_INTRO);

            // If no highlighting in highlightIntroList use highlightDetailedDescriptionList if if there is anyone
            // there.
            if (highlightIntroList == null && highlightDetailedDescriptionList != null) {
                highlightIntroList = highlightDetailedDescriptionList;
            }

            // Asset tag highlighting
            highlightTagsList = highlightedFields.get(Field.ASSET_TAG_NAMES);
        }

        if (highlightTitle != null) {
            actorArticle.setTitle(stripLanguageSuffix(highlightTitle));
        } else {
            actorArticle.setTitle(stripLanguageSuffix((String) doc.getFieldValue(Field.TITLE)));
        }

        if (highlightName != null) {
            actorArticle.setName(highlightName);
        } else {
            String name = (String) doc.getFieldValue(ActorsConstants.ARTICLE_XML_COMPANY_NAME);
            actorArticle.setName(name);
        }

        if (highlightOrg != null) {
            actorArticle.setOrganizationName(highlightOrg);
        } else {
            String organisationName =
                    (String) doc.getFieldValue(ActorsConstants.ARTICLE_XML_ORGANIZATION_NAME);
            actorArticle.setOrganizationName(organisationName);
        }

        if (highlightIntroList != null && highlightIntroList.size() > 0) {
            Iterator<String> iterator = highlightIntroList.iterator();
            while (true) {
                String content = iterator.next();
                sb.append(content);
                if (iterator.hasNext()) {
                    sb.append("<b>... </b> ");
                } else {
                    break;
                }
            }
            actorArticle.setIntro(sb.toString());
        } else {
            // No intro highlighting. Set article intro instead.
            actorArticle.setIntro((String) doc.getFieldValue(ActorsConstants.ARTICLE_XML_INTRO));
        }

        if (highlightDetailedDescriptionList != null && highlightDetailedDescriptionList.size() > 0) {
            Iterator<String> iterator = highlightDetailedDescriptionList.iterator();
            while (true) {
                String content = iterator.next();
                sb.append(content);
                if (iterator.hasNext()) {
                    sb.append("<b>... </b> ");
                } else {
                    break;
                }
            }
            actorArticle.setDetailedDescription(sb.toString());
        } else {
            // No detailed description highlighting. Set article detailed description instead.
            actorArticle.setDetailedDescription((String) doc
                    .getFieldValue(ActorsConstants.ARTICLE_XML_DETAILED_DISCRIPTION));
        }

        if (highlightTagsList != null && highlightTagsList.size() > 0) {
            String tagsStr = "";
            for (String tag : highlightTagsList) {
                tagsStr = tagsStr + tag + ",";
            }
            actorArticle.setTagsStr(tagsStr);
        } else {
            String tagsStr = "";

            Object tags = doc.getFieldValue(Field.ASSET_TAG_NAMES);

            if (tags != null) {

                if (tags instanceof String) {
                    tagsStr = (String) tags;
                } else if (tags instanceof ArrayList) {
                    ArrayList<String> tags2 = (ArrayList<String>) tags;
                    for (String tag : tags2) {
                        tagsStr = tagsStr + tag + ",";
                    }
                }
                actorArticle.setTagsStr(tagsStr);
            }
        }

        // System.out.println("actorArticle intro " + actorArticle.getIntro());
        // System.out.println("actorArticle dede " + actorArticle.getDetailedDescription());
    }

    private String stripLanguageSuffix(String string) {
        string = string.replace("_sv", "");
        string = string.replace("_SV", "");
        return string;
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
            String redirect =
                    ActorsConstants.SEARCH_REDIRECT_URL
                            + URLEncoder.encode(searchTerm, ActorsConstants.UTF_8);
            response.sendRedirect(redirect);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred when the search was attempted to performed", e);
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
            String redirect = ActorsConstants.SEARCH_REDIRECT_URL + "view-all";
            response.sendRedirect(redirect);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred when the search for all the actors was attempted.",
                    e);
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

        DynamicQuery dq =
                DynamicQueryFactoryUtil.forClass(AssetTag.class, PortalClassLoaderUtil.getClassLoader());

        Criterion searchTermCriterion = RestrictionsFactoryUtil.ilike("name", searchWord + "%");

        dq.add(searchTermCriterion);

        try {
            tagsList = AssetTagLocalServiceUtil.dynamicQuery(dq);

            log.info("Number of matching tags: " + tagsList.size());

        } catch (SystemException e) {
            log.error(e.getMessage(), e);
        }

        return tagsList;
    }

}
