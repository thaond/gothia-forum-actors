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

package se.gothiaforum.actorsarticle.util;

/**
 * This is a constant file for actors.
 * 
 * @author simgo3
 */
public final class ActorsConstants {

    private ActorsConstants() {
        throw new UnsupportedOperationException();
    }

    public static final String GLOBAL_STRUCTRUEID = "GF_ACTOR_STRUCTURE"; // GF_SERVICE or GF_ACTOR_STRUCTURE
    public static final String GLOBAL_TEMPLATEID = "GF_ACTOR_TEMPLATE"; // GF_SERVICE or GF_ACTOR_TEMPLATE
    public static final String GLOBAL_LIST_TEMPLATEID = "GF_ACTOR_LIST";
    public static final String GLOBAL_MORE_LIKE_THIS_TEMPLATEID = "GF_MORE_LIKE_THIS";

    public static final String STRUCTRUEID = "GF_ACTOR"; // GF_SERVICE or GF_ACTOR_STRUCTURE
    public static final String TEMPLATEID = "GF_ACTOR"; // GF_SERVICE or GF_ACTOR_TEMPLATE

    public static final String PARENT_ORGANIZATION = "Gothia Parent Organization";
    public static final String ORGNIZATION_ACTOR_TYPE = "gothia-actor";

    public static final String ELEMENT_TYPE_TEXT = "text";
    public static final String ELEMENT_TYPE_TEXTAREA = "text_area";
    public static final String ELEMENT_TYPE_IMAGE_GALLERY = "image_gallery";

    public static final int PENDING = 1;
    public static final int DRAFT = 2;

    public static final String LOGO_FOLDER_NAME = "logotype";
    public static final String ACTOR_LOGO_NAME = "actor-logo";
    public static final String ACTOR_LOGO_HEIGHT_AND_WIDTH = "&height=40&width=200";

    public static final String SEARCH_RIDERECT_URL = "/sv/web/guest/sok-tjanst/-/s/";
    public static final String PROFILE_RIDERECT_URL = "/sv/web/guest/profil/-/a/";

    public static final int NUMBER_OF_NODES_ARTICLE_XML = 14;

    public static final String ARTICLE_XML_COMPANY_NAME = "actor-name";
    public static final String ARTICLE_XML_ORGANIZATION_NAME = "org-name";
    public static final String ARTICLE_XML_INTRO = "intro";
    public static final String ARTICLE_XML_DETAILED_DISCRIPTION = "description";
    public static final String ARTICLE_XML_HOMEPAGE = "contact-webpage";
    public static final String ARTICLE_XML_CONTACT_NAME = "contact-name";
    public static final String ARTICLE_XML_CONTACT_TITLE = "contact-title";
    public static final String ARTICLE_XML_PHONE = "contact-phone";
    public static final String ARTICLE_XML_FAX = "contact-fax";
    public static final String ARTICLE_XML_ADDRESS = "contact-address";
    public static final String ARTICLE_XML_MOBILE_PHONE = "contact-mobile";
    public static final String ARTICLE_XML_MAIL = "contact-email";
    public static final String ARTICLE_XML_LOGO = "logotype";
    public static final String ARTICLE_XML_IMAGES = "actor-images";

    public static final String ARTICLE_XML_DYNAMIC_ELEMENT = "dynamic-element";
    public static final String ARTICLE_XML_DYNAMIC_CONTENT = "dynamic-content";

    public static final String LISTTYPE_TYPE = "com.liferay.portal.model.Organization.status";

    public static final String TABS = "profile-tab,form-tab,user-tab";

    public static final String TYPE_ACTOR = "actor";

    public static final String UTF_8 = "UTF-8";

    public static final String ORGANIZATION_ADMIN = "Organization Administrator";

    public static final String ACTORS_ARTICLE_PK = "articleId"; // "articleId" = sp2 "entryClassPK" = sp1

    public static final String GROUP_ID = "groupId";

    public static final String LANG_PREFIX = "_SV";
    public static final String ACTOR_PARENT = "gothia-actor-parent";
}
