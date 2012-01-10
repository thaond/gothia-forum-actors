package se.gothiaforum.util;

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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import se.gothiaforum.actorsarticle.util.ActorsConstants;

import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.xml.SAXReaderImpl;

public class TestXMLParsing {

    @Before
    public void before() {

        SAXReaderUtil sax = new SAXReaderUtil();

        SAXReader saxRead = new SAXReaderImpl();

        sax.setSAXReader(saxRead);

    }

    @Test
    public void testOfDynamicElement() throws Exception {

        String name = "testname1";
        String value = "apa";

        ArticleContentXml articleContentXml = new ArticleContentXml();

        articleContentXml.dynamicElemnet("1", name, ActorsConstants.ELEMENT_TYPE_TEXT, value);

        String content = articleContentXml.end();

        DynamicArticleHandler dynamicArticleHandler = new DynamicArticleHandler();

        DynamicElement dynamicElement = dynamicArticleHandler.generateDynamicElements(content);

        String resultValue = dynamicElement.getElementByName(name).getContent();

        assertEquals(value, resultValue);
    }

    @Test
    public void testOfDynamicElement2() throws Exception {

        String name1 = "testname1";
        String value1 = "apa";

        String name2 = "testname2";
        String value2 = "bepa";

        String name3 = "testname3";
        String value3 = "cepa";

        ArticleContentXml articleContentXml = new ArticleContentXml();

        articleContentXml.dynamicElemnet("1", name1, ActorsConstants.ELEMENT_TYPE_TEXT, value1);
        articleContentXml.dynamicElemnet("2", name2, ActorsConstants.ELEMENT_TYPE_TEXT, value2);
        articleContentXml.dynamicElemnet("3", name3, ActorsConstants.ELEMENT_TYPE_TEXT, value3);

        String content = articleContentXml.end();

        DynamicArticleHandler dynamicArticleHandler = new DynamicArticleHandler();

        DynamicElement dynamicElement = dynamicArticleHandler.generateDynamicElements(content);

        String resultValue1 = dynamicElement.getElementByName(name1).getContent();
        String resultValue2 = dynamicElement.getElementByName(name2).getContent();
        String resultValue3 = dynamicElement.getElementByName(name3).getContent();

        assertEquals(value1, resultValue1);
        assertEquals(value2, resultValue2);
        assertEquals(value3, resultValue3);

    }

    @Test
    public void testOfDynamicElement3() throws Exception {

        String name = "testname1";
        String value = "apa";

        DynamicArticleHandler dynamicArticleHandler = new DynamicArticleHandler();

        DynamicElement dynamicElement = dynamicArticleHandler.generateDynamicElements(testXml);

        String resultValue = dynamicElement.getElementByName(name).getContent();

        assertEquals(value, resultValue);

    }

    private static final String testXml = "<?xml version=\"1.0\"?>" + "<root>"
            + "<dynamic-element instance-id=\"1\" name=\"testname1\" type=\"text\" index-type=\"\">"
            + "<dynamic-content><![CDATA[apa]]></dynamic-content>" + "</dynamic-element>" + "</root>";

}
