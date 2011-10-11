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

package se.gothiaforum.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liferay.portal.kernel.search.Field;

public class ActroSolrQuery extends SolrQuery {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ActroSolrQuery.class);

	private SolrServer solrServer;

	public ActroSolrQuery(SolrServer solrServer) {
		this.solrServer = solrServer;
	}

	public ActroSolrQuery() {
		super();
	}

	public ActroSolrQuery(String s) {
		super(s);
	}

	public ActroSolrQuery findAllActorQuery() {
		this.setQuery("entryClassName:com.liferay.portlet.journal.model.JournalArticle");
		return this;
	}

	public ActroSolrQuery actorQuery(String q) {
		this.setQuery(q);
		this.setQueryType("dismax");
		this.set("qf", "assetTagNames^10 title^5 content^1");
		this.set("mm", "50%");
		return this;
	}

	public ActroSolrQuery moreLikeThis() {
		this.setQueryType("mlt");
		this.set("mlt.fl", "assetTagNames,content");
		this.set("mlt.bf", "assetTagNames^10 content^1");
		this.set("mlt.interestingTerms", "none");
		this.set("mlt.mintf", 0);
		this.set("fl", "title,urlTitle,entryClassPK,status,type,created,content");
		this.set("rows", "5");
		return this;
	}

	public ActroSolrQuery filterActors(String category) {
		String filterBase = "entryClassName:com.liferay.portlet.journal.model.JournalArticle";

		String filterType = "";

		filterType += " AND " + Field.TYPE + ":actor";

		this.addFilterQuery(filterBase + filterType);

		return this;
	}

	public QueryResponse query() {

		// this.setSortField(sortField,
		// ORDER.valueOf(sortDirection.name().toLowerCase(CommonUtils.SWEDISH_LOCALE)));

		System.out.println("Query = " + this);

		QueryResponse response = null;

		try {
			response = solrServer.query(this);
		} catch (SolrServerException e) {
			LOGGER.error("Serverfel: {}", e.getCause());
		}

		// Clear query for next query
		clear();

		return response;

	}

}
