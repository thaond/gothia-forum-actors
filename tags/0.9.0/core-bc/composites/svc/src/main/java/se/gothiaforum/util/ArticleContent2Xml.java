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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author simgo3
 * 
 */
public class ArticleContent2Xml {
    private static final Log log = LogFactory.getLog(ArticleContent2Xml.class);
    private StringBuffer content = null;
    private final static String STARTTEXT = "<?xml version=\"1.0\"?><root>";
    private final static String ENDTEXT = "</root>";

    public ArticleContent2Xml() {
        super();
        content = new StringBuffer();
        content.append(STARTTEXT);
    }

    public void dynamicElemnet(String id, String name, String type, String value) {
        StringBuffer tempBuffer = new StringBuffer();

        tempBuffer.append("<dynamic-element instance-id=\"");
        tempBuffer.append(id);
        tempBuffer.append("\" name=\"");
        tempBuffer.append(name);
        tempBuffer.append("\" type=\"");
        tempBuffer.append(type);
        tempBuffer.append("\" index-type=\"\">");
        tempBuffer.append("<dynamic-content><![CDATA[");
        tempBuffer.append(value);
        tempBuffer.append("]]></dynamic-content></dynamic-element>");

        content.append(tempBuffer.toString());

    }

    public void dynamicElemnetStart(String id, String name, String type) {
        StringBuffer tempBuffer = new StringBuffer();

        tempBuffer.append("<dynamic-element instance-id=\"");
        tempBuffer.append(id);
        tempBuffer.append("\" name=\"");
        tempBuffer.append(name);
        tempBuffer.append("\" type=\"");
        tempBuffer.append(type);
        tempBuffer.append("\" index-type=\"\">");

        content.append(tempBuffer.toString());

    }

    public void dynamicElemnetEnd(String value) {
        StringBuffer tempBuffer = new StringBuffer();

        tempBuffer.append("<dynamic-content><![CDATA[");
        tempBuffer.append(value);
        tempBuffer.append("]]></dynamic-content></dynamic-element>");

        content.append(tempBuffer.toString());

    }

    public String end() {

        /*
         * if (content.indexOf(STARTTEXT) < 0) { // This should never happen. throw new
         * RuntimeException("illegal state in " + this.getClass().getName() + " expected string to contain " +
         * STARTTEXT); }
         */
        String result = content.append(ENDTEXT).toString();
        content = new StringBuffer(); // Clear content. <this prevent wrong use of end

        return result;

    }

}
