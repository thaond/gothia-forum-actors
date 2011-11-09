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

package se.gothiaforum.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * This class is originally written by
 * 
 * @author Brian Wing Shun Chan
 */
public class FriendlyURLNormalizer {

    private static NormalizerWrapper normalizerWrapper = new NormalizerWrapper();

    public static String normalize(String friendlyURL) {
        return normalize(friendlyURL, null);
    }

    public static String normalize(String friendlyURL, char[] replaceChars) {
        if (Validator.isNull(friendlyURL)) {
            return friendlyURL;
        }

        friendlyURL = GetterUtil.getString(friendlyURL);
        friendlyURL = friendlyURL.toLowerCase();
        friendlyURL = normalizerWrapper.normalizeToAscii(friendlyURL);

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

        return friendlyURL;
    }

    public static void setNormalizerWrapper(NormalizerWrapper normalizerWrapper) {
        FriendlyURLNormalizer.normalizerWrapper = normalizerWrapper;
    }

    private static final char[] _REPLACE_CHARS = new char[] { ' ', ',', '\\', '\'', '\"', '(', ')', '{', '}', '?',
            '#', '@', '+', '~', ';', '$', '%' };

}