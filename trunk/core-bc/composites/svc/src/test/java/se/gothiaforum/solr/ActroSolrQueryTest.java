package se.gothiaforum.solr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActroSolrQueryTest {

    @Test
    public void testBuildQueryString() throws Exception {
        String query = ActroSolrQuery.buildQueryString("searchText");

        assertEquals("content:searchText OR title:searchText OR assetTagNames:searchText OR description:searchText OR "
                + "actor-name:searchText OR org-name:searchText OR intro:searchText", query);
    }
}