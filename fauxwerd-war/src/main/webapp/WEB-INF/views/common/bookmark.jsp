<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!--[if IE]>
<p class="instructions">Right click and "Add to Favorites"</p>
<style>#instructions {display:none;}</style>
<![endif]-->

<c:choose>
	<c:when test="${fn:containsIgnoreCase(site.hostname, 'fauxwerd')}">
		<c:set var="env" value="${site.hostname}"/>
	</c:when>
	<c:otherwise>
		<c:set var="env" value="localhost:8080"/>
	</c:otherwise>
</c:choose>
<p class="instructions" id="instructions">Drag to your bookmarks bar</p>
<a href="javascript:var FW={};FW.l=location,FW.t; function fauxwerd(at) { if(FW.l.hostname.indexOf('fauxwerd.com')>=0) { return; } FW.d=document; FW.b=FW.d.body; try{ if(!FW.b) { throw('__PNR__'); } FW.env='${env}';FW.sc = FW.d.createElement('scr' + 'ipt'); FW.sc.src='http://'+FW.env+'/scripts/bookmark.js';FW.u='${user.id}';FW.b.appendChild(FW.sc); } catch(err) { if(at == 10 && err === '__PNR__'){ clearTimeout(FW.t); alert('Please allow the page to fully load, then click the bookmark again.'); } else if (at == 10 && err !== '__PNR__') { clearTimeout(FW.t); alert('Please ensure your bookmark is properly installed.'); } else { FW.t = setTimeout(function(){fauxwerd(++at);},500); } } } function fauxwerdBeGone(at){ if (at === 10) {FW.b.removeChild(FW.sc);FW.b.removeChild(FW.i); } else { setTimeout(function() { fauxwerdBeGone(++at);}, 1000); } } fauxwerd(1);" id="bookmarklet" class="bookmarklet">Fauxwerd</a>