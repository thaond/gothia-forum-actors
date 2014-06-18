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

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.gothiaforum.actorsarticle.util.ActorsConstants;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.List;

/**
 * This controller class performs a search for actors.
 * 
 * @author simgo3
 */

@Controller
@RequestMapping(value = "VIEW")
public class ActorsSearchResultController {
    private static final Logger LOG = LoggerFactory.getLogger(ActorsSearchResultController.class);

    @Autowired
    private JournalArticleLocalService articleService;

    /**
     * This method shows actor article view.
     * 
     * @param request
     *            the request
     * @param response
     *            the response
     * @param model
     *            the model
     * @return the profile view
     */
    @RenderMapping
    public String showActorArticleView(RenderRequest request, RenderResponse response, Model model) {

        String friendlyURL = request.getParameter("friendlyURL");

        if ((friendlyURL != null) && (!friendlyURL.contains("nologo"))) {

            ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

            try {
                Group groupContainingArticle =
                        GroupLocalServiceUtil.getFriendlyURLGroup(themeDisplay.getCompanyId(), "/"
                                + friendlyURL);

                JournalArticle foundJournalArticle = null;
                List<JournalArticle> articles;

                articles = articleService.getArticles();

                // Find the Journal Article for the current group.
                for (JournalArticle jA : articles) {
                    if (jA.getGroupId() == groupContainingArticle.getGroupId() && jA.getType().equals(ActorsConstants.TYPE_ACTOR)) {
                        foundJournalArticle = articleService.getLatestArticle(jA.getResourcePrimKey());
                        break;
                    }
                }

                String content =
                        articleService.getArticleContent(foundJournalArticle, ActorsConstants.GLOBAL_TEMPLATEID,
                                null, themeDisplay.getLanguageId(), themeDisplay);

                model.addAttribute("content", content);
            } catch (NoSuchGroupException e1) {
                LOG.warn("No such group found");
            } catch (PortalException e1) {
                throw new RuntimeException(
                        "An error occurred when the attempt of the rendering of the view was performed.", e1);
            } catch (SystemException e1) {
                throw new RuntimeException(
                        "An error occurred when the attempt of the rendering of the view was performed.", e1);
            }
        }

        return "profileView";
    }

    /**
     * Handles the exceptions.
     * 
     * @return the string
     */
    public String handleException() {
        return "errorPage";
    }

}
