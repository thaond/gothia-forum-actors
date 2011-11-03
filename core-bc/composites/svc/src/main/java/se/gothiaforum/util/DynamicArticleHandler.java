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

import java.util.ArrayList;
import java.util.List;

import se.gothiaforum.actorsarticle.util.ActorsConstants;

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

/**
 * This class generate the dynamic element representation from the XML string.
 * 
 * @author simgo3
 */
public class DynamicArticleHandler {

    /**
     * Generate dynamic elements from an XML string.
     * 
     * @param content
     *            the content
     * @return the dynamic element
     */
    public DynamicElement generateDynamicElements(String content) {

        DynamicElement rootDynamicElement = null;

        try {
            if (content != null) {
                Document document;

                document = SAXReaderUtil.read(content);

                Element element = document.getRootElement();

                // Språk
                // kolla att det finns en struktur

                // SAXReaderUtil.selectNodes("[/@name=title]", document.content());

                List<Element> elements = element.elements(ActorsConstants.ARTICLE_XML_DYNAMIC_ELEMENT);
                String elementContent = element.elementText(ActorsConstants.ARTICLE_XML_DYNAMIC_ELEMENT);

                rootDynamicElement = new DynamicElement(element.getName(), elementContent,
                        generateDynamicElement(elements));
            }

        } catch (DocumentException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        }

        return rootDynamicElement;
    }

    private List<DynamicElement> generateDynamicElement(List<Element> elements) {

        List<DynamicElement> dynamicElementList = new ArrayList<DynamicElement>();

        for (Element e : elements) {
            List<Element> childElements = e.elements(ActorsConstants.ARTICLE_XML_DYNAMIC_ELEMENT);
            String content = e.elementText(ActorsConstants.ARTICLE_XML_DYNAMIC_CONTENT);
            String name = e.attributeValue("name");

            DynamicElement dynamicElement;

            if (childElements.size() > 0) {
                dynamicElement = new DynamicElement(name, content, generateDynamicElement(childElements));
            } else {
                dynamicElement = new DynamicElement(name, content, new ArrayList<DynamicElement>());
            }

            dynamicElementList.add(dynamicElement);
        }
        return dynamicElementList;
    }

}
