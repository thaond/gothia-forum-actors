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

<portlet:renderURL var="createActorURL">
  <portlet:param name="tabs1" value="form-tab" />
</portlet:renderURL>

<portlet:renderURL var="connectURL">
  <portlet:param name="view" value="showConnectView" />
</portlet:renderURL>

<p>
  ${fistTimeArticleContent}
</p>

<div class="actor-intro-spot-wrap clearfix">
  <a href="${createActorURL}" class="actor-intro-spot"> <span class="actor-intro-spot-hd"><liferay-ui:message
        key="create-actor-message2" />
  </span> <span class="actor-intro-spot-bd"><liferay-ui:message key="create-actor-message3" /> </span> </a> <span
    class="actor-intro-spot-sep">eller</span> <a href="${connectURL}" class="actor-intro-spot"> <span
    class="actor-intro-spot-hd"><liferay-ui:message key="create-actor-message4" /> </span> <span
    class="actor-intro-spot-bd"><liferay-ui:message key="create-actor-message5" /> </span> </a>
</div>


