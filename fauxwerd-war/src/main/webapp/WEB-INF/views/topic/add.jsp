<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="/WEB-INF/views/common/head.jsp" />
<jsp:include page="/WEB-INF/views/common/header.jsp">
    <jsp:param name="title" value="${index.title}"/>
</jsp:include>

      <h1><%--<fmt:message key="user.register.title"/>--%>Add a Topic</h1>
          <section>  
            <form:form modelAttribute="topic" action="add" method="post">
                <form:errors/>
    
                <p> 
                  <form:label for="name" path="name" cssErrorClass="error">name</form:label><br/>
                  <form:input path="name" /> <form:errors path="name" cssClass="error"/>
                </p>
                <p> 
                  <input type="submit" value="add" />
                </p>

            </form:form>
      </section>      

<jsp:include page="/WEB-INF/views/common/footer.jsp" />