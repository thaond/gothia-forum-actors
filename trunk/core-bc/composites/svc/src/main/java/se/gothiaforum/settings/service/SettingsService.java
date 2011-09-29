package se.gothiaforum.settings.service;

import com.liferay.portal.model.Group;

/**
 * The Interface settingsService.
 * 
 * @author simgo3
 * 
 *         Manage the settings for Gothia Forum.
 */

public interface SettingsService {
	
	public void setSetting(String data, String columnName, long companyId, long groupId);
	
	public String getSetting(String columnName, long companyId, long groupId);
	
	

}
