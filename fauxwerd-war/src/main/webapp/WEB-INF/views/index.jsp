<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="bookmarklet" value="javascript:var t;function fauxwerd(at){var d=document,b=d.body,l=d.location;try{if (!b){throw(0);}i=d.createElement('iframe');b.appendChild(i);i.style.display='none';var doc=i.contentDocument ? i.contentDocument : i.contentWindow.document;doc.open();doc.write('<form action="http://localhost/milwaukee/test.php" method="post" id="fauxwerd"><input type="hidden" name="url" value="'+ encodeURIComponent(location.href) + '" /></form>');doc.close();var f=doc.getElementById('fauxwerd');f.submit();}catch(err){if (at==10) {clearTimeout(t);alert('Please allow the page to fully load, then click the bookmark again.');}else{t=setTimeout(fauxwerd,500,++at);}}}fauxwerd(1);" />

<html lang="en">
  <head>
    <meta charset="utf-8" />
    <title>fauxwerd.com</title>    
  </head>
  <body>
    <header>
      <p><img src="images/fauxwerd.png" alt="faxuwerd.com"/></p>
      <h1><fmt:message key="index.title"/></h1>
    </header>
    <article>
      <h2><fmt:message key="index.subtitle"/></h2>
      <p><fmt:message key="index.content1"/></p>
	  <!--[if IE]>
<p class="instructions">Right click and "Add to Favorites"</p>
<style>#instructions {display:none;}</style>
<![endif]-->
<p class="instructions" id="instructions">Drag to your bookmarks bar</p>
<a href="javascript:var t;function fauxwerd(at){var d=document,b=d.body;try{if(!b){throw('Not ready');}var s=d.createElement('scr'+'ipt');s.src=d.location.protocol+'//fauxwerd.com/script/fauxwerd.js';b.appendChild(s);}catch(err){if(at == 10){clearTimeout(t);alert('Please allow the page to fully load, then click the bookmark again.');}else{t=setTimeout(fauxwerd,500,++at);}}}fauxwerd(1);" id="bookmarklet" class="bookmarklet">Fauxwerd</a>
      <p><a href="http://twitter.com/#!/fauxwerd"><fmt:message key="index.twitterLink"/></a></p>
    </article>
  </body>
</html>