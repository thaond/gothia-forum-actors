/**
 * 
 */
package se.gothiaforum.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.util.Normalizer;

/**
 * @author simongoransson
 * 
 */
public class NormalizerWrapper {
    private static final Log LOG = LogFactory.getLog(NormalizerWrapper.class);

    public String normalizeToAscii(String s) {

        return toAscii(s);
    }

    private String toAscii(String s) {
        return Normalizer.normalizeToAscii(s);
    }

}
