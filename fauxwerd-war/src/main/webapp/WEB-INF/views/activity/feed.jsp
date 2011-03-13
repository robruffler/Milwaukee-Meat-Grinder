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

<c:if test="${fn:length(activityFeed) >= 1}">
    <h1 class="title pizazz">Recent Activity</h1>
    <table class="content-list">
        <tr class="heading">
            <td width="150">User</td>
            <td>Activity</td>
        </tr>
        <c:forEach items="${activityFeed}" var="activity" varStatus="status">
            <tr <c:if test="${status.count % 2 == 0}">class="alt-row"</c:if>>
                <td><a href="/user/${activity.user.id}">${activity.user.fullName}</a></td>
                <td>${activity.type} <a href="/content/${activity.content.id}">${activity.content.title}</a> from <a href="${activity.content.url}">${activity.content.site.hostname}</a></td>
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