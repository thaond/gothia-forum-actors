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

import java.lang.reflect.Field;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import se.gothiaforum.controller.actorsearchthinclient.ActorsSearchThinClientController;
import se.gothiaforum.settings.service.SettingsService;
import se.gothiaforum.settings.service.impl.SettingsServiceImpl;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.expando.service.ExpandoColumnLocalService;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.journal.service.JournalArticleLocalService;

/**
 * @author simongoransson
 * 
 */

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

    @InjectMocks
    private final ActorsSearchThinClientController mockActorsSearchThinClientController = new ActorsSearchThinClientController();

    @InjectMocks
    private final SettingsService settingsService = new SettingsServiceImpl();

    @Before
    public void before() throws SecurityException, NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {

        // mock of services.
        articleService = mock(JournalArticleLocalService.class);
        expandoColumnService = mock(ExpandoColumnLocalService.class);
        expandoTableService = mock(ExpandoTableLocalService.class);
        expandoValueService = mock(ExpandoValueLocalService.class);

        // mock creation
        actionRequest = mock(ActionRequest.class);
        actionResponse = mock(ActionResponse.class);

        renderRequest = mock(RenderRequest.class);
        renderResponse = mock(RenderResponse.class);
        model = mock(Model.class);

        Field field = mockActorsSearchThinClientController.getClass().getDeclaredField("settingsService");
        field.setAccessible(true);
        field.set(mockActorsSearchThinClientController, settingsService);

        Field fieldArcticle = mockActorsSearchThinClientController.getClass().getDeclaredField("articleService");
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

        // Settings service
        when(
                expandoValueService.getData(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
                        Mockito.anyString(), Mockito.anyLong(), Mockito.anyString())).thenReturn("testBannerID");

        when(
                articleService.getArticleContent(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
                        Mockito.anyString(), (ThemeDisplay) Mockito.anyObject())).thenReturn("Hello World");

        when(model.addAttribute(eq("bannerArticleContent"), eq("testBannerID"))).thenReturn(model);

        // using mock object
        mockActorsSearchThinClientController.showSearchActorView(renderRequest, model);

        // verification
        verify(model).addAttribute(eq("bannerArticleContent"), eq("Hello World"));

    }
}
