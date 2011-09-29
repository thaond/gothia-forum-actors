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
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="<portlet:namespace />mltWrap" class="mlt-wrap">
    <c:if test="${hitsIsNotEmpty}">
		<h2>Liknande profiler</h2>
	</c:if>
	<ul class="cal-list">
	  	<c:forEach items="${hits}" var="hit">
	    	<li class="clearfix">
	        	<a href="${hit.profileURL}">
				    ${hit.content}
	        	</a>
	    	</li>
	  	</c:forEach>
	</ul>
</div>

<liferay-util:html-bottom>
<script type="text/javascript">

	AUI().ready('aui-tooltip', function(A) {
	
		var t1 = new A.Tooltip({
			trigger: '#<portlet:namespace />mltWrap .mlt-title',
			align: { points: [ 'br', 'tl' ] },
			anim: {
				show: true
			},
			cssClass: 'gothia-tooltip',
			hideDelay: 300,
			title: true
		})
		.render();
	
	});

</script>
</liferay-util:html-bottom>
