package se.gothiaforum.controller.actorsform;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.gothiaforum.actorsarticle.domain.model.ActorArticle;
import se.gothiaforum.actorsarticle.service.ActorsService;
import se.gothiaforum.actorsarticle.util.ActorsConstants;
import se.gothiaforum.actorsarticle.util.ExpandoConstants;
import se.gothiaforum.settings.service.SettingsService;
import se.gothiaforum.util.actorsform.SocialRequestVO;
import se.gothiaforum.validator.actorsform.ActorArticleValidator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalService;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.SocialRequestConstants;
import com.liferay.portlet.social.service.SocialRequestInterpreterLocalService;
import com.liferay.portlet.social.service.SocialRequestLocalService;
import com.liferay.portlet.social.service.SocialRequestLocalServiceUtil;

/**
 * @author simgo3
 * 
 *         This controller takes care of rendering of the create actor article page.
 * 
 */

@Controller
@RequestMapping("VIEW")
public class ViewActorsArticleController {

    private static final Log log = LogFactory.getLog(ViewActorsArticleController.class);

    @Autowired
    private JournalArticleLocalService articleService;

    @Autowired
    private ActorsService actorsService;

    @Autowired
    private ActorArticleValidator validator;

    @Autowired
    private SocialRequestLocalService socialRequestService;
    
    @Autowired
    private SocialRequestInterpreterLocalService socialRequestInterpreterService;
       
    @Autowired
    private SettingsService settingsService;
    
    @RenderMapping(params = "view=showConnectView")
    public String showConnectView(Model model, RenderRequest request, RenderResponse response) {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        List<Organization> orgs = actorsService.getOrganizations(themeDisplay.getCompanyId());

        int cur = getInteger(request, "cur", 1);
        int delta = getInteger(request, "delta", 10);
    
        int start = (cur - 1) * delta;
        int end = start + delta;
        
        if (end > orgs.size()) {
            end = orgs.size();
        }
        
        model.addAttribute("actors", orgs.subList(start, end));
        model.addAttribute("total", orgs.size());
        model.addAttribute("cur", cur);
        model.addAttribute("delta", delta);
        
        
        PortletURL iteratorURL = response.createRenderURL() ;
        iteratorURL.setParameter("view", "showConnectView");    	   			
    	model.addAttribute("iteratorURL", iteratorURL);

        return "connectView";
    }

    @RenderMapping(params = "tabs1=user-tab")
    public String showUserView(Model model, RenderRequest request, RenderResponse response) {
    	  	
    	ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
    			WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();
		
		try {
			List<SocialRequest>  requests = socialRequestService.getReceiverUserRequests(
				user.getUserId(), SocialRequestConstants.STATUS_PENDING, 0,
				100);
			
				
				
				List<SocialRequestVO> socialRequestVOs = new ArrayList<SocialRequestVO>();
				
				for(SocialRequest s: requests){
					SocialRequestVO socialRequestVO = new  SocialRequestVO();
					socialRequestVO.setSocialRequest(s);
					socialRequestVO.setRequestFeedEntry(socialRequestInterpreterService.interpret(s, themeDisplay));
					socialRequestVOs.add(socialRequestVO);				
				}
								
				request.setAttribute(WebKeys.SOCIAL_REQUESTS, socialRequestVOs);
			
		} catch (SystemException e) {
			log.info("no social requests found.");
			System.out.println("no social requests found.");
		}
 		
        usersToResult(request, model);
        generateTabURL(request, response, model);
        return "blocks/userView";

    }

    @RenderMapping(params = "tabs1=form-tab")
    public String showFormView(Model model, RenderRequest request, RenderResponse response) {

        // Workaround to get the errors form-validation from actionrequest
        Errors errors = (Errors) model.asMap().get("errors");
        if (errors != null) {
            model.addAttribute("org.springframework.validation.BindingResult.actorArticle", errors);
            return "imageFormView";
        }

        generateTabURL(request, response, model);
        return "blocks/formView";

    }

