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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is a help class that helps to create the XML for the content field on an journal article that
 * describes the content of an article. The class it self has a StringBuffer that you can append dynamic elements
 * to in different combinations to create your representation of an article. When finish whit the XML use the end
 * method to receive a string representation of the XML.
 * 
 * @author simgo3
 */
public class ArticleContentXml {
    private static final Logger LOG = LoggerFactory.getLogger(ArticleContentXml.class);
    private StringBuffer content = null;
    private static final String STARTTEXT = "<?xml version=\"1.0\"?><root>";
    private static final String ENDTEXT = "</root>";

    /**
     * Instantiates a new ArticleContentXml.
     */
    public ArticleContentXml() {
        super();
        content = new StringBuffer();
        content.append(STARTTEXT);
    }

    /**
     * This method appends an entire dynamic element to the xml.
     * 
     * @param id
     *            the id
     * @param name
     *            the name
     * @param type
     *            the type
     * @param value
     *            the value
     */
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

    /**
     * This method appends an first part of an dynamic element to the xml. Use together with the dynamicElemnetEnd
     * method.
     * 
     * @param id
     *            the id
     * @param name
     *            the name
     * @param type
     *            the type
     */
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

    /**
     * This method appends an end part of an dynamic element to the xml. Use together with the dynamicElemnetStart
     * method.
     * 
     * @param value
     *            the value
     */
    public void dynamicElemnetEnd(String value) {
        StringBuffer tempBuffer = new StringBuffer();

        tempBuffer.append("<dynamic-content><![CDATA[");
        tempBuffer.append(value);
        tempBuffer.append("]]></dynamic-content></dynamic-element>");

        content.append(tempBuffer.toString());

    }

    /**
     * This method ends the xml and returns it as an string.
     * 
     * @return the string representation of the xml.
     */
    public String end() {

        String result = content.append(ENDTEXT).toString();
        content = new StringBuffer(); // Clear content. <this prevent wrong use of end

        return result;

    }

}
