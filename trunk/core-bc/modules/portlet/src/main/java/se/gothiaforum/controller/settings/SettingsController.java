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

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.gothiaforum.settings.service.SettingsService;
import se.gothiaforum.settings.util.ExpandoConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;

/**
 * This is the controller class for the settings portlet that is use to configure which article should been shown
 * where on this site.
 * 
 * @author simgo3
 */

@Controller
@RequestMapping(value = "VIEW")
public class SettingsController {
    private static final Logger LOG = LoggerFactory.getLogger(SettingsController.class);

    @Autowired
    private SettingsService settingsService;

    /**
     * This is the render for the settings portlet.
     * 
     * @param request
     *            the request
     * @param model
     *            the model
     * @return the string
     */
    @RenderMapping
    public String showSettingsView(RenderRequest request, Model model) {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        long companyId = themeDisplay.getCompanyId();
        long groupId = themeDisplay.getScopeGroup().getGroupId();

        String siteLanguage =
                settingsService.getSetting(ExpandoConstants.GOTHIA_SITE_LANGUAGE, companyId, groupId);

        String topNavigationId =
                settingsService.getSetting(ExpandoConstants.GOTHIA_HEADER_TOP_NAVIGATION_ARTICLE_ID,
                        companyId, groupId);

        String footerId1 =
                settingsService.getSetting(ExpandoConstants.GOTHIA_FOOTER_ARTICLE_ID1, companyId, groupId);
        String footerId2 =
                settingsService.getSetting(ExpandoConstants.GOTHIA_FOOTER_ARTICLE_ID2, companyId, groupId);
        String footerId3 =
                settingsService.getSetting(ExpandoConstants.GOTHIA_FOOTER_ARTICLE_ID3, companyId, groupId);
        String bannerArticleId =
                settingsService.getSetting(ExpandoConstants.GOTHIA_BANNER_ARTICLE, companyId, groupId);
        String searchclientArticleId =
                settingsService.getSetting(ExpandoConstants.GOTHIA_THIN_SEARCH_ARTICLE, companyId, groupId);
        String searchArticleId =
                settingsService.getSetting(ExpandoConstants.GOTHIA_SEARCH_ARTICLE, companyId, groupId);
        String socialRequestSentArticlId =
                settingsService.getSetting(ExpandoConstants.GOTHIA_SOCIAL_REQUEST_SENT, companyId, groupId);
        String searchFirstTimeArticleId =
                settingsService.getSetting(ExpandoConstants.GOTHIA_SEARCH_FITST_TIME_ARTICLE, companyId,
                        groupId);
        String searchNoHitsArticleId =
                settingsService
                        .getSetting(ExpandoConstants.GOTHIA_SEARCH_NO_HITS_ARTICLE, companyId, groupId);
        String fistTimeArticleId =
                settingsService.getSetting(ExpandoConstants.GOTHIA_FIRST_TIME, companyId, groupId);

        boolean showUserPages =
                settingsService
                        .getSettingBoolean(ExpandoConstants.GOTHIA_SHOW_USER_PAGES, companyId, groupId);

        model.addAttribute("siteLanguage", siteLanguage);
        model.addAttribute("topNavigationId", topNavigationId);
        model.addAttribute("footerId1", footerId1);
        model.addAttribute("footerId2", footerId2);
        model.addAttribute("footerId3", footerId3);
        model.addAttribute("bannerArticleId", bannerArticleId);
        model.addAttribute("searchclientArticleId", searchclientArticleId);
        model.addAttribute("searchArticleId", searchArticleId);
        model.addAttribute("socialRequestSentArticlId", socialRequestSentArticlId);
        model.addAttribute("searchFirstTimeArticleId", searchFirstTimeArticleId);
        model.addAttribute("searchNoHitsArticleId", searchNoHitsArticleId);
        model.addAttribute("fistTimeArticleId", fistTimeArticleId);
        model.addAttribute("showUserPages", showUserPages);

        return "settingsView";

    }

    /**
     * This method saves the settings for the settings portlet.
     * 
     * @param request
     *            the request
     * @param response
     *            the response
     */
    @ActionMapping(params = "action=save")
    public void save(ActionRequest request, ActionResponse response) {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        long companyId = themeDisplay.getCompanyId();
        long groupId = themeDisplay.getScopeGroup().getGroupId();

        String siteLanguage = request.getParameter("siteLanguage");
        String topNavigationId = request.getParameter("topNavigationId");
        String footerId1 = request.getParameter("footerId1");
        String footerId2 = request.getParameter("footerId2");
        String footerId3 = request.getParameter("footerId3");

        String bannerArticleId = request.getParameter("bannerArticleId");
        String searchclientArticleId = request.getParameter("searchclientArticleId");
        String searchArticleId = request.getParameter("searchArticleId");
        String socialRequestSentArticlId = request.getParameter("socialRequestSentArticlId");
        String searchFirstTimeArticleId = request.getParameter("searchFirstTimeArticleId");
        String searchNoHitsArticleId = request.getParameter("searchNoHitsArticleId");
        String fistTimeArticleId = request.getParameter("fistTimeArticleId");

        boolean showUserPages = ParamUtil.getBoolean(request, "showUserPages", false);

        settingsService.setSetting(siteLanguage, ExpandoConstants.GOTHIA_SITE_LANGUAGE, companyId, groupId);
        settingsService.setSetting(topNavigationId, ExpandoConstants.GOTHIA_HEADER_TOP_NAVIGATION_ARTICLE_ID,
                companyId, groupId);
        settingsService.setSetting(footerId1, ExpandoConstants.GOTHIA_FOOTER_ARTICLE_ID1, companyId, groupId);
        settingsService.setSetting(footerId2, ExpandoConstants.GOTHIA_FOOTER_ARTICLE_ID2, companyId, groupId);
        settingsService.setSetting(footerId3, ExpandoConstants.GOTHIA_FOOTER_ARTICLE_ID3, companyId, groupId);
        settingsService.setSetting(bannerArticleId, ExpandoConstants.GOTHIA_BANNER_ARTICLE, companyId,
                groupId);
        settingsService.setSetting(searchclientArticleId, ExpandoConstants.GOTHIA_THIN_SEARCH_ARTICLE,
                companyId, groupId);
        settingsService.setSetting(searchArticleId, ExpandoConstants.GOTHIA_SEARCH_ARTICLE, companyId,
                groupId);
        settingsService.setSetting(socialRequestSentArticlId, ExpandoConstants.GOTHIA_SOCIAL_REQUEST_SENT,
                companyId, groupId);
        settingsService.setSetting(searchFirstTimeArticleId,
                ExpandoConstants.GOTHIA_SEARCH_FITST_TIME_ARTICLE, companyId, groupId);
        settingsService.setSetting(searchNoHitsArticleId, ExpandoConstants.GOTHIA_SEARCH_NO_HITS_ARTICLE,
                companyId, groupId);
        settingsService.setSetting(fistTimeArticleId, ExpandoConstants.GOTHIA_FIRST_TIME, companyId, groupId);

        settingsService.setSettingBoolean(showUserPages, ExpandoConstants.GOTHIA_SHOW_USER_PAGES, companyId,
                groupId);

    }

}
