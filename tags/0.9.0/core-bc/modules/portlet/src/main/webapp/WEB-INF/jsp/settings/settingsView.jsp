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


<portlet:actionURL name="saveSettings" var="saveSettingsURL">
    <portlet:param name="action" value="save"/>
</portlet:actionURL>

<h1><liferay-ui:message key="settings" /></h1>  
<aui:form action="${saveSettingsURL}" method="post" name="setSettings">
 		             
	<aui:input name="footerId1"    type="text"     label="footerId1"    value="${footerId1}"/>
	<aui:input name="footerId2"    type="text"     label="footerId2"    value="${footerId2}"/>
	<aui:input name="footerId3"    type="text"     label="footerId3"    value="${footerId3}"/>
	<aui:input name="footerId4"    type="text"     label="footerId4"    value="${footerId4}"/>
	
	<aui:input name="bannerArticleId"    type="text"     label="bannerArticleId"    value="${bannerArticleId}"/>
	<aui:input name="searchclientArticleId"    type="text"     label="searchclientArticleId"    value="${searchclientArticleId}"/>
	
	<aui:input name="searchArticleId"    type="text"     label="searchArticleId"    value="${searchArticleId}"/>
	<aui:input name="searchFirstTimeArticleId"    type="text"     label="searchFirstTimeArticleId"    value="${searchFirstTimeArticleId}"/>
	<aui:input name="searchNoHitsArticleId"    type="text"     label="searchNoHitsArticleId"    value="${searchNoHitsArticleId}"/>
		
	<aui:input name="socialRequestSentArticlId"    type="text"     label="socialRequestSentArticlId"    value="${socialRequestSentArticlId}"/>
	<aui:input name="fistTimeArticleId"    type="text"     label="fistTimeArticleId"    value="${fistTimeArticleId}"/>
	
			
	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row> 
</aui:form>

