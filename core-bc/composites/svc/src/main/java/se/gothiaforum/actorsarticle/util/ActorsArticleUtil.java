package se.gothiaforum.actorsarticle.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.FriendlyURLNormalizer;

/**
 * @author simgo3
 * 
 */
public class ActorsArticleUtil {
    private static final Log log = LogFactory.getLog(ActorsArticleUtil.class);

    public String generateUrlTitle(long articelId, String title) throws PortalException, SystemException {
        title = title.trim().toLowerCase();

        if (Validator.isNull(title) || Validator.isNumber(title) || title.equals(StringPool.BLANK)) {
            return String.valueOf(articelId);
        } else {
            String urlTitle = FriendlyURLNormalizer.normalize(title, _URL_TITLE_REPLACE_CHARS);
            return urlTitle;
        }
    }

    private static final char[] _URL_TITLE_REPLACE_CHARS = new char[] { '.', '/' };

}
