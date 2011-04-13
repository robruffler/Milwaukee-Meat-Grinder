<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="/WEB-INF/views/common/head.jsp">
	<jsp:param name="title" value="Fauxwerd.com" />
</jsp:include>

<c:choose>
	<c:when test="${fn:containsIgnoreCase(site.hostname, 'fauxwerd')}">
		<c:set var="env" value="${site.hostname}"/>
	</c:when>
	<c:otherwise>
		<c:set var="env" value="localhost:8080"/>
	</c:otherwise>
</c:choose>
<style type="text/css">html, body {background: transparent;}</style>
<div id="response"><!-- --></div>
	
	<script type="text/javascript">
		$(window).ready(function() {
			var u = fw.utils.cookie.eat('u');
			if (u === '${u}') { 
				$.ajax({
					url: '/content',
					data: {url: '${url}', userId: '${u}', title: '${t}', page: '${p}' },
					contentType: 'application/x-www-form-urlencoded;charset=utf-8',
					cache: false,
					type: 'post',
					success: function(d,t,x) {
						$('#response').fadeOut(500, function() { $('#response').html(d).fadeIn(200);});
					},
					error: function() {
						$('#response').html('<div class="infotainer error">Could not save. Try again in a few minutes.</div>');
					}
				});
			} else if (u === null) {
				parent.location = 'http://${env}/login?url=${url}&t=${t}';
			} else { 
				parent.location = 'http://${env}/wrong-bookmark?url=${url}&t=${t}';
			} 
		});
	</script>
		
<jsp:include page="/WEB-INF/views/common/footer.jsp" />