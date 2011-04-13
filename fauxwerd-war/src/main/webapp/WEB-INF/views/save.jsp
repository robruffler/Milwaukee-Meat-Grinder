<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="/WEB-INF/views/common/head.jsp" />
<jsp:include page="/WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value="${index.title}"/>
</jsp:include>

<c:choose>
	<c:when test="${fn:containsIgnoreCase(site.hostname, 'fauxwerd')}">
		<c:set var="env" value="${site.hostname}"/>
	</c:when>
	<c:otherwise>
		<c:set var="env" value="localhost:8080"/>
	</c:otherwise>
</c:choose>
<div id="response"><!-- --></div>
<div class="infotainer info">
	Do you want to add <strong class="pop">${url}</strong> to your Fauxwerd articles?
	<form id="fwForm" action="/content" method="post">
		<input type="hidden" name="userId" value="${user.id}" />
		<input type="hidden" name="url" value="${url}" />
		<input type="hidden" name="title" value="" />
		<input type="hidden" name="page" value="" />
		<input type="submit" value="Fauxwerd it!" />
	</form>
		
		<a href="${url}" class="oops">Oops, take me back!</a>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$("#fwForm").submit(function(e) {
			e.preventDefault();
			$.ajax({
				url: '/content',
				data: {url: '${url}', userId: '${user.id}', title: '', page: '' },
				contentType: 'application/x-www-form-urlencoded;charset=utf-8',
				cache: false,
				type: 'post',
				success: function(d,t,x) {
					location = "http://${env}/home";
				},
				error: function() {
					$('#response').html('<div class="infotainer error">Could not save. Try again in a few minutes.</div>');
				}
			});
			return false;
		});
	});
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />