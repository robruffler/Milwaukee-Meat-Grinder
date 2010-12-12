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


<jsp:include page="/WEB-INF/views/common/head.jsp">
  <jsp:param name="title" value="login | Fauxwerd.com" />
</jsp:include>
<jsp:include page="/WEB-INF/views/common/header.jsp">
  <jsp:param name="title" value="${index.title}"/>
</jsp:include>

<form action="j_spring_security_check" method="post"'>
  <fieldset>    
    <!-- <legend>Login</legend> -->  
	  <p>
	    <label for="j_username">email</label>
	    <input type="text" name="j_username" id="j_username" <c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY) %>'</c:if>/>
	  </p>
	  <p>
		  <label for="j_password">password</label>
		  <input type="password" name="j_password" id="j_password"/>
	  </p>
	  <p>
	    <input type='checkbox' name='_spring_security_remember_me'/> Remember me on this computer.
	  </p>
	  <p>
	    <input type="submit" value="Login"/>
	  </p>
  </fieldset>
</form>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />