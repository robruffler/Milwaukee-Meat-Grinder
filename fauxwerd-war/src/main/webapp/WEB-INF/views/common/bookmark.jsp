<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!--[if IE]>
<style type="text/css">
	#instructions-ie { display: block; }
	#instructions { display: none; }
</style>
<![endif]-->

<c:choose>
	<c:when test="${fn:containsIgnoreCase(site.hostname, 'fauxwerd')}">
		<c:set var="env" value="${site.hostname}"/>
	</c:when>
	<c:otherwise>
		<c:set var="env" value="localhost:8080"/>
	</c:otherwise>
</c:choose>
<c:set var="anchor" value="javascript:var FW={};FW.l=location,FW.t; function fauxwerd(at) { if(FW.l.hostname.indexOf('fauxwerd.com')>=0) { return; } FW.d=document; FW.b=FW.d.body; try{ if(!FW.b) { throw('__PNR__'); } FW.env='${env}';FW.sc = FW.d.createElement('scr' + 'ipt'); FW.sc.src='http://'+FW.env+'/scripts/bookmark.js';FW.u='${user.id}';FW.b.appendChild(FW.sc); } catch(err) { if(at == 10 && err === '__PNR__'){ clearTimeout(FW.t); alert('Please allow the page to fully load, then click the bookmark again.'); } else if (at == 10 && err !== '__PNR__') { clearTimeout(FW.t); alert('Please ensure your bookmark is properly installed.'); } else { FW.t = setTimeout(function(){fauxwerd(++at);},500); } } } function fauxwerdBeGone(at){ if (at === 10) {FW.b.removeChild(FW.sc);FW.b.removeChild(FW.i); } else { setTimeout(function() { fauxwerdBeGone(++at);}, 1000); } } fauxwerd(1);" />
<p class="instructions" id="instructions">
	<span class="pop">Drag</span> the 
	<a href="${anchor}" id="bookmarklet" class="bookmarklet"><span class="seo">Fauxwerd</span></a>
	button to your bookmarks bar!
</p>

<p class="instructions" id="instructions-ie">
	<span class="pop">Right-click</span> the 
	<a href="${anchor}" id="bookmarklet" class="bookmarklet"><span class="seo">Fauxwerd</span></a>
	button and "Add to Favorites!"
</p>