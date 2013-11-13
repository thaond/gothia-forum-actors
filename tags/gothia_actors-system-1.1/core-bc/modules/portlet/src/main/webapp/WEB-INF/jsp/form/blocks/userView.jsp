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

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<liferay-ui:tabs names="${tabs}" url="${tabURL}" />

<div class="gothia-actor-users-wrap slimmed-iterator">
	<aui:layout>
		<aui:column columnWidth="50" first="true">
			<liferay-ui:search-container
				id="<portlet:namespace />parentSearchContainer"
				delta="${searchDelta}" iteratorURL="${iteratorURL}">
				<liferay-ui:search-container-results results="${users}"
					total="${actorUsersCount}" />
				<liferay-ui:search-container-row
					className="com.liferay.portal.model.User" keyProperty="userId">

					<liferay-ui:search-container-column-text name="fullName"
						property="fullName" />

					<liferay-ui:search-container-column-text name="emailAddress"
						property="emailAddress" />
				</liferay-ui:search-container-row>
				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</aui:column>
		<aui:column columnWidth="50" last="true">
			<table class="lfr-table" width="100%">
			  <c:forEach items="${SOCIAL_REQUESTS}" var="request">		
			    <tr>
					<td align="center" class="lfr-top">
						<liferay-ui:user-display
							userId="${request.socialRequest.userId}"
							displayStyle="${2}"
						/>				
					</td>
					<td class="lfr-top" width="99%" >	
						<div>
							${request.requestFeedEntry.title}
						</div>		   
						<div>     
			      			${request.requestFeedEntry.body}
			        	</div>
			        	<br />
			        	<liferay-ui:icon-list>

							<portlet:actionURL name="confirm" var="confirmURL">
							  <portlet:param name="action" value="confirm" />
							  <portlet:param name="requestId" value="${request.socialRequest.requestId}" />
							</portlet:actionURL>
							
							<liferay-ui:icon
								image="activate"
								message="confirm"
								url="${confirmURL}"
							/>

							<portlet:actionURL name="deny" var="denyURL">
							  <portlet:param name="action" value="deny" />
							  <portlet:param name="requestId" value="${request.socialRequest.requestId}" />
							</portlet:actionURL>

							<liferay-ui:icon
								image="deactivate"
								message="deny"
								url="${denyURL}"
							/>
						</liferay-ui:icon-list>
			        </td>
			    </tr>
			  </c:forEach>
			</table>				
		</aui:column>
	</aui:layout>
</div>



