package se.gothiaforum.index2;

import com.liferay.portal.kernel.search.*;
import com.liferay.portlet.journal.model.JournalArticle;

import javax.portlet.PortletURL;
import java.util.Locale;

/**
 * @author Patrik Bergström
 */
public class CustomJournalIndexerPostProcessor implements IndexerPostProcessor {

    @Override
    public void postProcessContextQuery(BooleanQuery booleanQuery, SearchContext searchContext) throws Exception {
    }

    @Override
    public void postProcessDocument(Document document, Object o) throws Exception {
//todo        document.addKeyword(Field.CONTENT, "aslkdfjasökljdf");
        System.out.println(document + " " + o);
        JournalArticle journalArticle = (JournalArticle) o;
        String content = journalArticle.getContent();
        int beginIndex = content.indexOf("![CDATA[") + 8;
        content = content.substring(beginIndex, content.indexOf("]]", beginIndex));
        document.addKeyword(Field.CONTENT, content);
//        JournalArticleLocalServiceUtil.gadis
//        JournalArticleLocalServiceUtil.getArticleContent(journalArticle, "GF_ACTOR_LIST", null,
//                themeDisplay.getLanguageId(), themeDisplay)
//                JournalArticleLocalServiceUtil.getArticle()
    }

    @Override
    public void postProcessFullQuery(BooleanQuery booleanQuery, SearchContext searchContext) throws Exception {
    }

    @Override
    public void postProcessSearchQuery(BooleanQuery booleanQuery, SearchContext searchContext) throws Exception {
    }

    @Override
    public void postProcessSummary(Summary summary, Document document, Locale locale, String s, PortletURL portletURL) {
    }
}
