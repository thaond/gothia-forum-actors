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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.gothiaforum.util.FriendlyURLNormalizer;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

/**
 * This is a util class for actoraricles.
 * 
 * @author simgo3
 */
public class ActorsArticleUtil {
    private static final Log LOG = LogFactory.getLog(ActorsArticleUtil.class);

    /**
     * Generate an url title.
     * 
     * @param articelId
     *            the articel id
     * @param title
     *            the title
     * @return URL safe title
     * @throws PortalException
     *             the portal exception
     * @throws SystemException
     *             the system exception
     */
    public String generateUrlTitle(long articelId, String title) throws PortalException, SystemException {
        title = title.trim().toLowerCase();

        if (Validator.isNull(title) || Validator.isNumber(title) || title.equals(StringPool.BLANK)) {
            return String.valueOf(articelId);
        } else {
            String urlTitle = FriendlyURLNormalizer.normalize(title, URL_TITLE_REPLACE_CHARS);
            return urlTitle;
        }
    }

    private static final char[] URL_TITLE_REPLACE_CHARS = new char[] { '.', '/' };

}