    @RenderMapping
    public String showActorView(Model model, RenderRequest request, RenderResponse response) {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

        long userId = themeDisplay.getUserId();
        long companyId = themeDisplay.getCompanyId();
    	long groupId = themeDisplay.getScopeGroupId();

        try {
        	        	
            List<SocialRequest> socialRequests = socialRequestService.getUserRequests(userId, 0,
                    socialRequestService.getUserRequestsCount(userId));
            
            for(SocialRequest s: socialRequests){
            	if(s.getStatus() == 3 ){
            		
            		//This is for pinking up the articles in the portlet.
                	  		     	
                	try {
                		String socialRequestSentArticleId = settingsService.getSetting(ExpandoConstants.GOTHIA_SOCIAL_REQUEST_SENT, companyId, groupId);      	
            			String socialRequestSentArticleContent = articleService.getArticleContent(groupId, socialRequestSentArticleId, null, themeDisplay.getLanguageId(), themeDisplay);				
            			model.addAttribute("socialRequestSentArticleContent", socialRequestSentArticleContent);
            		} catch (PortalException e) {
            			log.info("no article for thin client search portlet found");
            		} catch (SystemException e) {
            			log.info("no article for thin client search portlet found");
            		}
            		
            		return "connectRequestSentView";
            	}
            }
            
            
       	try {
      		String fistTimeArticleId = settingsService.getSetting(ExpandoConstants.GOTHIA_FIRST_TIME, companyId, groupId);      	
  			String fistTimeArticleContent = articleService.getArticleContent(groupId, fistTimeArticleId, null, themeDisplay.getLanguageId(), themeDisplay);				
  			model.addAttribute("fistTimeArticleContent", fistTimeArticleContent);
  		} catch (PortalException e) {
  			log.info("no article for thin client search portlet found");
  		} catch (SystemException e) {
  			log.info("no article for thin client search portlet found");
  		}
  		
        } catch (SystemException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        }

        generateTabURL(request, response, model);
        return "blocks/profileView";
    }

    @InitBinder("actorArcticle")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    /**
     * 
     * @param actorArticle
     * @param request
     * @param response
     * @param sessionStatus
     * 
     *            adds the actors article.
     */
    @ActionMapping(params = "action=addActor")
    public void addActor(@ModelAttribute("actorArcticle") ActorArticle actorArticle, BindingResult result,
            @RequestParam("tagsEntries") String tagsEntries, ActionRequest request, ActionResponse response,
            Model model, SessionStatus sessionStatus) {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        long groupId = themeDisplay.getScopeGroupId();
        long userId = themeDisplay.getUserId();
        long defaultUserId = 0;

        try {
            defaultUserId = themeDisplay.getDefaultUserId();
            ServiceContext serviceContext = ServiceContextFactory.getInstance(JournalArticle.class.getName(),
                    request);
                        
            //Checks for duplicates, if exists return a validate error.
            List<Organization> orgs = actorsService.getOrganizations(themeDisplay.getCompanyId());            
            for(Organization o: orgs){            	
            	if (o.getName().equals(actorArticle.getCompanyName()) && actorArticle.getArticleId().isEmpty()) {
                	result.rejectValue("companyName", "code.dublicate", "duplicate-companyName");
                }	
            }
                  
            validator.validate(actorArticle, result);
            
            if (result.hasErrors()) {
                model.addAttribute("errors", result);
            } else {
                if (actorArticle.getArticleId().isEmpty()) {
                    actorsService.addActors(actorArticle, userId, defaultUserId, themeDisplay.getCompanyId(),
                            serviceContext, tagsEntries, themeDisplay.getScopeGroupId());
                } else {
                    JournalArticle article = actorsService.updateActors(actorArticle, userId, serviceContext,
                            tagsEntries, themeDisplay.getScopeGroupId());
                    Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class.getName());
                    indexer.reindex(article);
                }
                actorArticle = getActorsArticle(request);
            }
        } catch (PortalException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        } catch (SystemException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        }

        model.addAttribute("actorArcticle", actorArticle);
        model.addAttribute("tagsEntries", tagsEntries);

