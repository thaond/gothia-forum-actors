<%--

    Copyright 2011 Västra Götalandsregionen

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


<c:if test="${actorArcticle.articleStatus != -1}">
   <liferay-ui:tabs names="${tabs}"  url="${tabURL}" />
</c:if>

<portlet:actionURL name="addActorsArticle" var="addActorsArticleURL">
    <portlet:param name="action" value="addActor"/>
</portlet:actionURL>

<aui:form action="<%= addActorsArticleURL%>" method="post" name="addActorsArticleFM" cssClass="actors-form">
    
    <aui:layout>
        <aui:column columnWidth="100" first="true" last="true">
            <h1><liferay-ui:message key="edit-profile" /></h1>    
            <p><liferay-ui:message key="mandatory-text" /></p>      
        </aui:column>
        <aui:column columnWidth="50" first="true">
          <aui:fieldset>
          
          <liferay-ui:error key="address-required" message="address-required" />
          
              <h2><liferay-ui:message key="actor-info" /></h2>  
              <form:errors path="actorArcticle.companyName" cssClass="portlet-msg-error"/>                 
              <aui:input name="companyName"         type="text"     label="name-on-unit-company"    value="${actorArcticle.companyName}"        helpMessage="help-name-on-unit-company" cssClass="mandatory" />
              <form:errors path="actorArcticle.organizationName" cssClass="portlet-msg-error"/>
              <aui:input name="organizationName"    type="text"     label="organization-name"       value="${actorArcticle.organizationName}"   helpMessage="help-organization-name" cssClass="optional"/>
              <form:errors path="actorArcticle.ingress" cssClass="portlet-msg-error"/>
              <aui:input name="ingress"             type="textarea" label="ingress"                 value="${actorArcticle.ingress}"            helpMessage="help-ingress" cssClass="optional" />
              <form:errors path="actorArcticle.detailedDescription" cssClass="portlet-msg-error"/>
              <aui:input name="detailedDescription" type="textarea" label="detailed-description"    value="${actorArcticle.detailedDescription}" helpMessage="help-detailed-description" cssClass="mandatory"/>  
              <form:errors path="actorArcticle.externalHomepage" cssClass="portlet-msg-error"/>
              <aui:input name="externalHomepage"    type="text"     label="external-homepage"        value="${actorArcticle.externalHomepage}"   helpMessage="this-is-a-help-message" cssClass="optional"/>  
              <aui:input name="id"                  type="hidden"   value="${actorArcticle.articleId}" />
                
               
              <liferay-ui:message key="tags" />
              <liferay-ui:asset-tags-selector 
                      className="<%= com.liferay.portlet.journal.model.JournalArticle.class.getName() %>"
                      classPK="${actorArcticle.resourcePrimKeyId}"
                      hiddenInput="tagsEntries"   
                      curTags="${tagsEntries}"                 
               />
          </aui:fieldset>
        </aui:column>
        <aui:column columnWidth="50" last="true">
          <aui:fieldset>
              <h2><liferay-ui:message key="contact-person" /></h2>
              <form:errors path="actorArcticle.name" cssClass="portlet-msg-error"/>
              <aui:input name="name"              type="text"     label="name"                    value="${actorArcticle.name}"               helpMessage="help-contact-person" cssClass="mandatory"/>
              <form:errors path="actorArcticle.title" cssClass="portlet-msg-error"/>
              <aui:input name="title"             type="text"     label="title"                   value="${actorArcticle.title}"              helpMessage="help-title" cssClass="optional"/>
              <form:errors path="actorArcticle.address" cssClass="portlet-msg-error"/>
              <aui:input name="address"           type="text"     label="address"                 value="${actorArcticle.address}"            helpMessage="help-address" cssClass="mandatory"/>
              <form:errors path="actorArcticle.phone" cssClass="portlet-msg-error"/>
              <aui:input name="phone"             type="text"     label="phone"                   value="${actorArcticle.phone}"              helpMessage="help-phone" cssClass="mandatory"/>
              <form:errors path="actorArcticle.mobilePhone" cssClass="portlet-msg-error"/>
              <aui:input name="mobilePhone"       type="text"     label="mobile-phone"            value="${actorArcticle.mobilePhone}"        helpMessage="help-mobile-phone" cssClass="optional"/>
              <form:errors path="actorArcticle.fax" cssClass="portlet-msg-error"/>
              <aui:input name="fax"               type="text"     label="fax"                     value="${actorArcticle.fax}"                helpMessage="help-fax" cssClass="optional"/>
              <form:errors path="actorArcticle.mail" cssClass="portlet-msg-error"/>
              <aui:input name="mail"              type="text"     label="mail"                    value="${actorArcticle.mail}"               helpMessage="help-mail" cssClass="mandatory"/>
          </aui:fieldset>
        </aui:column>
        <aui:column columnWidth="100" first="true" last="true">
          <aui:button-row>
            <aui:button type="submit" value="next" />
          </aui:button-row>                
        </aui:column>
    </aui:layout>
</aui:form>
    