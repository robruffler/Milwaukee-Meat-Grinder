<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="/WEB-INF/views/common/head.jsp">
	<jsp:param name="title" value="Fauxwerd.com" />
</jsp:include>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="Oh dear, you appear to be using the wrong bookmark!"/>
</jsp:include>

<h3 class="tip">You are signed in as ${user.email}, but your bookmark is meant for someone else! Don't worry, we've supplied the correct one right here for you.</h3>

<jsp:include page="/WEB-INF/views/common/bookmark.jsp" />

<h3 class="url">${url}</h3>
<p class="default">
	You can still <a href="#" id="save-content">save</a> your article to <strong>${user.email}</strong> account, or you can <a href="#">login as a different user</a>.
</p>

<script type="text/javascript">
	$(document).ready(function() {
		$('#save-content').unbind('click').click(function(e) {
			e.preventDefault();
			$.ajax({
				url: '/content',
				data: {url: '${url}', userId: '${user.id}' },
				cache: false,
				type: 'post',
				success: function(d,t,x) {
					location.href = 'http://' + fw.env + '/user/profile';
				},
				error: function(x,t,er) {
					fw.modules.error({msg: "<div id='error'>Oh, dear. We seem to be having some server issues. Give it a few and try again.</div>"});
				}
			});
			return false;
		});
	});
</script>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />