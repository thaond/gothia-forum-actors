/**
 * 
 */
package se.gothiaforum.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.gothiaforum.util.NormalizerWrapper;

/**
 * @author simongoransson
 * 
 */
public class NormalizerWrapperMock extends NormalizerWrapper {
    private static final Log LOG = LogFactory.getLog(NormalizerWrapperMock.class);

    /*
     * (non-Javadoc)
     * 
     * @see se.gothiaforum.util.NormalizerWrapper#normalizeToAscii(java.lang.String)
     */
    @Override
    public String normalizeToAscii(String s) {

        return "test";
    }

}
