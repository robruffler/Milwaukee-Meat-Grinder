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
<p>Welcome ${user.fullName}, today is: <joda:format value="${now}" style="SM" /></p>
<p><a href="/users">Find people to follow</a></p>
<p><a href="/activity">See what your friends are doing</a></p>
<p><a href="/topic/list">Browse topics</a></p>
<jsp:include page="/WEB-INF/views/common/bookmark.jsp" />
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

<script type="text/javascript">
    $(document).ready(function() {
        if (fw.utils.cookie.eat('${user.id}') === null) {
            fw.user.login('${user.id}');
        }
    });
</script>

    <!-- uservoice widget -->
    <script type="text/javascript">
      var uservoiceOptions = {
        key: 'fauxwerd',
        host: 'fauxwerd.uservoice.com', 
        forum: '104931',
        alignment: 'left',
        background_color:'#f00', 
        text_color: 'white',
        hover_color: '#06c',
        lang: 'en',
        showTab: true
      };
      function _loadUserVoice() {
        var s = document.createElement('script');
        s.src = ("https:" == document.location.protocol ? "https://" : "http://") + "cdn.uservoice.com/javascripts/widgets/tab.js";
        document.getElementsByTagName('head')[0].appendChild(s);
      }
      _loadSuper = window.onload;
      window.onload = (typeof window.onload != 'function') ? _loadUserVoice : function() { _loadSuper(); _loadUserVoice(); };
    </script>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />