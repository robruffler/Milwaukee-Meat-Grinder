<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ page import="org.springframework.security.web.authentication.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.web.authentication.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<jsp:include page="/WEB-INF/views/common/head.jsp">
  <jsp:param name="title" value="login | Fauxwerd.com" />
</jsp:include>
<jsp:include page="/WEB-INF/views/common/header.jsp">
  <jsp:param name="title" value="${index.title}"/>
</jsp:include>

<h1>Password Assistance</h1>
<c:choose>
    <c:when test="${complete eq true}">
        <h2>Check your e-mail.</h2>
        <p>If the e-mail address you entered ${forgotPasswordForm.email} is associated with a customer account in our records, you will receive an e-mail from us with instructions for resetting your password. If you don't receive this e-mail, please check your junk mail folder or visit our Help pages to contact Customer Service for further assistance.</p>    
    </c:when>
    <c:when test="${resetComplete eq true}">
        <p>You have successfully changed your password.</p>
    </c:when>    
    <c:when test="${reset eq true}">
        <h2>Create your new password.</h2>   
        <form:form modelAttribute="resetPasswordForm" method="post">
            <p>
                <form:errors cssClass="error"/>
            </p>
            <p> 
                <form:label for="password" path="password" cssErrorClass="error">new password:</form:label>
                <form:password path="password" /> <form:errors path="password" cssClass="error"/>
            </p>
            <p> 
                <form:label for="confirmPassword" path="confirmPassword" cssErrorClass="error">confirm new password:</form:label>
                <form:password path="confirmPassword" /> <form:errors path="confirmPassword" cssClass="error"/>
            </p>            
            <input type="submit" value="Save changes"/>
        </form:form>                 
    </c:when>    
    <c:otherwise>
		<p>Enter the e-mail address associated with your fauxwerd account, then click Continue. We'll email you a link to a page where you can easily create a new password.</p>
		<form:form modelAttribute="forgotPasswordForm" method="post">
		    <p>
		        <form:errors/>
		        <form:errors path="email" cssClass="error"/>
		    </p>
		    <form:input path="email" /> 
		    <input type="submit" value="Continue"/>
		</form:form>    
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />