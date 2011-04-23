<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fw" uri="/WEB-INF/tlds/fauxwerd.tld" %>

<jsp:include page="/WEB-INF/views/mobile/common/head.jsp">
    <jsp:param name="title" value="${content.title}" />
</jsp:include>
<jsp:include page="/WEB-INF/views/mobile/common/header.jsp" />

<section class="share">
	<a href="http://twitter.com/share" class="twitter-share-button" data-count="none" data-via="fauwerd">Tweet</a>
	<fb:like href="http://<%= request.getServerName() + request.getAttribute("javax.servlet.forward.request_uri").toString() %>" layout="button_count" show_faces="false" width="450" />
</section>

<article class="fauxwerd-article">
	<header>${content.title}</header>
	<cite>via <a href="${content.url}">${content.site.hostname}</a></cite>
	<ul class="topics">
		<c:forEach items="${content.topics}" var="topic">
			<li><a href="/m/topic/${topic.id}">${topic.name}</a></li>
		</c:forEach>
	</ul>

	<fw:content contentId="${content.id}"/>
</article>

<jsp:include page="/WEB-INF/views/mobile/common/footer.jsp" />

