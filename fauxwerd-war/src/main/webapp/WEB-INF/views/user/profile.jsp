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
	<jsp:param name="title" value="${index.title}"/>
</jsp:include>



	<div>      
		User = ${user}, ID = ${user.id}
	</div>
	<div>
		Your content:
	<ul>
		<c:forEach items="${user.content}" var="contentItem">
			<li><a href="${contentItem.url}">${contentItem.url}</a></li>
		</c:forEach>
	</ul>
	</div>
	<jsp:include page="/WEB-INF/views/common/bookmark.jsp" />

	
	<script type="text/javascript">
		$(document).ready(function() {
			if (fw.utils.cookie.eat('${user.id}') === null) {
				fw.user.login('${user.id}');
			}
		});
	</script>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />