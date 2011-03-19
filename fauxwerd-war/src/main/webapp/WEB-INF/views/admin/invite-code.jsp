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

<p>Your invite code is ${invite.code} it's status is ${invite.status}</p>
<c:set var="baseUrl" value="http://${site.hostname}"/>
<c:if test="${not (site.port eq 80)}">
    <c:set var="baseUrl" value="${baseUrl}:${site.port}"/>    
</c:if>
<p><a href="${baseUrl}/register?code=${invite.code}">${baseUrl}/register?code=${invite.code}</a></p>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