        // This sets the view page for the render phase.
        response.setRenderParameter("view", "showImageActorsForm");

    }

    @ActionMapping(params = "action=sendActor")
    public void sendActor(@ModelAttribute("actorArcticle") ActorArticle actorArticle, ActionRequest request,
            ActionResponse response, SessionStatus sessionStatus, Model model) {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        long userId = themeDisplay.getUserId();

        try {

            ServiceContext serviceContext = ServiceContextFactory.getInstance(JournalArticle.class.getName(),
                    request);

            System.out.println("send Actor - actorArticle.getPrimKeyId() = " + actorArticle.getPrimKeyId());

            actorsService.sendActors(userId, articleService.getArticle(actorArticle.getPrimKeyId()),
                    themeDisplay.getScopeGroupId(), serviceContext);

        } catch (PortalException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        } catch (SystemException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        }

        model.addAttribute("actorArcticle", getActorsArticle(request));

    }

    @ActionMapping(params = "action=connectToActor")
    public void connectToActor(ActionRequest request, ActionResponse response, SessionStatus sessionStatus,
            Model model, @RequestParam("organizationId") long organizationId) {

        System.out.println("Action connect to actor - organizationId = " + organizationId);

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

        actorsService.sendConnectRequest(organizationId, themeDisplay.getUserId(), themeDisplay.getCompanyId());

        // This sets the view page for the render phase.
        response.setRenderParameter("view", "connectRequestSentView");

    }
    
    @ActionMapping(params = "action=confirm")
    public void confirm(ActionRequest request, ActionResponse response, Model model  ) {
    	
    	ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
    			WebKeys.THEME_DISPLAY);

    		long requestId = ParamUtil.getLong(request, "requestId");
    		
    		try {
				socialRequestService.updateRequest(
					requestId, SocialRequestConstants.STATUS_CONFIRM, themeDisplay);
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	response.setRenderParameter("tabs1", "user-tab");
    }
    
    @ActionMapping(params = "action=deny")
    public void deny(ActionRequest request, ActionResponse response) {
    	
    	ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
    			WebKeys.THEME_DISPLAY);

    		long requestId = ParamUtil.getLong(request, "requestId");

    		try {
				socialRequestService.updateRequest(
					requestId, SocialRequestConstants.STATUS_IGNORE, themeDisplay);
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	response.setRenderParameter("tabs1", "user-tab");
    }

    
    /**
     * ModelAttributes
     */

    @ModelAttribute(value = "actorArcticle")
    public ActorArticle getActorsArticle(PortletRequest request) {

        // System.out.println("get modelattribute - actorArticle ");

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        ActorArticle actorArticle = new ActorArticle();
        actorArticle = actorsService.getActorsArticle(themeDisplay);
        return actorArticle;
    }


    @ModelAttribute(value = "tab")
    public String getTab(PortletRequest request, PortletResponse response) {

        return null;

    }

    // @ExceptionHandler({ Exception.class })
    public String handleException() {
        return "errorPage";
    }

    private void usersToResult(PortletRequest request, Model model) {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        List<User> users = actorsService.getUsers(themeDisplay);

        int cur = getInteger(request, "cur", 1);
        int delta = getInteger(request, "delta", 10);

        int start = (cur - 1) * delta;
        int end = start + delta;

        if (end > users.size()) {
            end = users.size();
        }
        
        model.addAttribute("users", users.subList(start, end));
        model.addAttribute("total", users.size());
        model.addAttribute("cur", cur);
        model.addAttribute("delta", delta);
    }

    private void generateTabURL(RenderRequest request, RenderResponse response, Model model) {

        PortletURL tabURL = response.createRenderURL();
        String tab = request.getParameter("tabs1");

        if (tab == null) {
            tab = "";
        }

        tabURL.setParameter("tabs1", tab);
        model.addAttribute("tabURL", tabURL);
        model.addAttribute("tabs", ActorsConstants.TABS);

    }

    private int getInteger(PortletRequest request, String paramName, int defualtvalue) {

        int param = defualtvalue;

        String paramStr = request.getParameter(paramName);
        if (paramStr != null) {
            param = Integer.valueOf(paramStr);
        }
        return param;
    }

}