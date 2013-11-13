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

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * The Class MoreLikeThisEditController.
 */
@Controller
@RequestMapping("EDIT")
public class ActorSearchEditController {
    private static final Log LOG = LogFactory.getLog(ActorSearchEditController.class);

    /**
     * Rendering the edit view.
     * 
     * @param model
     *            the model
     * @param request
     *            the request
     * @param response
     *            the response
     * @return edit view
     */
    @RenderMapping
    public String edit(Model model, RenderRequest request, RenderResponse response) {

        PortletPreferences prefs = request.getPreferences();
        String numberOfHitsToShow = prefs.getValue("numberOfHitsToShow", "3");

        model.addAttribute("numberOfHitsToShow", numberOfHitsToShow);

        return "edit";
    }

    /**
     * Store the tags from the posted form.
     * 
     * @param request
     *            the request
     * @param tagsEntries
     *            the tags entries
     */
    @ActionMapping(params = "action=save")
    public void savePreferences(ActionRequest request,
            @RequestParam("numberOfHitsToShow") String numberOfHitsToShow) {

        try {
            PortletPreferences prefs = request.getPreferences();
            prefs.setValue("numberOfHitsToShow", numberOfHitsToShow);
            prefs.store();
        } catch (ReadOnlyException e) {
            LOG.error("could not store tags in more like this edit mode.", e);
        } catch (ValidatorException e) {
            LOG.error("could not store tags in more like this edit mode.", e);
        } catch (IOException e) {
            LOG.error("could not store tags in more like this edit mode.", e);
        }
    }
}
