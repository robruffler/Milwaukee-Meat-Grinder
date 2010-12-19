<%@page contentType="text/html" pageEncoding="windows-1252"%>
<jsp:include page="/WEB-INF/views/common/head.jsp">
	<jsp:param name="title" value="Fauxwerd.com" />
</jsp:include>
<style type="text/css">html, body {background: transparent;}</style>
<div id="response"><!-- --></div>
	
	<script type="text/javascript">
		$(window).ready(function() {
			var u = fw.utils.cookie.eat('u');
			if (u === '${u}') { 
				$.ajax({
					url: '/content',
					data: {url: '${url}', userId: '${u}' },
					cache: false,
					type: 'post',
					success: function(d,t,x) {
						$('#response').html(d);
					},
					error: function() {
						$('#response').html('<div class="error">Could not save. Try again in a few minutes.</div>');
					}
				});
			} else if (u === null) {
				parent.location = 'http://${site.hostname}/spring_security_login?url=' + encodeURIComponent('${url}');
			} else { 
				parent.location = 'http://${site.hostname}/user/wrong-bookmark?url=' + encodeURIComponent('${url}');
			} 
		});
	</script>
		
<jsp:include page="/WEB-INF/views/common/footer.jsp" />