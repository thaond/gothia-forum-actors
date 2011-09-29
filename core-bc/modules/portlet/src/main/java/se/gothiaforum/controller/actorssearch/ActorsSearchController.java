package se.gothiaforum.controller.actorssearch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import se.gothiaforum.actorsarticle.util.ExpandoConstants;
import se.gothiaforum.settings.service.SettingsService;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.StringQueryImpl;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalService;

/**
 * @author simgo3
 * 
 *         This controller class performs a search for actors.
 * 
 */

@Controller
@RequestMapping(value = "VIEW")
public class ActorsSearchController {

    private static Log log = LogFactory.getLog(ActorsSearchController.class);

    @Autowired
    private JournalArticleLocalService articleService;

    @Autowired
    private GroupLocalService groupService;
    
    @Autowired
    private SettingsService settingsService;
    

    @RenderMapping
    public String showFormView(RenderRequest request, Model model) {

    	ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    	
    	long companyId = themeDisplay.getCompanyId();
    	long groupId = themeDisplay.getScopeGroupId();
    	 
        String searchTerm = request.getParameter("searchTerm");

        if (searchTerm != null) {

            try {

                // TODO Kolla på att använda SolrQuery solrQuery och göra toString.

                StringQueryImpl query = new StringQueryImpl("(" + searchTerm + " or assetTagNames:" + searchTerm
                        + ") AND (entryClassName:com.liferay.portlet.journal.model.JournalArticle AND type:"
                        + ActorsConstants.TYPE_ACTOR + " AND status:0)");

                int start = 0;
                int end = 100;

                //System.out.println("query ? " + query);

                Hits hits = SearchEngineUtil.search(companyId, query, start, end); //SearchEngineUtil.search(companyId, groupIds, userId, className, query, start, end);
                List<ActorArticle> actorArticles = new ArrayList<ActorArticle>();

                for (Document d : hits.getDocs()) {
                    ActorArticle actorArticle = new ActorArticle();
                    actorArticle.setArticleId(d.get(ActorsConstants.ACTORS_ARTICLE_PK));

                    long curGroupId = Long.valueOf(d.get(ActorsConstants.GROUP_ID));

                    actorArticle.setGroupId(curGroupId);

                    JournalArticle journalArticle = articleService.getArticle(actorArticle.getGroupId(),
                            actorArticle.getArticleId());
                    actorArticle.setContent(articleService.getArticleContent(journalArticle,
                            ActorsConstants.GLOBAL_LIST_TEMPLATEID, null, themeDisplay.getLanguageId(),
                            themeDisplay));

                    String namePrefix = groupService.getGroup(curGroupId).getFriendlyURL();

                    actorArticle.setProfileURL(ActorsConstants.PROFILE_RIDERECT_URL + namePrefix.substring(1));

                    actorArticles.add(actorArticle);
                }

                /*   for (ActorArticle a : actorArticles) {
                       System.out.println("search result = " + a.getArticleId());
                   }*/
                
                if(actorArticles.size() == 0){
                	
                	 try {
                 		String searchNoHitsArticleId = settingsService.getSetting(ExpandoConstants.GOTHIA_SEARCH_NO_HITS_ARTICLE, companyId, groupId);      	
             			String searchNoHitsArticleContent = articleService.getArticleContent(groupId, searchNoHitsArticleId, null, themeDisplay.getLanguageId(), themeDisplay);				
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
        }else{ //Fist time = no search made. 
        	
        	
        	try {
        		String searchFirstTimeArticleId = settingsService.getSetting(ExpandoConstants.GOTHIA_SEARCH_FITST_TIME_ARTICLE, companyId, groupId);      	
    			String searchFirstTimeArticleContent = articleService.getArticleContent(groupId, searchFirstTimeArticleId, null, themeDisplay.getLanguageId(), themeDisplay);				
    			model.addAttribute("searchFirstTimeArticleContent", searchFirstTimeArticleContent);
    		} catch (PortalException e) {
    			log.info("no article for thin client search portlet found");
    		} catch (SystemException e) {
    			log.info("no article for thin client search portlet found");
    		}
        	
        }
        
      //This is for pinking up the articles in the portlet.
    	
        try {
    		String searchArticleId = settingsService.getSetting(ExpandoConstants.GOTHIA_SEARCH_ARTICLE, companyId, groupId);      	
			String searchArticleContent = articleService.getArticleContent(groupId, searchArticleId, null, themeDisplay.getLanguageId(), themeDisplay);				
			model.addAttribute("searchArticleContent", searchArticleContent);
		} catch (PortalException e) {
			log.info("no article for thin client search portlet found");
		} catch (SystemException e) {
			log.info("no article for thin client search portlet found");
		}
		
		
		
		

        return "searchView";
    }

    /*
     * This method receives an parameter (searchTerm) and just send it to the render using an public render parameter. 
     * Where the render performs the search on that searchTerm. 
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

        //response.setRenderParameter("searchTerm", searchTerm);

    }

    /*
     * This method providing the actors search portlet whit matching tags while auto completing.   
     * Uses the parameter searchTerm to find tags and paring them to json objects for the java script.
     */
    @ResourceMapping
    public void serveResource(@RequestParam("searchTerm") String searchFor, ResourceRequest request,
            ResourceResponse response) throws IOException, PortletException {

    		System.out.println("searchFor = " + searchFor);
    	
        try {

            List<AssetTag> matchingTags = getMatchingTags(searchFor);

            JSONArray jsonResult = com.liferay.portal.kernel.json.JSONFactoryUtil.createJSONArray();

            for (AssetTag tag : matchingTags) {
                JSONObject jsonRow = JSONFactoryUtil.createJSONObject();
                jsonRow.put("key", tag.getTagId());
                jsonRow.put("name", tag.getName());
                jsonResult.put(jsonRow);
                
                System.out.println("tagname = " + tag.getName());
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
     * This is an help method to serveResource it helps by providing matching 
     * tags to the current search term. Returns a list of tags.
     * 
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
