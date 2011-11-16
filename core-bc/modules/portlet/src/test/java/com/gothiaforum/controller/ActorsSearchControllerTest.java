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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.web.bind.support.SessionStatus;

import se.gothiaforum.controller.actorssearch.ActorsSearchController;
import se.gothiaforum.settings.service.SettingsService;
import se.gothiaforum.solr.ActroSolrQuery;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalService;

/**
 * @author simongoransson
 * 
 */
public class ActorsSearchControllerTest {
    private static final Log LOG = LogFactory.getLog(ActorsSearchControllerTest.class);

    private ActionRequest actionRequest;
    private ActionResponse actionResponse;
    private RenderRequest renderRequest;
    private RenderResponse renderResponse;
    private Model model;
    private SessionStatus sessionStatus;

    @Mock
    private JournalArticleLocalService articleService;

    @Mock
    private SettingsService settingsService;

    @Mock
    private ActroSolrQuery actroSolrQuery;

    @InjectMocks
    private final ActorsSearchController actorsSearchController = new ActorsSearchController();

    @Before
    public void before() throws SecurityException, NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {

        settingsService = mock(SettingsService.class);
        articleService = mock(JournalArticleLocalService.class);
        actroSolrQuery = mock(ActroSolrQuery.class);

        // mock creation
        actionRequest = mock(ActionRequest.class);
        actionResponse = mock(ActionResponse.class);

        renderRequest = mock(RenderRequest.class);
        renderResponse = mock(RenderResponse.class);
        model = mock(Model.class);

        sessionStatus = mock(SessionStatus.class);

        Field field = actorsSearchController.getClass().getDeclaredField("settingsService");
        field.setAccessible(true);
        field.set(actorsSearchController, settingsService);

        Field fieldArcticle = actorsSearchController.getClass().getDeclaredField("articleService");
        fieldArcticle.setAccessible(true);
        fieldArcticle.set(actorsSearchController, articleService);

        Field fieldActroSolrQuery = actorsSearchController.getClass().getDeclaredField("actroSolrQuery");
        fieldActroSolrQuery.setAccessible(true);
        fieldActroSolrQuery.set(actorsSearchController, actroSolrQuery);

    }

    @Test
    public void testView() throws Exception {

        ThemeDisplay themeDisplay = mock(ThemeDisplay.class);

        when(renderRequest.getAttribute(eq(WebKeys.THEME_DISPLAY))).thenReturn(themeDisplay);
        when(themeDisplay.getCompanyId()).thenReturn((long) 888);
        when(themeDisplay.getScopeGroupId()).thenReturn((long) 999);
        when(themeDisplay.getLanguageId()).thenReturn("222");
        when(
                articleService.getArticleContent(Mockito.anyLong(), Mockito.anyString(),
                        (String) Mockito.isNull(), Mockito.anyString(), (ThemeDisplay) Mockito.anyObject()))
                .thenReturn("Hello World");

        when(renderRequest.getParameter(eq("searchTerm"))).thenReturn("apa");

        actorsSearchController.showFormView(renderRequest, renderResponse, model);

        verify(model).addAttribute(eq("searchNoHitsArticleContent"), eq("Hello World"));

    }

    @Test
    public void testSearchActon() throws Exception {

        String searchTerm = "apa";

        actorsSearchController.search(actionRequest, actionResponse, sessionStatus, searchTerm, model);

        verify(actionResponse).sendRedirect(Mockito.anyString());

    }

    @Test
    public void testSearchAllActon() throws Exception {

        actorsSearchController.searchForAll(actionRequest, actionResponse, sessionStatus, model);

        verify(actionResponse).sendRedirect(Mockito.anyString());

    }
}
