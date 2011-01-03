<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="/WEB-INF/views/common/head.jsp">
	<jsp:param name="title" value="Fauxwerd.com" />
</jsp:include>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="${index.title}"/>
</jsp:include>
  <p>Test = ${site}, ${site.hostname}, ${site.port}</p>
	<div>      
		<p>User = ${user}, ID = ${user.id}</p>
	</div>
	<div>
    <p><a href="<spring:url value="/user/logout" htmlEscape="true" />">Logout</a></p>
	</div>
	<div>
		<p>Your content:</p>
	<ol>
		<c:forEach items="${user.userContent}" var="userContentItem">
			<li><a href="${userContentItem.content.url}">${userContentItem.content.url}</a> 
			 - <c:if test="${userContentItem.content.status eq 'FETCHED'}"><a href="/content/${userContentItem.content.id}"></c:if> ${userContentItem.content.status}<c:if test="${userContentItem.content.status eq 'FETCHED'}"></a></c:if> 
			 - ${userContentItem.dateAdded}</li>
		</c:forEach>
	</ol>
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