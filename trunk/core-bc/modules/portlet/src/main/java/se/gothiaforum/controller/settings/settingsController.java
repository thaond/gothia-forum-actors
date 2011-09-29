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

package se.gothiaforum.controller.settings;

import java.io.IOException;
import java.net.URLEncoder;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;

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

import se.gothiaforum.actorsarticle.util.ActorsConstants;
import se.gothiaforum.actorsarticle.util.ExpandoConstants;
import se.gothiaforum.settings.service.SettingsService;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.expando.DuplicateColumnNameException;
import com.liferay.portlet.expando.DuplicateTableNameException;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;

/**
 * @author simgo3
 * 
 */

@Controller
@RequestMapping(value = "VIEW")
public class settingsController {
    private static final Log log = LogFactory.getLog(settingsController.class);

    
    @Autowired
    private SettingsService settingsService;
    
    @Autowired
    private GroupLocalService groupService; 
    
    
    @RenderMapping
    public String showSettingsView(RenderRequest request, Model model) {

    	ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    	long companyId = themeDisplay.getCompanyId();
    	long groupId = 0;
		try {
			groupId = groupService.getGroup(companyId, "Guest").getGroupId();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String footerId1 = settingsService.getSetting(ExpandoConstants.GOTHIA_FOOTER_ARTICLE_ID1, companyId, groupId);
    	String footerId2 = settingsService.getSetting(ExpandoConstants.GOTHIA_FOOTER_ARTICLE_ID2, companyId, groupId);
    	String footerId3 = settingsService.getSetting(ExpandoConstants.GOTHIA_FOOTER_ARTICLE_ID3, companyId, groupId);
    	String footerId4 = settingsService.getSetting(ExpandoConstants.GOTHIA_FOOTER_ARTICLE_ID4, companyId, groupId);    	
    	String bannerArticleId = settingsService.getSetting(ExpandoConstants.GOTHIA_BANNER_ARTICLE, companyId, groupId);
    	String searchclientArticleId = settingsService.getSetting(ExpandoConstants.GOTHIA_THIN_SEARCH_ARTICLE, companyId, groupId);
    	String searchArticleId = settingsService.getSetting(ExpandoConstants.GOTHIA_SEARCH_ARTICLE, companyId, groupId);
    	String socialRequestSentArticlId = settingsService.getSetting(ExpandoConstants.GOTHIA_SOCIAL_REQUEST_SENT, companyId, groupId);
    	String searchFirstTimeArticleId = settingsService.getSetting(ExpandoConstants.GOTHIA_SEARCH_FITST_TIME_ARTICLE, companyId, groupId);
    	String searchNoHitsArticleId = settingsService.getSetting(ExpandoConstants.GOTHIA_SEARCH_NO_HITS_ARTICLE, companyId, groupId);
    	String fistTimeArticleId = settingsService.getSetting(ExpandoConstants.GOTHIA_FIRST_TIME, companyId, groupId);
    	    	    	
    	model.addAttribute("footerId1", footerId1 );    	
    	model.addAttribute("footerId2", footerId2 );
    	model.addAttribute("footerId3", footerId3 );    	
    	model.addAttribute("footerId4", footerId4 );
    	model.addAttribute("bannerArticleId", bannerArticleId );    	
    	model.addAttribute("searchclientArticleId", searchclientArticleId );
    	model.addAttribute("searchArticleId", searchArticleId );
    	model.addAttribute("socialRequestSentArticlId", socialRequestSentArticlId );
    	model.addAttribute("searchFirstTimeArticleId", searchFirstTimeArticleId );
    	model.addAttribute("searchNoHitsArticleId", searchNoHitsArticleId );
    	model.addAttribute("fistTimeArticleId", fistTimeArticleId );
    	
        return "settingsView";

    }
    
    
    @ActionMapping(params = "action=save")
    public void search(ActionRequest request, ActionResponse response) {

    	ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    	long companyId = themeDisplay.getCompanyId();
    	long groupId = 0;
		try {
			groupId = groupService.getGroup(companyId, "Guest").getGroupId();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String footerId1 = request.getParameter("footerId1");
    	String footerId2 = request.getParameter("footerId2");
    	String footerId3 = request.getParameter("footerId3");
    	String footerId4 = request.getParameter("footerId4");
    	
    	String bannerArticleId = request.getParameter("bannerArticleId");
    	String searchclientArticleId = request.getParameter("searchclientArticleId");
    	String searchArticleId = request.getParameter("searchArticleId");
    	String socialRequestSentArticlId = request.getParameter("socialRequestSentArticlId");
    	String searchFirstTimeArticleId = request.getParameter("searchFirstTimeArticleId");
    	String searchNoHitsArticleId = request.getParameter("searchNoHitsArticleId");
    	String fistTimeArticleId = request.getParameter("fistTimeArticleId");
    	
    	
    	
    	   	
    	settingsService.setSetting(footerId1,ExpandoConstants.GOTHIA_FOOTER_ARTICLE_ID1, companyId, groupId);
    	settingsService.setSetting(footerId2,ExpandoConstants.GOTHIA_FOOTER_ARTICLE_ID2, companyId, groupId);
    	settingsService.setSetting(footerId3,ExpandoConstants.GOTHIA_FOOTER_ARTICLE_ID3, companyId, groupId);
    	settingsService.setSetting(footerId4,ExpandoConstants.GOTHIA_FOOTER_ARTICLE_ID4, companyId, groupId);
    	settingsService.setSetting(bannerArticleId,ExpandoConstants.GOTHIA_BANNER_ARTICLE, companyId, groupId);
    	settingsService.setSetting(searchclientArticleId,ExpandoConstants.GOTHIA_THIN_SEARCH_ARTICLE, companyId, groupId);
    	settingsService.setSetting(searchArticleId,ExpandoConstants.GOTHIA_SEARCH_ARTICLE, companyId, groupId);
    	settingsService.setSetting(socialRequestSentArticlId,ExpandoConstants.GOTHIA_SOCIAL_REQUEST_SENT, companyId, groupId);
    	settingsService.setSetting(searchFirstTimeArticleId,ExpandoConstants.GOTHIA_SEARCH_FITST_TIME_ARTICLE, companyId, groupId);
    	settingsService.setSetting(searchNoHitsArticleId,ExpandoConstants.GOTHIA_SEARCH_NO_HITS_ARTICLE, companyId, groupId);
    	settingsService.setSetting(fistTimeArticleId,ExpandoConstants.GOTHIA_FIRST_TIME, companyId, groupId);
    	
    }
  
} 
	