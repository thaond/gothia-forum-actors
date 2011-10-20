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

package se.gothiaforum.controller.actorsearchthinclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
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
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleLocalService;

/**
 * @author simgo3
 * 
 *         This Controller class is used as a client to call the "main" search component (ActorSearchController).
 * 
 */

@Controller
@RequestMapping(value = "VIEW")
public class ActorsSearchThinClientController {
	private static final Log log = LogFactory.getLog(ActorsSearchThinClientController.class);

	@Autowired
	private SettingsService settingsService;

	@Autowired
	private JournalArticleLocalService articleService;

	@RenderMapping
	public String showEditIFeedForm(RenderRequest request, Model model) {

		// This is for pinking up the articles in the portlet.
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		long companyId = themeDisplay.getCompanyId();
		long groupId = themeDisplay.getScopeGroupId();

		try {
			String bannerArticleId = settingsService.getSetting(ExpandoConstants.GOTHIA_BANNER_ARTICLE, companyId,
			        groupId);
			String bannerArticleContent = articleService.getArticleContent(groupId, bannerArticleId, null,
			        themeDisplay.getLanguageId(), themeDisplay);
			model.addAttribute("bannerArticleContent", bannerArticleContent);
		} catch (PortalException e) {
			log.info("no article for thin client search portlet found");
		} catch (SystemException e) {
			log.info("no article for thin client search portlet found");
		}

		try {
			String searchclientArticleId = settingsService.getSetting(ExpandoConstants.GOTHIA_THIN_SEARCH_ARTICLE,
			        companyId, groupId);
			String searchclientArticleContent = articleService.getArticleContent(groupId, searchclientArticleId,
			        null, themeDisplay.getLanguageId(), themeDisplay);
			model.addAttribute("searchclientArticleContent", searchclientArticleContent);
		} catch (PortalException e) {
			log.info("no article for thin client search portlet found");
		} catch (SystemException e) {
			log.info("no article for thin client search portlet found");
		}

		try {
			model.addAttribute("hostName", InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "searchActorView";
	}

	@ActionMapping("search")
	public void search(ActionRequest request, ActionResponse response) {

		String searchTerm = request.getParameter("searchTerm");

		try {
			String redirect = ActorsConstants.SEARCH_RIDERECT_URL
			        + URLEncoder.encode(searchTerm, ActorsConstants.UTF_8);
			response.sendRedirect(redirect);
		} catch (IOException e) {
			throw new RuntimeException("TODO: Handle this exception better", e);
		}
	}

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

			PrintWriter printWriter = response.getWriter().append(jsonResponse.toString());

			response.setContentType("application/json");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
