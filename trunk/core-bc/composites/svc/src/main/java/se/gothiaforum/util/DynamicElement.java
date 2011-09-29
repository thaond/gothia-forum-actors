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
