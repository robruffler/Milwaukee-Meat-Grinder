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

<c:if test="${fn:length(otherUsers) >= 1}">
    <h1 class="title pizazz">Fauxwerd Users</h1>
    <table class="content-list">
        <tr class="heading">
            <td width="150">Name</td>
            <td>Articles Saved</td>
        </tr>
        <c:forEach items="${otherUsers}" var="user" varStatus="status">
            <tr <c:if test="${status.count % 2 == 0}">class="alt-row"</c:if>>
                <td><a href="/user/${user.id}">${user.fullName}</a></td>
                <td>${fn:length(user.userContent)}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<script type="text/javascript">
    $(document).ready(function() {
        if (fw.utils.cookie.eat('${user.id}') === null) {
            fw.user.login('${user.id}');
        }
    });
</script>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />