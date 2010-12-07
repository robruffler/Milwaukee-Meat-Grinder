<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/views/common/head.jsp">
	<jsp:param name="title" value="Fauxwerd.com" />
</jsp:include>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="${index.title}"/>
</jsp:include>
    
<article>
	<h2><fmt:message key="index.subtitle"/></h2>
	<p><fmt:message key="index.content1"/></p>
	<!--[if IE]>
	<p class="instructions">Right click and "Add to Favorites"</p>
	<style>#instructions {display:none;}</style>
	<![endif]-->
	<p class="instructions" id="instructions">Drag to your bookmarks bar</p>
	<a href="javascript:var t;function fauxwerd(at){var d=document,b=d.body;try{if(!b){throw('Not ready');}var s=d.createElement('scr'+'ipt');s.src=d.location.protocol+'//fauxwerd.com/scripts/fauxwerd.js';b.appendChild(s);}catch(err){if(at == 10){clearTimeout(t);alert('Please allow the page to fully load, then click the bookmark again.');}else{t=setTimeout(fauxwerd,500,++at);}}}fauxwerd(1);" id="bookmarklet" class="bookmarklet">Fauxwerd</a>
	<p><a href="http://twitter.com/#!/fauxwerd"><fmt:message key="index.twitterLink"/></a></p>
</article>	  
	  
<jsp:include page="/WEB-INF/views/common/footer.jsp" />