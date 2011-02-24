<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="/WEB-INF/views/common/head.jsp">
	<jsp:param name="title" value="Fauxwerd.com" />
</jsp:include>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="${index.title}"/>
</jsp:include>
    
<article>
	<h2><fmt:message key="index.subtitle"/></h2>
<c:choose>	
	<c:when test="${cookie.subscribed.value eq 'true'}">
	    <p><fmt:message key="index.thanks"/></p>
	    <p><a href="http://twitter.com/#!/fauxwerd"><fmt:message key="index.twitterLink"/></a></p>
	</c:when>
	<c:otherwise>
	    <p><fmt:message key="index.content1"/></p>
	    ${cookie.subscribed.value}
	    <form:form modelAttribute="emailSubscription" action="subscribe" method="post">
	        <p>       
	            <form:input path="email" /> <form:errors path="email" cssClass="error"/>
	            <form:hidden path="listId" value="df2e4019a9"/>
	            <input type="submit" value="keep me posted" />
	        </p>    
	    </form:form>
	</c:otherwise>    	
</c:choose>
	
</article>	  
	  
<jsp:include page="/WEB-INF/views/common/footer.jsp" />