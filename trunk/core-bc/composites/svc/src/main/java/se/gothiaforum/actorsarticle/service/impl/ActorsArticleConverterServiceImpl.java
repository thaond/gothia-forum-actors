package se.gothiaforum.actorsarticle.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.gothiaforum.actorsarticle.domain.model.ActorArticle;
import se.gothiaforum.actorsarticle.service.ActorsArticleConverterService;
import se.gothiaforum.actorsarticle.util.ActorsConstants;
import se.gothiaforum.util.DynamicArticleHandler;
import se.gothiaforum.util.DynamicElement;

/**
 * @author Hans Gyllensten, vgrid=hangy2
 * 
 */
public class ActorsArticleConverterServiceImpl implements ActorsArticleConverterService {

    private static final Log log = LogFactory.getLog(ActorsArticleConverterServiceImpl.class);

    public ActorsArticleConverterServiceImpl() {
        init();
    }

    /**
	 * 
	 */
    protected void init() {

    }

    /**
     * @inheritDoc
     */
    @Override
    public ActorArticle getActorsArticle(String actorsArticleAsXML) {
        DynamicArticleHandler dynamicArticleHandler = new DynamicArticleHandler();
        DynamicElement dynamicElement = dynamicArticleHandler.generateDynamicElements(actorsArticleAsXML);

        ActorArticle actorArticle = new ActorArticle();

        if (dynamicElement.getChildNodes().size() != ActorsConstants.NUMBER_OF_NODES_ARTICLE_XML) {
            String msg = "Expected that the arcticle xml would countain "
                    + ActorsConstants.NUMBER_OF_NODES_ARTICLE_XML + " number of nodes but it contains "
                    + dynamicElement.getChildNodes().size() + " number of nodes.";
            RuntimeException e = new RuntimeException(msg);
            log.error(msg, e);
            throw e;
        }

        actorArticle.setCompanyName(dynamicElement.getElementByName(ActorsConstants.ARTICLE_XML_COMPANY_NAME)
                .getContent());
        actorArticle.setOrganizationName(dynamicElement.getElementByName(
                ActorsConstants.ARTICLE_XML_ORGANIZATION_NAME).getContent());
        actorArticle.setIngress(dynamicElement.getElementByName(ActorsConstants.ARTICLE_XML_INGRESS).getContent());
        actorArticle.setDetailedDescription(dynamicElement.getElementByName(
                ActorsConstants.ARTICLE_XML_DETAILED_DISCRIPTION).getContent());
        actorArticle.setExternalHomepage(dynamicElement.getElementByName(ActorsConstants.ARTICLE_XML_HOMEPAGE)
                .getContent());
        actorArticle.setName(dynamicElement.getElementByName(ActorsConstants.ARTICLE_XML_CONTACT_NAME)
                .getContent());
        actorArticle.setTitle(dynamicElement.getElementByName(ActorsConstants.ARTICLE_XML_CONTACT_TITLE)
                .getContent());
        actorArticle.setAddress(dynamicElement.getElementByName(ActorsConstants.ARTICLE_XML_ADDRESS).getContent());
        actorArticle.setPhone(dynamicElement.getElementByName(ActorsConstants.ARTICLE_XML_PHONE).getContent());
        actorArticle.setMobilePhone(dynamicElement.getElementByName(ActorsConstants.ARTICLE_XML_MOBILE_PHONE)
                .getContent());
        actorArticle.setFax(dynamicElement.getElementByName(ActorsConstants.ARTICLE_XML_FAX).getContent());
        actorArticle.setMail(dynamicElement.getElementByName(ActorsConstants.ARTICLE_XML_MAIL).getContent());
        actorArticle.setLogo(dynamicElement.getElementByName(ActorsConstants.ARTICLE_XML_LOGO).getContent());

        return actorArticle;
    }
}
