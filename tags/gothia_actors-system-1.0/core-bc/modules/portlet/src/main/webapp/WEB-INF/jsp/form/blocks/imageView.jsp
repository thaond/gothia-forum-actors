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

<%@page import="com.liferay.portlet.journal.model.JournalArticle"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:actionURL name="addActorsArticle" var="addActorsArticleURL">
  <portlet:param name="action" value="addActorImage" />
</portlet:actionURL>


<portlet:renderURL var="cancelURL">
  <portlet:param name="tabs1" value="form-tab" />
</portlet:renderURL>

<aui:form action="<%= addActorsArticleURL%>" method="post" name="addActorsArticleFM" cssClass="actors-form"
  enctype="multipart/form-data" useNamespace="false">
  <aui:layout>
    <aui:column columnWidth="100" first="true" last="true">
      <h1>
        <liferay-ui:message key="edit-profile" />
      </h1>
    </aui:column>
    <aui:column columnWidth="100" first="true">
      <aui:fieldset>
        <c:choose>
          <c:when test="${fn:contains(actorLogo,'noLogo')}">
            <liferay-ui:message key="no-logo" />
          </c:when>
          <c:otherwise>
            <div>
              <img src="${actorLogo} ">
            </div>
          </c:otherwise>
        </c:choose>
        <div>
          <liferay-ui:message key="logo-message" />
        </div>
      </aui:fieldset>
    </aui:column>
    <aui:column columnWidth="100" first="true">
      <aui:fieldset>
        <c:if test="${not empty errorList}">
          <div class="portlet-msg-error">
            <c:forEach items="${errorList}" var="error">
              <div>
                <liferay-ui:message key="${error}" />
              </div>
            </c:forEach>
          </div>
        </c:if>
        <aui:input name="file" type="file" label="logo" helpMessage="help-logo" />
      </aui:fieldset>
    </aui:column>
    <aui:column columnWidth="100" first="true" last="true">
      <aui:button-row>
        <aui:button onClick="${cancelURL}" type="cancel" value="back" />
        <aui:button type="submit" value="save-and-preview" />
      </aui:button-row>
    </aui:column>
  </aui:layout>
</aui:form>


