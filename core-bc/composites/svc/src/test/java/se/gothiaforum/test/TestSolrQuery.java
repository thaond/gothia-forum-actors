/**
 * 
 */
package se.gothiaforum.test;

import static org.junit.Assert.assertEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import se.gothiaforum.solr.ActroSolrQuery;

/**
 * @author Simon Göransson vgrid=simgo3
 * 
 */
public class TestSolrQuery {
    private static final Log LOG = LogFactory.getLog(TestSolrQuery.class);

    @Before
    public void before() {

    }

    @Test
    public void testOfSolrQuerySerachForApa() throws Exception {

        ActroSolrQuery actroSolrQuery = new ActroSolrQuery();

        actroSolrQuery.actorQuery("apa");

        assertEquals(testResult1, actroSolrQuery.toString());

    }

    @Test
    public void testOfSolrQueryFindAll() throws Exception {

        ActroSolrQuery actroSolrQuery = new ActroSolrQuery();

        actroSolrQuery.findAllActorQuery();

        assertEquals(testResult2, actroSolrQuery.toString());

    }

    @Test
    public void testOfSolrQueryMoreLikeThia() throws Exception {

        ActroSolrQuery actroSolrQuery = new ActroSolrQuery();

        actroSolrQuery.moreLikeThis("104107");

        System.out.println(actroSolrQuery);

        assertEquals(testResult3, actroSolrQuery.toString());

    }

    private static final String testResult1 = "q=apa&qt=dismax&qf=assetTagNames%5E10+title%5E5+content%5E1&mm=50%25&fl=*";
    private static final String testResult2 = "q=entryClassName%3Acom.liferay.portlet.journal.model.JournalArticle";
    private static final String testResult3 = "qt=mlt&mlt.fl=assetTagNames%2Ccontent&mlt.bf=assetTagNames%5E10+content%5E1&mlt.interestingTerms=none&mlt.mintf=0&fl=title%2CurlTitle%2CentryClassPK%2Cstatus%2Ctype%2Ccreated%2Ccontent&rows=5";

}
