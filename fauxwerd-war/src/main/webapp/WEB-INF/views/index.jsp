<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/views/common/head.jsp">
	<jsp:param name="title" value="Fauxwerd.com" />
</jsp:include>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="${index.title}"/>
</jsp:include>
    
<article>
	<h2><fmt:message key="index.subtitle"/></h2>
	<p><fmt:message key="index.content1"/></p>
	<p><a href="http://twitter.com/#!/fauxwerd"><fmt:message key="index.twitterLink"/></a></p>
</article>	  
	  
<jsp:include page="/WEB-INF/views/common/footer.jsp" />