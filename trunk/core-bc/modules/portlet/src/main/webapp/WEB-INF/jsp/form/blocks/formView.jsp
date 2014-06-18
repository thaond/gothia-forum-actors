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
<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:if test="${actorArticle.articleStatus != -1}">
  <liferay-ui:tabs names="${tabs}" url="${tabURL}" />
</c:if>

<portlet:actionURL name="addActorsArticle" var="addActorsArticleURL">
  <portlet:param name="action" value="addActor" />
</portlet:actionURL>

<aui:form action="<%= addActorsArticleURL%>" method="post" name="addActorsArticleFM" cssClass="actors-form">

  <aui:layout>
    <aui:column columnWidth="100" first="true" last="true">
      <h1>
        <liferay-ui:message key="edit-profile" />
      </h1>
      <p>
        <liferay-ui:message key="mandatory-text" />
      </p>
    </aui:column>
    <aui:column columnWidth="50" first="true">
      <aui:fieldset>

        <liferay-ui:error key="address-required" message="address-required" />

        <h2>
          <liferay-ui:message key="actor-info" />
        </h2>
        <form:errors path="actorArticle.companyName" cssClass="portlet-msg-error" />
        <aui:input name="companyName" type="text" label="name-on-unit-company" value="${actorArticle.companyName}"
          helpMessage="help-name-on-unit-company" cssClass="mandatory" />
        <form:errors path="actorArticle.organizationName" cssClass="portlet-msg-error" />
        <aui:input name="organizationName" type="text" label="organization-name"
          value="${actorArticle.organizationName}" helpMessage="help-organization-name" cssClass="optional" />
        <form:errors path="actorArticle.intro" cssClass="portlet-msg-error" />
        <aui:input name="intro" type="textarea" label="intro" value="${actorArticle.intro}"
          helpMessage="help-intro" cssClass="mandatory" />
        <form:errors path="actorArticle.detailedDescription" cssClass="portlet-msg-error" />
        <aui:field-wrapper label="detailed-description">
          <liferay-ui:input-editor name="descriptionEditor" toolbarSet="gothia" initMethod="initEditor" width="200" />
          <script type="text/javascript">
            function <portlet:namespace />initEditor() { return "${actorArticle.detailedDescriptionUnicoded}"; }
        </script>
        </aui:field-wrapper>
        <form:errors path="actorArticle.externalHomepage" cssClass="portlet-msg-error" />
        <aui:input name="externalHomepage" type="text" label="external-homepage"
          value="${actorArticle.externalHomepage}" cssClass="optional" />
        <aui:input name="id" type="hidden" value="${actorArticle.articleId}" />

        <p>
            <liferay-ui:message key="help-tag-message" />
        </p>
        <liferay-ui:message key="tags" />

        <%-- https://issues.liferay.com/browse/LPS-41285. A hack to mitigate a bug. '--%>
          <%
              ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
              pageContext.setAttribute("themeDisplay", themeDisplay);
          %>

        <liferay-ui:asset-tags-selector
          className="<%= com.liferay.portlet.journal.model.JournalArticle.class.getName() %>"
          classPK="${actorArticle.resourcePrimKeyId}" hiddenInput="tagsEntries" curTags="${tagsEntries}" />
      </aui:fieldset>
    </aui:column>
    <aui:column columnWidth="50" last="true">
      <aui:fieldset>
        <h2>
          <liferay-ui:message key="contact-person" />
        </h2>
        <form:errors path="actorArticle.name" cssClass="portlet-msg-error" />
        <aui:input name="name" type="text" label="name" value="${actorArticle.name}"
          helpMessage="help-contact-person" cssClass="mandatory" />
        <form:errors path="actorArticle.title" cssClass="portlet-msg-error" />
        <aui:input name="title" type="text" label="title" value="${actorArticle.title}" helpMessage="help-title"
          cssClass="optional" />
        <form:errors path="actorArticle.address" cssClass="portlet-msg-error" />
        <aui:input name="address" type="text" label="address" value="${actorArticle.address}"
          helpMessage="help-address" cssClass="mandatory" />
        <form:errors path="actorArticle.phone" cssClass="portlet-msg-error" />
        <aui:input name="phone" type="text" label="phone" value="${actorArticle.phone}" helpMessage="help-phone"
          cssClass="mandatory" />
        <form:errors path="actorArticle.mobilePhone" cssClass="portlet-msg-error" />
        <aui:input name="mobilePhone" type="text" label="mobile-phone" value="${actorArticle.mobilePhone}"
          helpMessage="help-mobile-phone" cssClass="optional" />
        <form:errors path="actorArticle.fax" cssClass="portlet-msg-error" />
        <aui:input name="fax" type="text" label="fax" value="${actorArticle.fax}" helpMessage="help-fax"
          cssClass="optional" />
        <form:errors path="actorArticle.mail" cssClass="portlet-msg-error" />
        <aui:input name="mail" type="text" label="mail" value="${actorArticle.mail}" helpMessage="help-mail"
          cssClass="mandatory" />
      </aui:fieldset>
    </aui:column>
    <aui:column columnWidth="100" first="true" last="true">
      <aui:button-row>
        <aui:button type="submit" value="next" />
      </aui:button-row>
    </aui:column>
  </aui:layout>
</aui:form>
