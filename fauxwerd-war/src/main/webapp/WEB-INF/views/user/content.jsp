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

<% pageContext.setAttribute("now", new org.joda.time.DateTime()); %>
<h2>${user.fullName}</h2>
<c:if test="${fn:length(user.userContent) >= 1}">
    <h1 class="title pizazz">Saved Articles</h1>
    <table class="content-list">
        <tr class="heading">
            <td width="105">Saved At</td>
            <td>Article</td>
        </tr>
        <c:forEach items="${user.userContent}" var="userContentItem" varStatus="status">
            <tr <c:if test="${status.count % 2 == 0}">class="alt-row"</c:if>>
            <c:set var="contentReady" value="${userContentItem.content.status eq 'PARSED'}" />
                <td>
                    <c:choose>
                        <c:when test="${not empty userContentItem.dateUpdated}">
                            <joda:format value="${userContentItem.dateUpdated}" style="SS" />
                        </c:when>
                        <c:otherwise>
                            <joda:format value="${userContentItem.dateAdded}" style="SS" />
                        </c:otherwise>
                    </c:choose>                
                </td>
                <td>
                    <c:if test="${contentReady}"><a href="/content/${userContentItem.content.id}"></c:if><c:choose><c:when test="${!empty userContentItem.content.title}">${userContentItem.content.title}</c:when><c:otherwise>${userContentItem.content.url}</c:otherwise></c:choose><c:if test="${contentReady}"></a></c:if>
                     <span class="source">(<a href="${userContentItem.content.url}" title="Original Content">${userContentItem.content.site.hostname}</a>)</span>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />