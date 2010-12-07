<%@page contentType="text/html" pageEncoding="windows-1252"%>
<jsp:include page="/WEB-INF/views/common/head.jsp">
	<jsp:param name="title" value="Fauxwerd.com" />
</jsp:include>
		
<div id="response"><!-- --></div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$.ajax({
				url: '/content',
				data: {url: '${url}', userId: '${u}' },
				cache: false,
				type: 'post',
				success: function(d,t,x) {
					$('#response').html(d);
				}
			});
		});
	</script>
		
<jsp:include page="/WEB-INF/views/common/footer.jsp" />