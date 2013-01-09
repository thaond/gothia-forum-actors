package se.gothiaforum.index;

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
        System.out.println(document + " " + o);
        document.addKeyword(Field.CONTENT, ((JournalArticle) o).getContent());
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
