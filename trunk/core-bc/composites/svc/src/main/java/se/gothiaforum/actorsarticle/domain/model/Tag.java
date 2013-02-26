/**
 * 
 */
package se.gothiaforum.actorsarticle.domain.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Simon Göransson - simon.goransson@monator.com - vgrid: simgo3
 * 
 */
public class Tag {

    private String name;
    private String link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    private static final Log LOG = LogFactory.getLog(Tag.class);
}
