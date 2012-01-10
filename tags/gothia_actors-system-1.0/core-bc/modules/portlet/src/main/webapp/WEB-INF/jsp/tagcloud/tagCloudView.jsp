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
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>


<style type="text/css">
   ul.tags-cloud-list {
       list-style-type: none;
       margin: 0;
       padding: 0;
       text-align: center;
   }

   ul.tags-cloud-list li {
       display: inline;
   }

   ul.tags-cloud-list li a {}

   .tag-weight-1 { font-size: 80%; }
   .tag-weight-2 { font-size: 100%; }
   .tag-weight-3 { font-size: 120%; }
   .tag-weight-4 { font-size: 140%; }
   .tag-weight-5 { font-size: 160%; }
   .tag-weight-6 { font-size: 180%; }
   .tag-weight-7 { font-size: 240%; }
   .tag-weight-8 { font-size: 260%; }
   .tag-weight-9 { font-size: 280%; }
   .tag-weight-10 { font-size: 300%; }

</style>

<ul class="tags-cloud-list">
  <c:forEach items="${tagVOList}" var="tag">
      <li class="${tag.cssClass}">
          <a href="${tag.href }">
              ${tag.name}
          </a>
      </li>
  </c:forEach>  
</ul>



