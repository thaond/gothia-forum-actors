package se.gothiaforum.portlet.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ValidatorException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import se.gothiaforum.portlet.service.PortletService;

@Service
public class PortletServiceImpl implements PortletService {

	@Override
	public String renderPortlet(PortletRequest request, PortletResponse response, String portletId, String queryString) throws SystemException, IOException, ServletException, ValidatorException {
		
        // Get servlet request / response
        HttpServletRequest servletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
        HttpServletResponse servletResponse = PortalUtil.getHttpServletResponse(response);
        // Get theme display
        final ThemeDisplay themeDisplay = (ThemeDisplay) servletRequest.getAttribute(WebKeys.THEME_DISPLAY);
        // Backup current state
        PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
        PortletDisplay portletDisplayClone = new PortletDisplay();
        portletDisplay.copyTo(portletDisplayClone);
        final Map<String, Object> requestAttributeBackup = new HashMap<String, Object>();
        for (final String key : Collections.list((Enumeration<String>) servletRequest.getAttributeNames())) {
            requestAttributeBackup.put(key, servletRequest.getAttribute(key));
        }
        // Render the portlet as a runtime portlet
        String result;
        try {
            PortletBag implPortletBag = PortletBagPool.get(PortletKeys.JOURNAL);
            com.liferay.portal.model.Portlet portlet = PortletLocalServiceUtil.getPortletById(PortalUtil.getCompanyId(request), portletId);
            servletRequest.setAttribute(WebKeys.RENDER_PORTLET_RESOURCE, Boolean.TRUE);
            result = PortalUtil.renderPortlet(implPortletBag.getServletContext(), servletRequest, servletResponse, portlet, queryString, false);
        } finally {
            // Restore the state
            portletDisplay.copyFrom(portletDisplayClone);
            portletDisplayClone.recycle();
            for (final String key : Collections.list((Enumeration<String>) servletRequest.getAttributeNames())) {
                if (!requestAttributeBackup.containsKey(key)) {
                    servletRequest.removeAttribute(key);
                }
            }
            for (final Map.Entry<String, Object> entry : requestAttributeBackup.entrySet()) {
                servletRequest.setAttribute(entry.getKey(), entry.getValue());
            }
        }
        return result;
		
		
	}
	
}
