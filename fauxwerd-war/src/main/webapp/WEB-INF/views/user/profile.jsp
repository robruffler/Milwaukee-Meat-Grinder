<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<jsp:include page="/WEB-INF/views/common/head.jsp" />
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="${index.title}"/>
</jsp:include>


<h1>Settings for ${user.fullName}</h1>
<div>You are following:</div>
<div>
    <ul>
    <c:forEach items="${user.following}" var="userFollow">
        <li>${userFollow.follow.fullName} <a href="/unfollow/${userFollow.follow.id}">unfollow</a></li>
    </c:forEach>
    </ul>
</div>

<div>You are followed by:</div>
<div>
    <ul>
    <c:forEach items="${user.followers}" var="userFollow">
        <li>${userFollow.user.fullName}</li>
    </c:forEach>
    </ul>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />