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

package se.gothiaforum.controller.actorssearchresult;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.gothiaforum.actorsarticle.util.ActorsConstants;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
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
public class ActorsSearchResultController {
	private static final Log log = LogFactory.getLog(ActorsSearchResultController.class);

	@Autowired
	private JournalArticleLocalService articleService;

	@RenderMapping
	public String showActorArticleView(RenderRequest request, RenderResponse response, Model model) {

		String friendlyURL = request.getParameter("friendlyURL");

		if (friendlyURL != null) {

			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

			try {
				Group group = GroupLocalServiceUtil.getFriendlyURLGroup(themeDisplay.getCompanyId(), "/"
				        + friendlyURL);

				JournalArticle journalArticle = null;
				List<JournalArticle> articles;

				articles = articleService.getArticles();

				// Find the Journal Article for the current group.
				for (JournalArticle jA : articles) {
					if (jA.getGroupId() == group.getGroupId() && jA.getType().equals(ActorsConstants.TYPE_ACTOR)) {
						journalArticle = articleService.getLatestArticle(jA.getResourcePrimKey());
					}
				}

				String content = articleService.getArticleContent(journalArticle,
				        ActorsConstants.GLOBAL_TEMPLATEID, null, themeDisplay.getLanguageId(), themeDisplay);

				model.addAttribute("content", content);
			} catch (PortalException e1) {
				throw new RuntimeException("TODO: Handle this exception better", e1);
			} catch (SystemException e1) {
				throw new RuntimeException("TODO: Handle this exception better", e1);
			}
		}

		return "profileView";
	}

	// @ExceptionHandler({ Exception.class })
	public String handleException() {
		return "errorPage";
	}

}
