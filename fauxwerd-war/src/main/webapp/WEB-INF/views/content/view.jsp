<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fw" uri="/WEB-INF/tlds/fauxwerd.tld" %>

<jsp:include page="/WEB-INF/views/common/head.jsp">
    <jsp:param name="title" value="Fauxwerd.com" />
</jsp:include>
<jsp:include page="/WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value="${index.title}"/>
</jsp:include>

<h1>${content.title}</h1>

<p><a href="${content.url}">View this article on ${content.site.hostname}</a></p>

<fw:content contentId="${content.id}"/>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />