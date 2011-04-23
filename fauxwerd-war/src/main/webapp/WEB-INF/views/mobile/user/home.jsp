<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<jsp:include page="/WEB-INF/views/mobile/common/head.jsp">
	<jsp:param name="title" value="See what your friends are reading"/>
</jsp:include>
<jsp:include page="/WEB-INF/views/mobile/common/header.jsp">
    <jsp:param name="title" value="${index.title}"/>
</jsp:include>

<c:if test="${fn:length(user.userContent) >= 1}">
	<section class="module">
		<header class="mod-header">
			saved articles
		</header>
		<ul class="mod-content">
			<c:forEach items="${user.userContent}" var="userContentItem" varStatus="status">
				<li>
				    <c:set var="contentReady" value="${userContentItem.content.status eq 'PARSED'}" />
		            <c:if test="${contentReady}"><a href="/m/content/${userContentItem.content.id}"></c:if><c:choose><c:when test="${!empty userContentItem.content.title}">${userContentItem.content.title}</c:when><c:otherwise>${userContentItem.content.url}</c:otherwise></c:choose><c:if test="${contentReady}"></a></c:if>
                </li>
		    </c:forEach>
			</li>
		</ul>
	</section>

<jsp:include page="/WEB-INF/views/mobile/common/footer.jsp" />
