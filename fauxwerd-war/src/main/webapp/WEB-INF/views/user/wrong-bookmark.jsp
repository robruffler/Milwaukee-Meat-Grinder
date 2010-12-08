<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="/WEB-INF/views/common/head.jsp">
	<jsp:param name="title" value="Fauxwerd.com" />
</jsp:include>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="Oh dear, you appear to be using the wrong bookmark!"/>
</jsp:include>

<h3 class="tip">You are signed in as ${user.email}, but your bookmark is meant for someone else! Don't worry, we've supplied the correct one right here for you.</h3>

<!--[if IE]>
<p class="instructions">Right click and "Add to Favorites"</p>
<style>#instructions {display:none;}</style>
<![endif]-->
<p class="instructions" id="instructions">Drag to your bookmarks bar</p>
<a href="javascript:var t;function fauxwerd(at){var d=document,b=d.body;try{if(!b){throw(0);}var i=d.createElement('iframe');i.src=d.location.protocol+'//fauxwerd.com/iform?u=${user.id}&url='+encodeURIComponent(location.href);i.id='fauxwerd';i.width=300;i.height=250;b.appendChild(i);}catch(err){if(at == 10){clearTimeout(t);alert('Please allow the page to fully load, then click the bookmark again.');}else{t=setTimeout(fauxwerd,500,++at);}}}fauxwerd(1);" id="bookmarklet" class="bookmarklet">Fauxwerd</a>
<h3 class="url">${url}</h3>
<p class="default">
	You can still <a href="#">save</a> your article to <strong>${user.email}</strong> account, or you can <a href="#">login as a different user</a>.
</p>
	

<jsp:include page="/WEB-INF/views/common/footer.jsp" />