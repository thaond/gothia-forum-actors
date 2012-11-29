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

/**
 * 
 */
package se.gothiaforum.solr;

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
    public void testOfSolrQueryMoreLikeThis() throws Exception {

        ActroSolrQuery actroSolrQuery = new ActroSolrQuery();

        actroSolrQuery.moreLikeThis("104107");

        assertEquals(testResult3, actroSolrQuery.toString());

    }

    private static final String testResult1 = "q=apa&qt=dismax&qf=assetTagNames%5E1000+title%5E100+content%5E1&mm=50%25&fl=*&hl=true&hl.fl=*&hl.snippets=2&hl.mergeContiguous=true";
    private static final String testResult2 = "q=entryClassName%3Acom.liferay.portlet.journal.model.JournalArticle";
    private static final String testResult3 = "q=entryClassPK%3A104107&fq=entryClassName%3Acom.liferay.portlet.journal.model.JournalArticle+AND+type%3Aactor&qt=mlt&mlt.fl=assetTagNames+content&mlt.bf=assetTagNames%5E10+content%5E1&mlt.interestingTerms=none&mlt.mintf=0&fl=assetTagNames+content&rows=5";

}