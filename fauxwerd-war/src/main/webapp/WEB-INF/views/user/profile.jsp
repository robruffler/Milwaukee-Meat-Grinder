<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <title>fauxwerd.com</title>    
  </head>
  <body>
    <header>
      <p><img src="/images/fauxwerd.png" alt="faxuwerd.com"/></p>
      <h1><fmt:message key="user.profile.title"/></h1>
    </header>
    <div>      
      User = <sec:authentication property="principal"/>
    </div>
<!--     
    <article>
      <h2><fmt:message key="index.subtitle"/></h2>
      <p><fmt:message key="index.content1"/></p>
-->      
    <!--[if IE]>
<p class="instructions">Right click and "Add to Favorites"</p>
<style>#instructions {display:none;}</style>
<![endif]-->
<!-- 
<p class="instructions" id="instructions">Drag to your bookmarks bar</p>
<a href="javascript:var t;function fauxwerd(at){var d=document,b=d.body;try{if(!b){throw('Not ready');}var s=d.createElement('scr'+'ipt');s.src=d.location.protocol+'//fauxwerd.com/scripts/fauxwerd.js';b.appendChild(s);}catch(err){if(at == 10){clearTimeout(t);alert('Please allow the page to fully load, then click the bookmark again.');}else{t=setTimeout(fauxwerd,500,++at);}}}fauxwerd(1);" id="bookmarklet" class="bookmarklet">Fauxwerd</a>
      <p><a href="http://twitter.com/#!/fauxwerd"><fmt:message key="index.twitterLink"/></a></p>
    </article>
 -->    
  </body>
</html>