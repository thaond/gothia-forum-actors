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
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
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

<portlet:actionURL name="searchForAll" var="searchForAllURL">
  <portlet:param name="action" value="searchForAll" />
</portlet:actionURL>

<portlet:renderURL var="showActorURL">
    <portlet:param name="view" value="showActorArticle" />
    <portlet:param name="articleId" value="1" />
</portlet:renderURL>

<c:set var="themeDisplay" value="${THEME_DISPLAY}" />
  
<div class="gothia-search-wrap clearfix">
    <form action="${searchURL}" method="post">
        <div id="<portlet:namespace />queryWrap" class="search-input-wrap clearfix">
            <aui:button type="submit" value="search" cssClass="gothia-search-button" />
            <aui:input name="searchTerm" type="text" cssClass="gothia-search-input" label="" value="" />          
        </div>
    </form>
</div>



<!-- show articles hits  -->

<ul class="gothia-list">
    <c:forEach items="${hits}" var="hit" varStatus="status">
        <c:set scope="page" var="listItemCssClass" value="" />
        <c:if test="${(status.index)%2 ne 0}">
            <c:set var="listItemCssClass" value="${listItemCssClass} gothia-list-item-odd" scope="page" />
        </c:if>
        <c:if test="${status.last}">
            <c:set var="listItemCssClass" value="${listItemCssClass} gothia-list-item-last" scope="page" />
        </c:if>
        <li class="gothia-list-item ${listItemCssClass}">
            <div class="title">
                <h3>
                    <a href="${hit.profileURL}">${hit.title}</a>
                </h3>
                <c:if test="${not empty hit.organizationName}">
                    <p class="subtitle">${hit.organizationName}</p>
                </c:if>
            </div>
            <div class="content">
                <a href="${hit.profileURL}">
                   ${hit.intro}
                </a> 
            </div>
            <div class="meta">
                <ul class="search-tags">
                    <c:forEach items="${hit.tags}" var="tag" varStatus="status">
                        <li><a href="${tag.link}">${tag.name}</a></li>
                    </c:forEach>
                </ul>
            </div>
        </li>
    </c:forEach>
</ul>

<div class="gothia-paging-wrap">
  <c:if test="${pageIterator.showPaginator}">
      <ul class="gothia-paging clearfix">  
        <!--   <c:if test="${pageIterator.showFirst}">
            <li class="first">
              
                  <liferay-portlet:renderURL  portletMode="VIEW" var="firstUrl" windowState="normal" >
                      <portlet:param name="pageNumber" value="1" />
                      <c:if test="${isViewAll}">
                           <portlet:param name="viewAll" value="viewAll" />    
                      </c:if>               
                  </liferay-portlet:renderURL>
                  <a href="${firstUrl}">F&ouml;rsta</a>
              </li>
          </c:if> -->
          <c:if test="${pageIterator.showPrevious}">
              <li class="previous">
                  <liferay-portlet:renderURL  portletMode="VIEW" var="previousUrl" windowState="normal" >
                      <portlet:param name="pageNumber" value="${pageIterator.previous}" />
                      <c:if test="${isViewAll}">
                           <portlet:param name="viewAll" value="viewAll" />    
                      </c:if>   
                  </liferay-portlet:renderURL>
                  <a href="${previousUrl}">F&ouml;reg&aring;ende</a>
              </li>
          </c:if> 
          
          <c:forEach var="page" items="${pageIterator.pages}">
              
              <c:set var="pageItemCssClass" value="" scope="page" />
              <c:if test="${page.isSelected}">
                  <c:set var="pageItemCssClass" value="selected" scope="page" />
              </c:if>     
          
              <li class="${pageItemCssClass}">
                  <c:choose>
                      <c:when test="${page.isSelected}">
                          <span>${page.pagenumber}</span>
                      </c:when>
                      <c:otherwise>
                          <liferay-portlet:renderURL  portletMode="VIEW" var="url" windowState="normal" >
                              <portlet:param name="pageNumber" value="${page.pagenumber}" />
                              <c:if test="${isViewAll}">
                                   <portlet:param name="viewAll" value="viewAll" />    
                              </c:if>   
                          </liferay-portlet:renderURL>        
                          <a href="${url}">${page.pagenumber}</a>
                      </c:otherwise>
                  </c:choose>
              </li>   
          </c:forEach>
          
          <c:if test="${pageIterator.showNext}">
              <li class="next">
                  <liferay-portlet:renderURL  portletMode="VIEW" var="nextUrl" windowState="normal" >
                      <portlet:param name="pageNumber" value="${pageIterator.next}" />
                      <c:if test="${isViewAll}">
                           <portlet:param name="viewAll" value="viewAll" />    
                      </c:if>   
                  </liferay-portlet:renderURL>
                  <a href="${nextUrl}">N&auml;sta</a>
              </li>
          </c:if> 
      <!--      <c:if test="${pageIterator.showLast}">
              <li class="last">
                  <liferay-portlet:renderURL  portletMode="VIEW" var="lastUrl" windowState="normal" >
                      <portlet:param name="pageNumber" value="${pageIterator.last}" />
                      <c:if test="${isViewAll}">
                           <portlet:param name="viewAll" value="viewAll" />    
                      </c:if>   
                  </liferay-portlet:renderURL>
                  <a href="${lastUrl}">Sista</a>
              </li>
          </c:if> -->
      </ul>
  </c:if> 
</div>
