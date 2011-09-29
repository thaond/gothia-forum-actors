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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
 <liferay-ui:search-container id="<portlet:namespace />parentSearchContainer"
    delta="${delta}"
    iteratorURL="${iteratorURL}"
>
    <liferay-ui:search-container-results
        results="${actors}"
        total="${total}"
    />
    <liferay-ui:search-container-row
        className="com.liferay.portal.model.Organization"
        keyProperty="organizationId"
        modelVar="actor"
    >
    
        <liferay-ui:search-container-column-text
            name="name"
            property="name"
        />
        
        <portlet:actionURL var="connectToActorURL">
            <portlet:param name="action" value="connectToActor" />
            <portlet:param name="organizationId" value="${actor.organizationId}" />
        </portlet:actionURL>
        
            
        <liferay-ui:search-container-column-text>
            <a href="${connectToActorURL}"  >Anslut till</a>
        </liferay-ui:search-container-column-text>
        
    </liferay-ui:search-container-row>
    <liferay-ui:search-iterator />
</liferay-ui:search-container>

