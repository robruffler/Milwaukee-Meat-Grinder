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

<c:if test="${fn:length(topic.content) >= 1}">
    <h1 class="title pizazz">${topic.name}</h1>
    <table class="content-list">
        <tr class="heading">
            <td width="105">Saved At</td>
            <td>Article</td>
        </tr>
        <c:forEach items="${topic.content}" var="contentItem" varStatus="status">
            <tr <c:if test="${status.count % 2 == 0}">class="alt-row"</c:if>>
            <c:set var="contentReady" value="${contentItem.status eq 'PARSED'}" />
                <td>
                    <c:choose>
                        <c:when test="${not empty contentItem.dateUpdated}">
                            <joda:format value="${contentItem.dateUpdated}" style="SS" />
                        </c:when>
                        <c:otherwise>
                            <joda:format value="${contentItem.dateAdded}" style="SS" />
                        </c:otherwise>
                    </c:choose>                
                </td>
                <td>
                    <c:if test="${contentReady}"><a href="/content/${contentItem.id}"></c:if><c:choose><c:when test="${!empty contentItem.title}">${contentItem.title}</c:when><c:otherwise>${contentItem.url}</c:otherwise></c:choose><c:if test="${contentReady}"></a></c:if>
                     <span class="source">(<a href="${contentItem.url}" title="Original Content">${contentItem.site.hostname}</a>)</span>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
 
<jsp:include page="/WEB-INF/views/common/footer.jsp" />