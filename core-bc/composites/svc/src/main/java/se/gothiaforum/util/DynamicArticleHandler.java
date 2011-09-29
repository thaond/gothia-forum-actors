package se.gothiaforum.util;

import java.util.ArrayList;
import java.util.List;

import se.gothiaforum.actorsarticle.util.ActorsConstants;

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

/**
 * @author simgo3
 * 
 */
public class DynamicArticleHandler {

    public DynamicElement generateDynamicElements(String content) {

        DynamicElement rootDynamicElement = null;

        try {
            if (content != null) {
                Document document;

                document = SAXReaderUtil.read(content);

                Element element = document.getRootElement();

                // Språk
                // kolla att det finns en struktur

                //   SAXReaderUtil.selectNodes("[/@name=title]", document.content());

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
