package se.gothiaforum.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author simgo3
 * 
 */
public class DynamicElement {
    private static final Log log = LogFactory.getLog(DynamicElement.class);

    private String name;
    private String content;
    private List<DynamicElement> childNodes;

    public DynamicElement(String name, String content, List<DynamicElement> childNodes) {
        super();
        this.name = name;
        this.content = content;
        this.childNodes = childNodes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<DynamicElement> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<DynamicElement> childNodes) {
        this.childNodes = childNodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DynamicElement getElementByName(String name) {
        if (this.name.equals(name)) {
            return this;
        }

        for (DynamicElement e : childNodes) {
            if (e.getName().equals(name)) {
                return e;
            }
        }

        return null;
    }

    @Override
    public String toString() {

        String str = "Content: " + content + "\n";

        for (DynamicElement n : childNodes) {
            str += n.toString();
        }

        return str;
    }

}
