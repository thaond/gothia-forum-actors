package se.gothiaforum.index;

import java.util.Locale;

import javax.portlet.PortletURL;

import se.gothiaforum.actorsarticle.domain.model.ActorArticle;
import se.gothiaforum.actorsarticle.service.ActorsArticleConverterService;
import se.gothiaforum.actorsarticle.service.impl.ActorsArticleConverterServiceImpl;
import se.gothiaforum.actorsarticle.util.ActorsConstants;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portlet.journal.model.JournalArticle;

/**
 * @author Patrik Bergström
 */
public class CustomJournalIndexerPostProcessor implements IndexerPostProcessor {

    ActorsArticleConverterService actorsArticleConverterService = new ActorsArticleConverterServiceImpl();

    @Override
    public void postProcessContextQuery(BooleanQuery booleanQuery, SearchContext searchContext)
            throws Exception {
    }

    @Override
    public void postProcessDocument(Document document, Object o) throws Exception {

        JournalArticle article = ((JournalArticle) o);

        if (article.getType().equals("actor")) {
            // System.out.println(document + " " + o);
            // System.out.println("document content " + ((JournalArticle) o).getContent());
            ActorArticle actorArticle = actorsArticleConverterService.getActorsArticle(article.getContent());

            // System.out.println("-------------------------------------------------------------------");
            // System.out.println("actorArticle.getTitle " + article.getTitle());
            // System.out.println("actorArticle.getIntro " + actorArticle.getIntro());
            // System.out.println("actorArticle.getDetailedDescription() "
            // + actorArticle.getDetailedDescription());
            // System.out.println("actorArticle.getTagStr " + actorArticle.getTagsStr());
            // System.out.println("-------------------------------------------------------------------");

            document.addKeyword(Field.TITLE, removeSuffix(article.getTitle()));
            document.addKeyword(ActorsConstants.ARTICLE_XML_COMPANY_NAME, actorArticle.getName());
            document.addKeyword(ActorsConstants.ARTICLE_XML_ORGANIZATION_NAME,
                    actorArticle.getOrganizationName());
            document.addKeyword(ActorsConstants.ARTICLE_XML_INTRO, actorArticle.getIntro());
            document.addKeyword(ActorsConstants.ARTICLE_XML_DETAILED_DISCRIPTION,
                    actorArticle.getDetailedDescription());
            document.addKeyword(Field.CONTENT, "");
            document.addKeyword("content_en_US", "");
        }

    }

    @Override
    public void postProcessFullQuery(BooleanQuery booleanQuery, SearchContext searchContext) throws Exception {
    }

    @Override
    public void postProcessSearchQuery(BooleanQuery booleanQuery, SearchContext searchContext)
            throws Exception {
    }

    @Override
    public void postProcessSummary(Summary summary, Document document, Locale locale, String s,
            PortletURL portletURL) {
    }

    private String removeSuffix(String string) {
        if (string.toLowerCase().endsWith("_sv") || string.toLowerCase().endsWith("_en")) {
            string = string.substring(0, string.length() - 3);
        }
        return string;
    }
}
