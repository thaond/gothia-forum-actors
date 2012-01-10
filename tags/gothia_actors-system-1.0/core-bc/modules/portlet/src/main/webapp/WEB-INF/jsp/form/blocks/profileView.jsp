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
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:choose>
  <c:when test="${actorArticle.articleStatus == -1}"><!-- not yet created   -->
      <jsp:include page="../firstTimeView.jsp" />
  </c:when>
  <c:when test="${hasSocialRequest}"><!-- not yet created   -->
      <jsp:include page="../connectRequestSentView.jsp" />
  </c:when>
  <c:otherwise>
    <liferay-ui:tabs names="${tabs}" url="${tabURL}" />

    <portlet:actionURL name="search" var="searchURL">
      <portlet:param name="action" value="search" />
    </portlet:actionURL>

    <c:set var="themeDisplay" value="${THEME_DISPLAY}" />

    <portlet:renderURL var="editActorURL">
      <portlet:param name="tabs1" value="form-tab" />
    </portlet:renderURL>

    <portlet:actionURL name="sendActor" var="sendActorURL">
      <portlet:param name="action" value="sendActor" />
    </portlet:actionURL>


    <c:choose>
      <c:when test="${actorArticle.articleStatus == 0}">
        <!-- Approved  -->
        <div class="portlet-msg-success">
          <liferay-ui:message key="approved" />
        </div>
      ${actorArticle.content}   
      <aui:button-row>
          <aui:button value="edit" onClick="${editActorURL}" />
        </aui:button-row>
      </c:when>
      <c:when test="${actorArticle.articleStatus == 2}">
        <!-- Draft   -->
        <div class="portlet-msg-success">
          <liferay-ui:message key="draft" />
        </div>
      ${actorArticle.content}  
      <aui:button-row>
          <aui:button value="edit" onClick="${editActorURL}" />
          <aui:button value="send-for-approval" onClick="${sendActorURL}" />
        </aui:button-row>
      </c:when>
      <c:otherwise>
        <!-- pending for approval -->
        <div class="portlet-msg-success">
          <liferay-ui:message key="pending-approval" />
        </div>             
        ${actorArticle.content}         
    </c:otherwise>
    </c:choose>
  </c:otherwise>
</c:choose>

