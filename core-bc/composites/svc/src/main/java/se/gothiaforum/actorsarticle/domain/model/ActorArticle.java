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
 * This Class is a model for the ActorArticle. This is the objects that is posted in from det the form to the
 * controller and then send to the service...
 * 
 * @author Hans Gyllensten, vgrid=hangy2
 * @author Simon Göransson vgrid=simgo3
 */
public class ActorArticle {

    private static final Log LOG = LogFactory.getLog(ActorArticle.class);

    /** The company name. */
    private String companyName = "";

    /** The organization name. */
    private String organizationName = "";

    /** The intro. */
    private String intro = "";

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

    private String tagsStr = "";

    private String articleId = "";

    private long primKeyId = 0;

    private long resourcePrimKeyId = 0;

    private int articleStatus = -1;

    private long groupId = 0;

    private String content = "no content";

    private String imageUuid = "";

    private String profileURL = "";

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getExternalHomepage() {
        return externalHomepage;
    }

    public void setExternalHomepage(String externalHomepage) {
        this.externalHomepage = externalHomepage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLogo() {
        return logo;
    }

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
        return imageUuid;
    }

    public void setImageUuid(String imageUuid) {
        this.imageUuid = imageUuid;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

}
