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

package se.gothiaforum.settings.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.gothiaforum.actorsarticle.util.ActorsConstants;

/**
 * This class holds constants for the settings service.
 * 
 * @author simgo3
 */
public final class ExpandoConstants {
    private static final Logger LOG = LoggerFactory.getLogger(ActorsConstants.class);

    private ExpandoConstants() {
        throw new UnsupportedOperationException();
    }

    // Language
    public static final String GOTHIA_SITE_LANGUAGE = "gothiaSiteLanguage";
    
    // Header
    public static final String GOTHIA_HEADER_TOP_NAVIGATION_ARTICLE_ID = "top-nav-article-id";

    // Footer
    public static final String GOTHIA_FOOTER_ARTICLE_ID1 = "footer-col-1-article-id";
    public static final String GOTHIA_FOOTER_ARTICLE_ID2 = "footer-col-2-article-id";
    public static final String GOTHIA_FOOTER_ARTICLE_ID3 = "footer-col-3-article-id";
    public static final String GOTHIA_FOOTER_ARTICLE_ID4 = "gothiaFooterArticleId4";

    // Search client portet
    public static final String GOTHIA_BANNER_ARTICLE = "thinSearchClientBannerArticleId";
    public static final String GOTHIA_THIN_SEARCH_ARTICLE = "thinSearchClientArticleId";

    // Search portlet
    public static final String GOTHIA_SEARCH_ARTICLE = "searchArticleId";
    public static final String GOTHIA_SEARCH_FITST_TIME_ARTICLE = "searchFirstTimeArticleId";
    public static final String GOTHIA_SEARCH_NO_HITS_ARTICLE = "searchNoHitsArticleId";

    // Form portlet
    public static final String GOTHIA_SOCIAL_REQUEST_SENT = "socialRequestSentArticleId";
    public static final String GOTHIA_FIRST_TIME = "fistTimeArticleId";

    //
    public static final String GOTHIA_SHOW_USER_PAGES = "showUserPages";
}
