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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is an representation of a dynamic element that is the type of element that the content of an article
 * is stored as.
 * 
 * @author simgo3
 * 
 */
public class DynamicElement {
    private static final Log LOG = LogFactory.getLog(DynamicElement.class);

    private String name;
    private String content;
    private List<DynamicElement> childNodes;

    /**
     * Instantiates a new dynamic element.
     * 
     * @param name
     *            the name
     * @param content
     *            the content
     * @param childNodes
     *            the child nodes
     */
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

    /**
     * Gets the element by the name.
     * 
     * @param nameToFind
     *            the name to find
     * @return the element by name
     */
    public DynamicElement getElementByName(String nameToFind) {
        if (this.name.equals(nameToFind)) {
            return this;
        }

        for (DynamicElement e : childNodes) {
            if (e.getName().equals(nameToFind)) {
                return e;
            }
        }

        return null;
    }

    @Override
    public String toString() {

        StringBuffer str = new StringBuffer("Content: " + content + "\n");

        for (DynamicElement n : childNodes) {
            str.append(n.toString());
        }

        return str.toString();
    }
}
