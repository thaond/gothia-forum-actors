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

<portlet:actionURL name="search" var="searchURL">
  <portlet:param name="action" value="search" />
</portlet:actionURL>

<portlet:renderURL var="showActorURL">
    <portlet:param name="view" value="showActorArticle" />
    <portlet:param name="articleId" value="1" />
</portlet:renderURL>

<c:set var="themeDisplay" value="${THEME_DISPLAY}" />
  
<h1><liferay-ui:message key="our-search-service" /></h1>
<aui:form action="<%= searchURL%>" method="post" name="addActorsArticleFM" cssClass="gothia-search-form">
    <div id="<portlet:namespace />searchInputWrap" class="search-input-wrap clearfix">
        <aui:input name="searchTerm" type="text" cssClass="gothia-search-input" label="" />
        <aui:button type="submit" value="search" />
    </div>
</aui:form>

${searchArticleContent} 
${searchFirstTimeArticleContent} 
${searchNoHitsArticleContent} 

<!-- show all articles hits  -->

<ul class="gothia-search-results-list clearfix">
  <c:forEach items="${hits}" var="hit">
    <li>
        <a href="${hit.profileURL}">
            ${hit.content}
        </a>
    </li>
  </c:forEach>
</ul>

<liferay-util:html-bottom>
    <script type="text/javascript" charset="utf-8">
        AUI().use('aui-autocomplete', 'aui-io', 'json', function(A) {
                      
            var dataSourceURL = '<portlet:resourceURL  id="search" />'+'&searchTerm='+A.one('#<portlet:namespace/>searchTerm').get('value');
            
            var myDataSource = new A.DataSource.IO({source: dataSourceURL});

            myDataSource.plug(A.Plugin.DataSourceJSONSchema, {
                schema: {
                    resultListLocator: 'results',
                    resultFields: ['name', 'key']
                }
            });
            
            window.AC = new A.AutoComplete(
                {
                    button: false,
                    contentBox: '#<portlet:namespace />searchInputWrap',
                    dataSource: myDataSource,
                    delimChar: false,
                    forceSelection: false,
                    input: '#<portlet:namespace />searchTerm',
                    matchKey: 'name',
                    minQueryLength: 2,
                    queryDelay: 0.2,
                    typeAhead: true
                }
            );

            AC.render();            

        });
    </script>
</liferay-util:html-bottom>
