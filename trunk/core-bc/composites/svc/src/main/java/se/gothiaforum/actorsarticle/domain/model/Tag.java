/**
 * 
 */
package se.gothiaforum.actorsarticle.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOG = LoggerFactory.getLogger(Tag.class);
}
