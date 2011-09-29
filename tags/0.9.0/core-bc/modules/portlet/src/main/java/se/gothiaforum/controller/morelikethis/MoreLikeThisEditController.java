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

package se.gothiaforum.controller.morelikethis;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;


@Controller
@RequestMapping("EDIT")
public class MoreLikeThisEditController {
    private static final Log log = LogFactory.getLog(MoreLikeThisController.class);


    @RenderMapping
    public String edit(Model model, RenderRequest request, RenderResponse response) {
    	
    	PortletPreferences prefs = request.getPreferences();
    	String tags = prefs.getValue("tags", "tags");
    	System.out.println("tags = " + tags);
    	model.addAttribute("tagsEntries", tags);
    	
        return "edit";
    }
    

    
    /**
     * 
     * @param request
     *            - action request
     */
    @ActionMapping(params = "action=save")
    public void savePreferences(ActionRequest request, @RequestParam("tagsEntries") String tagsEntries) {
    		    	
    	try {
    		PortletPreferences prefs = request.getPreferences();
			prefs.setValue("tags", tagsEntries);
			prefs.store();
		} catch (ReadOnlyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
