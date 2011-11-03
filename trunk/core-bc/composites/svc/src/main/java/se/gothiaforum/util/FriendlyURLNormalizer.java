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

package se.gothiaforum.util;

/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.Normalizer;

/**
 * @author Brian Wing Shun Chan
 */
public class FriendlyURLNormalizer {

    public static String normalize(String friendlyURL) {
        return normalize(friendlyURL, null);
    }

    public static String normalize(String friendlyURL, char[] replaceChars) {
        if (Validator.isNull(friendlyURL)) {
            return friendlyURL;
        }

        friendlyURL = GetterUtil.getString(friendlyURL);
        friendlyURL = friendlyURL.toLowerCase();
        friendlyURL = Normalizer.normalizeToAscii(friendlyURL);

        char[] chars = friendlyURL.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char oldChar = chars[i];

            char newChar = oldChar;

            if (ArrayUtil.contains(_REPLACE_CHARS, oldChar)
                    || ((replaceChars != null) && ArrayUtil.contains(replaceChars, oldChar))) {

                newChar = CharPool.DASH;
            }

            if (oldChar != newChar) {
                chars[i] = newChar;
            }
        }

        friendlyURL = new String(chars);

        while (friendlyURL.contains(StringPool.DASH + StringPool.DASH)) {
            friendlyURL = StringUtil.replace(friendlyURL, StringPool.DASH + StringPool.DASH, StringPool.DASH);
        }

        /*
         * if (friendlyURL.startsWith(StringPool.DASH)) { friendlyURL = friendlyURL.substring(1,
         * friendlyURL.length()); }
         * 
         * if (friendlyURL.endsWith(StringPool.DASH)) { friendlyURL = friendlyURL.substring(0, friendlyURL.length()
         * - 1); }
         */

        return friendlyURL;
    }

    private static final char[] _REPLACE_CHARS = new char[] { ' ', ',', '\\', '\'', '\"', '(', ')', '{', '}', '?',
            '#', '@', '+', '~', ';', '$', '%' };

}