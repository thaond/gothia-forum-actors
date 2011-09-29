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

package se.gothiaforum.actorsarticle.domain.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class ActorArticle.
 * 
 * @author Hans Gyllensten, vgrid=hangy2
 * @author Simon Göransson vgrid=simgo3
 */
public class ActorArticle {

    /** The Constant log. */
    private static final Log log = LogFactory.getLog(ActorArticle.class);

    /** The company name. */
    private String companyName = "";

    /** The organization name. */
    private String organizationName = "";

    /** The ingress. */
    private String ingress = "";

    /** The detailed description. */
    private String detailedDescription = "";

    /** The external homepage. */
    private String externalHomepage = "";

    /** The name. */
    private String name = "";

    /** The title. */
    private String title = "";

    /** The address. */
    private String address = "";

    /** The phone. */
    private String phone = "";

    /** The mobile phone. */
    private String mobilePhone = "";

    /** The fax. */
    private String fax = "";

    /** The mail. */
    private String mail = "";

    private String contactWeb = "";

    /** The logo. */
    private String logo;

    // private List<AssetTag> tags = new ArrayList<AssetTag>();

    private String tagsStr = "";

    private String articleId = "";

    private long primKeyId = 0;

    private long resourcePrimKeyId = 0;

    private int articleStatus = -1;

    private long groupId = 0;

    private String content = "no content";

    private String ImageUuid = "";

    private String profileURL = "";

    /**
     * Sets the company name.
     * 
     * @param companyName
     *            the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the company name.
     * 
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Gets the organization name.
     * 
     * @return the organization name
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Sets the organization name.
     * 
     * @param organizationName
     *            the new organization name
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * Gets the ingress.
     * 
     * @return the ingress
     */
    public String getIngress() {
        return ingress;
    }

    /**
     * Sets the ingress.
     * 
     * @param ingress
     *            the new ingress
     */
    public void setIngress(String ingress) {
        this.ingress = ingress;
    }

    /**
     * Gets the detailed description.
     * 
     * @return the detailed description
     */
    public String getDetailedDescription() {
        return detailedDescription;
    }

    /**
     * Sets the detailed description.
     * 
     * @param detailedDescription
     *            the new detailed description
     */
    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    /**
     * Gets the external homepage.
     * 
     * @return the external homepage
     */
    public String getExternalHomepage() {
        return externalHomepage;
    }

    /**
     * Sets the external homepage.
     * 
     * @param externalHomepage
     *            the new external homepage
     */
    public void setExternalHomepage(String externalHomepage) {
        this.externalHomepage = externalHomepage;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * 
     * @param title
     *            the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the address.
     * 
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     * 
     * @param address
     *            the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the phone.
     * 
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone.
     * 
     * @param phone
     *            the new phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the mobile phone.
     * 
     * @return the mobile phone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Sets the mobile phone.
     * 
     * @param mobilePhone
     *            the new mobile phone
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * Gets the fax.
     * 
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * Sets the fax.
     * 
     * @param fax
     *            the new fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Gets the mail.
     * 
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * Sets the mail.
     * 
     * @param mail
     *            the new mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Gets the logo.
     * 
     * @return the logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * Sets the logo.
     * 
     * @param logo
     *            the new logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTagsStr() {
        return tagsStr;
    }

    public void setTagsStr(String tagsStr) {
        this.tagsStr = tagsStr;
    }

    public long getResourcePrimKeyId() {
        return resourcePrimKeyId;
    }

    public void setResourcePrimKeyId(long resourcePrimKeyId) {
        this.resourcePrimKeyId = resourcePrimKeyId;
    }

    public String getContactWeb() {
        return contactWeb;
    }

    public void setContactWeb(String contactWeb) {
        this.contactWeb = contactWeb;
    }

    public long getPrimKeyId() {
        return primKeyId;
    }

    public void setPrimKeyId(long primKeyId) {
        this.primKeyId = primKeyId;
    }

    public int getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(int articleStatus) {
        this.articleStatus = articleStatus;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUuid() {
        return ImageUuid;
    }

    public void setImageUuid(String imageUuid) {
        ImageUuid = imageUuid;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

}
