<%@page contentType="text/html" pageEncoding="windows-1252"%>
<jsp:include page="/WEB-INF/views/common/head.jsp">
	<jsp:param name="title" value="Fauxwerd.com" />
</jsp:include>
		
<div id="response"><!-- --></div>
	
	<script type="text/javascript">
		$(window).ready(function() {
			if (fw.utils.cookie.eat('u') === '${u}') { 
				$.ajax({
				url: '/content',
				data: {url: '${url}', userId: '${u}' },
				cache: false,
				type: 'post',
				success: function(d,t,x) {
					$('#response').html(d);
				}
			});
			} else { 
				parent.location = 'http://' + fw.env + '/user/wrong-bookmark?url=' + encodeURIComponent('${url}');
			} 
		});
	</script>
		
<jsp:include page="/WEB-INF/views/common/footer.jsp" />