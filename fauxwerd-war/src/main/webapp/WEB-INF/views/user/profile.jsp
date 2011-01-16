<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:include page="/WEB-INF/views/common/head.jsp" />
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="${index.title}"/>
</jsp:include>

<c:set var="now" value="<%=new java.util.Date() %>"/>
<c:set var="insertedSavedEarlier" value="false"/>

<jsp:include page="/WEB-INF/views/common/bookmark.jsp" />
<c:if test="${fn:length(user.userContent) >= 1}">
	<h1 class="title pop">Saved Articles</h1>
	<table class="content-list">
		<tr class="heading">
			<td width="40">Status</td>
			<td width="75">Date Added</td>
			<td>Article</td>
		</tr>
		<c:forEach items="${user.userContent}" var="userContentItem" varStatus="status">
			<c:set var="mdy" value="${userContentItem.dateAdded.month + 1}/${userContentItem.dateAdded.date}/${userContentItem.dateAdded.year + 1900}"/>
			<tr <c:if test="${status.count % 2 == 0}">class="alt-row"</c:if>>
			<c:set var="contentReady" value="${userContentItem.content.status eq 'FETCHED' || userContentItem.content.status eq 'PARSED'}" />
			<c:set var="contentSaved" value="${userContentItem.content.status eq 'SAVED'}"/>
				<td align="center">
					<c:if test="${contentReady}"><img src="/images/fetched.png" alt="Ready" /></c:if>
					<c:if test="${contentSaved}"><img src="/images/pending.png" alt="Pending" /></c:if>
					<c:if test="${!contentReady && !contentSaved}"><img src="/images/error.png" alt="Error" /></c:if>
				</td>
				<td>${mdy}</td>
				<td>
					<c:if test="${contentReady}"><a href="/content/${userContentItem.content.id}"></c:if>
						${userContentItem.content.url}
					<c:if test="${contentReady}"></a></c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>

<script type="text/javascript">
	$(document).ready(function() {
		if (fw.utils.cookie.eat('${user.id}') === null) {
			fw.user.login('${user.id}');
		}
	});
</script>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />