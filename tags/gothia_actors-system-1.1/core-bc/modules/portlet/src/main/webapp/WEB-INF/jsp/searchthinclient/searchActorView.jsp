<%--

    Copyright 2010 Västra Götalandsregionen

      This library is free software; you can redistribute it and/or modify
      it under the terms of version 2.1 of the GNU Lesser General Public
      License as published by the Free Software Foundation.

      This library is distributed in the hope that it will be useful,
      but WITHOUT ANY WARRANTY; without even the implied warranty of
      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
      GNU Lesser General Public License for more details.

      You should have received a copy of the GNU Lesser General Public
      License along with this library; if not, write to the
      Free Software Foundation, Inc., 59 Temple Place, Suite 330,
      Boston, MA 02111-1307  USA


--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<portlet:actionURL name="search" var="searchActorURL"/>

<div class="search-box">
    <div class="search-box-hd">      
    	${bannerArticleHtml}
    </div>
    <div class="search-box-bd">            
        <div class="search-wrap">
			<div class="search-intro">
				<p>
					${searchclientArticleContent}
				</p>
			</div>
            <aui:form name="searchForm" action="${searchActorURL}" method="post">
                <div id="<portlet:namespace />searchInputWrap" class="search-input-wrap clearfix">  
                    <aui:input name="searchTerm" type="text" cssClass="gothia-search-input" />
                    <button class="gothia-search-btn" type="submit">
                        <span><liferay-ui:message key="search" /> &raquo;</span>
                    </button>
                </div>
            </aui:form>
        </div>

    </div>
</div>
<div style="display: none">
    ${hostName}
</div>


<liferay-util:html-bottom>

	<script type="text/javascript" src="<%= request.getContextPath() %>/js/gothia-search-thin.js"></script>
    <script type="text/javascript" charset="utf-8">

	    AUI().use('aui-base', 'gothia-search-thin', function(A) {
	
	    	var dataSourceURL = '<portlet:resourceURL  id="search" />'+'&searchTerm='+ A.one('#<portlet:namespace/>searchTerm').get('value');
	
	    	var gothiaSearchThin = new A.GothiaSearchThin({
	    		autoCompleteInput: '#<portlet:namespace />searchTerm',
	    		autoCompleteUrl: dataSourceURL,
	    		autoCompleteWrap: '#<portlet:namespace />searchInputWrap'
	       	});
	
	    	gothiaSearchThin.render();
	        
		});

    </script>
</liferay-util:html-bottom>
