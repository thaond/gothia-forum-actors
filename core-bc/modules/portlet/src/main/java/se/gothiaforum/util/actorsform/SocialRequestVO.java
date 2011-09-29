package se.gothiaforum.util.actorsform;

import javax.portlet.PortletURL;

import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.SocialRequestFeedEntry;

public class SocialRequestVO {
		
	private SocialRequest socialRequest;

	private SocialRequestFeedEntry requestFeedEntry;
	
	public SocialRequest getSocialRequest() {
		return socialRequest;
	}

	public void setSocialRequest(SocialRequest socialRequest) {
		this.socialRequest = socialRequest;
	}

	public SocialRequestFeedEntry getRequestFeedEntry() {
		return requestFeedEntry;
	}

	public void setRequestFeedEntry(SocialRequestFeedEntry requestFeedEntry) {
		this.requestFeedEntry = requestFeedEntry;
	}	
		
}
