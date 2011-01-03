<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ page session="false" %>
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
      <h1><fmt:message key="user.register.title"/></h1>
		  <section>  
		    <form:form modelAttribute="user" action="register" method="post">
		        <form:errors/>
		        <fieldset>    
		        <legend>Account Fields</legend>
		        <p> 
		          <form:label for="email" path="email" cssErrorClass="error">email</form:label><br/>
		          <form:input path="email" /> <form:errors path="email" cssClass="error"/>
		        </p>
		        <p>
		          <form:label for="password" path="password" cssErrorClass="error">password</form:label><br/>
		          <form:password path="password" /> <form:errors path="password" cssClass="error"/>
		        </p>
		        <p> 
		          <input type="submit" value="register" />
		        </p>
		      </fieldset>
		    </form:form>
      </section>      
    </article>
    
<jsp:include page="/WEB-INF/views/common/footer.jsp" />