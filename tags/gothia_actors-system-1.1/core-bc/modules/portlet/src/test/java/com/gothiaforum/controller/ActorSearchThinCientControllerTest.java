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

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.ui.Model;

import se.gothiaforum.controller.actorsearchthinclient.ActorsSearchThinClientController;
import se.gothiaforum.portlet.service.PortletService;
import se.gothiaforum.settings.service.SettingsService;
import se.gothiaforum.settings.service.impl.SettingsServiceImpl;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.expando.service.ExpandoColumnLocalService;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.journal.service.JournalArticleLocalService;

import static org.mockito.Mockito.*;

/**
 * @author simongoransson
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class ActorSearchThinCientControllerTest {

    private ActionRequest actionRequest;
    private ActionResponse actionResponse;
    private RenderRequest renderRequest;
    private RenderResponse renderResponse;
    private Model model;

    @Mock
    private JournalArticleLocalService articleService;

    @Mock
    private ExpandoColumnLocalService expandoColumnService;

    @Mock
    private ExpandoTableLocalService expandoTableService;

    @Mock
    private ExpandoValueLocalService expandoValueService;
    
    @Mock
    private PortletService portletService;
    

    @InjectMocks
    private final ActorsSearchThinClientController mockActorsSearchThinClientController =
            new ActorsSearchThinClientController();

    @InjectMocks
    private final SettingsService settingsService = new SettingsServiceImpl();

    @Before
    public void before() throws SecurityException, NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {

        // mock of services.
    	/*
        articleService = mock(JournalArticleLocalService.class);
        expandoColumnService = mock(ExpandoColumnLocalService.class);
        expandoTableService = mock(ExpandoTableLocalService.class);
        expandoValueService = mock(ExpandoValueLocalService.class);
        portletService = mock(PortletService.class);
        */

        // mock creation
        actionRequest = mock(ActionRequest.class);
        actionResponse = mock(ActionResponse.class);

        renderRequest = mock(RenderRequest.class);
        renderResponse = mock(RenderResponse.class);
        model = mock(Model.class);

        Field field = mockActorsSearchThinClientController.getClass().getDeclaredField("settingsService");
        field.setAccessible(true);
        field.set(mockActorsSearchThinClientController, settingsService);

        Field fieldArcticle =
                mockActorsSearchThinClientController.getClass().getDeclaredField("articleService");
        fieldArcticle.setAccessible(true);
        fieldArcticle.set(mockActorsSearchThinClientController, articleService);

        Field fieldValue = settingsService.getClass().getDeclaredField("expandoValueService");
        fieldValue.setAccessible(true);
        fieldValue.set(settingsService, expandoValueService);

    }

    @Test
    public void testActionSearch() throws Exception {

        when(actionRequest.getParameter(eq("searchTerm"))).thenReturn("apa");

        // using mock object
        mockActorsSearchThinClientController.search(actionRequest, actionResponse);

        // verification
        verify(actionRequest).getParameter(eq("searchTerm"));

    }

    @Test
    public void testView() throws Exception {

        ThemeDisplay themeDisplay = mock(ThemeDisplay.class);
        when(renderRequest.getAttribute(eq(WebKeys.THEME_DISPLAY))).thenReturn(themeDisplay);
        when(themeDisplay.getCompanyId()).thenReturn((long) 888);
        when(themeDisplay.getScopeGroupId()).thenReturn((long) 999);
        

        // Servlet Request
        PortalUtil portalUtil = new PortalUtil();
        Portal portal = mock(Portal.class);
        portalUtil.setPortal(portal);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletRequest httpServletRequest2 = mock(HttpServletRequest.class);
        when(portalUtil.getHttpServletRequest(any(PortletRequest.class))).thenReturn(httpServletRequest);
        when(portalUtil.getOriginalServletRequest(any(HttpServletRequest.class))).thenReturn(httpServletRequest2);
        
        // Portlet Preferences
        PortletPreferencesFactoryUtil portletPreferencesFactoryUtil = new PortletPreferencesFactoryUtil();
        PortletPreferencesFactory factory = mock(PortletPreferencesFactory.class);
        
        PortletPreferences portletPreferences = mock(PortletPreferences.class);
		when(factory.getPortletPreferences(Mockito.any(HttpServletRequest.class), Mockito.anyString())).thenReturn(portletPreferences);

		portletPreferencesFactoryUtil.setPortletPreferencesFactory(factory);
		
		// Portlet Service
		when(portletService.renderPortlet(any(PortletRequest.class), any(PortletResponse.class), anyString(), anyString())).thenReturn("Hello World");

        // using mock object
        mockActorsSearchThinClientController.showSearchActorView(renderRequest, renderResponse, model);

        // verification
        verify(model).addAttribute(eq("bannerArticleHtml"), eq("Hello World"));

    }
}
