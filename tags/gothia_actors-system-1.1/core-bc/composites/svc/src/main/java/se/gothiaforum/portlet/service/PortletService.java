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

package se.gothiaforum.portlet.service;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ValidatorException;
import javax.servlet.ServletException;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * The Interface portletService.
 * 
 * @author simgo3
 * 
 *         Portlet rendering and similar.
 */

public interface PortletService {

    /**
     * Renders the given portlet as a runtime portlet and returns the portlet's HTML.
     *
     * @param request     The {@link PortletRequest}.
     * @param response    The {@link PortletResponse}.
     * @param portletId   The ID of the portlet to render, includes instance ID for instance portlets.
     * @param queryString The query string to use for the portlet.
     * @return The HTML for the given portlet.
     * @throws SystemException    on error.
     * @throws IOException        on error.
     * @throws ServletException   on error.
     * @throws ValidatorException on error.
     */
    String renderPortlet(PortletRequest request, PortletResponse response, String portletId, String queryString) throws SystemException, IOException, ServletException, ValidatorException;
    
    
}
