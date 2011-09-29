package se.gothiaforum.controller.morelikethis;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.gothiaforum.actorsarticle.domain.model.ActorArticle;
import se.gothiaforum.actorsarticle.util.ActorsConstants;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.StringQueryImpl;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalService;

/**
 * @author simgo3 This is the controller for the more like this portlet for
 *         actor presentations.
 */

@Controller
@RequestMapping("VIEW")
public class MoreLikeThisController {
	private static final Log log = LogFactory
			.getLog(MoreLikeThisController.class);

	@Autowired
	private AssetEntryLocalService assetEntryService;

	@Autowired
	private JournalArticleLocalService articleService;

	@Autowired
	private GroupLocalService groupService;

	/*
	 * The main render for the more like this portlet for providing actor that
	 * reminds of the actor the user currently viewing.
	 */
	@RenderMapping
    public String showMoreLikeThis(RenderRequest request, Model model) {

    	ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    	PortletPreferences prefs = request.getPreferences();
    	String tagsStr = prefs.getValue("tags", "tags");
    	
    	StringQueryImpl query = new StringQueryImpl("");

    	if(!tagsStr.equals("")){
    		System.out.println("tagsStr = " + tagsStr);
    		String searchTags = tagsStr.replaceAll(",", " ");
    		System.out.println("searchTags = " + searchTags);
    		
    		query = new StringQueryImpl("(assetTagNames:" + searchTags
	                + ") AND (entryClassName:com.liferay.portlet.journal.model.JournalArticle AND type:"
	                + ActorsConstants.TYPE_ACTOR + ") sort=title asc");
    		
    		
    	}else{   	
	        String friendlyURL = request.getParameter("friendlyURL");
	        if (friendlyURL != null) {
	            
                Group group;
				try {
					group = GroupLocalServiceUtil.getFriendlyURLGroup(themeDisplay.getCompanyId(), "/"
					        + friendlyURL);
					JournalArticle journalArticle = null;
	                List<JournalArticle> articles;

	                articles = articleService.getArticles();
	                
	                // Find the Journal Article for the current group.
	                for (JournalArticle jA : articles) {
	                    if (jA.getGroupId() == group.getGroupId() && jA.getType().equals(ActorsConstants.TYPE_ACTOR)) {
	                        journalArticle = jA;
	                    }
	                }

	                List<AssetTag> tags = getTags(Long.valueOf(group.getGroupId()), journalArticle.getArticleId());
	                
	                String searchTags = "";
	                for (AssetTag t : tags) {
	    	            searchTags = searchTags + t.getName() + " ";
	    	        }
	    	
	                query = new StringQueryImpl("(assetTagNames:" + searchTags
	    	                + ") AND (entryClassName:com.liferay.portlet.journal.model.JournalArticle AND type:"
	    	                + ActorsConstants.TYPE_ACTOR + ") AND !(articleId:" + journalArticle.getArticleId()
	    	                + ") sort=title asc");
	                
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}         
	       }
    	}
	    //Search for more like this.                                          
        List<ActorArticle> actorArticles = search(themeDisplay, query);
     
        model.addAttribute("hitsIsNotEmpty", !actorArticles.isEmpty());         
        model.addAttribute("hits", actorArticles);
                
        return "moreLikeThisView";
    	
    }

	private List<ActorArticle> search(ThemeDisplay themeDisplay,
			StringQueryImpl query) {

		long companyId = themeDisplay.getCompanyId();

		int start = 0;
		int end = 5;

		List<ActorArticle> actorArticles = new ArrayList<ActorArticle>();

		try {
			// Searching for more like this actors articles
			Hits hits = SearchEngineUtil.search(companyId, query, start, end);

			// Populate the actorsArticles object (which we return) with the
			// content from the hits object
			if (hits.getDocs() != null) {

				for (Document d : hits.getDocs()) {
					ActorArticle actorArticle = new ActorArticle();
					actorArticle.setArticleId(d
							.get(ActorsConstants.ACTORS_ARTICLE_PK));		
					actorArticle.setGroupId(Long.valueOf(d
							.get(ActorsConstants.GROUP_ID)));
					JournalArticle tempJournalArticle = articleService
							.getArticle(actorArticle.getGroupId(),
									actorArticle.getArticleId());

					String content = articleService.getArticleContent(
							tempJournalArticle,
							ActorsConstants.GLOBAL_MORE_LIKE_THIS_TEMPLATEID,
							null, themeDisplay.getLanguageId(), themeDisplay);

					actorArticle.setContent(content);
					String namePrefix = groupService.getGroup(
							actorArticle.getGroupId()).getFriendlyURL();
					actorArticle
							.setProfileURL(ActorsConstants.PROFILE_RIDERECT_URL
									+ namePrefix.substring(1));
					actorArticles.add(actorArticle);
				}
			}
		} catch (PortalException e1) {
			throw new RuntimeException("TODO: Handle this exception better", e1);
		} catch (SystemException e1) {
			throw new RuntimeException("TODO: Handle this exception better", e1);
		}
		return actorArticles;
	}

	/*
	 * A help method to the showMoreLikeThis method, It returns the assettags to
	 * an article in parameters is group id and article id for identifying the
	 * tags.
	 */
	private List<AssetTag> getTags(long groupId, String articleId) {

		List<AssetTag> tags = null;

		try {
			long classPK = articleService.getArticle(groupId, articleId)
					.getResourcePrimKey();
			AssetEntry assetEntry = assetEntryService.getEntry(
					JournalArticle.class.getName(), classPK);
			tags = assetEntry.getTags();

		} catch (PortalException e) {
			throw new RuntimeException("TODO: Handle this exception better", e);
		} catch (SystemException e) {
			throw new RuntimeException("TODO: Handle this exception better", e);
		}

		return tags;

	}
}
