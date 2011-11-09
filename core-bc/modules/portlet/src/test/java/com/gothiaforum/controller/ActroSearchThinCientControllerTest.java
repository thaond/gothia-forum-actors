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

/**
 * 
 */
package com.gothiaforum.controller;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.Test;

import se.gothiaforum.controller.actorsearchthinclient.ActorsSearchThinClientController;

/**
 * @author simongoransson
 * 
 */

public class ActroSearchThinCientControllerTest {

    @Test
    public void test1() throws Exception {

        // mock creation
        ActionRequest request = mock(ActionRequest.class);
        ActionResponse response = mock(ActionResponse.class);

        when(request.getParameter(eq("searchTerm"))).thenReturn("apa");

        // System.out.println(request.getParameter("searchTerm"));

        ActorsSearchThinClientController mockActorsSearchThinClientController = new ActorsSearchThinClientController();

        // using mock object
        mockActorsSearchThinClientController.search(request, response);

        // System.out.println("test " + response.);

        // verification
        verify(request).getParameter(eq("searchTerm"));

    }
}
