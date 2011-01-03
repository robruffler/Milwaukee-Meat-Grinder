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

<h1>Content ${content.id}</h1>

<p>Site Hostname: ${content.site.hostname}</p>

<p>Url: ${content.url}</p>

<p>Status: ${content.status}</p>

<p>Path to html: ${content.rawHtmlPath} </p>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />