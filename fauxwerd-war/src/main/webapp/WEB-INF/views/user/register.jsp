<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <title><fmt:message key="user.register.title"/> | fauxwerd.com</title>    
  </head>
  <body>
    <header>
      <p><img src="/images/fauxwerd.png" alt="faxuwerd.com"/></p>      
    </header>
     
    <article>
      <h1><fmt:message key="user.register.title"/></h1>
		  <section>  
		    <form:form modelAttribute="user" action="register" method="post">
		        <fieldset>    
		        <legend>Account Fields</legend>
<%--
		        <p>
		          <form:label for="username" path="username" cssErrorClass="error">username</form:label><br/>
		          <form:input path="username" /> <form:errors path="username" />      
		        </p>
--%>		        
		        <p> 
		          <form:label for="email" path="email" cssErrorClass="error">email</form:label><br/>
		          <form:input path="email" /> <form:errors path="email" />
		        </p>
		        <p>
		          <form:label for="password" path="password" cssErrorClass="error">password</form:label><br/>
		          <form:password path="password" /> <form:errors path="password" />
		        </p>
<%-- 		        
		        <p> 
		          <form:label for="renewalDate" path="renewalDate" cssErrorClass="error">Renewal Date</form:label><br/>
		          <form:input path="renewalDate" /> <form:errors path="renewalDate" />
		        </p>
 --%>		        
		        <p> 
		          <input type="submit" />
		        </p>
		      </fieldset>
		    </form:form>
      </section>
      
    <!--[if IE]>
<p class="instructions">Right click and "Add to Favorites"</p>
<style>#instructions {display:none;}</style>
<![endif]-->
<!-- 
<p class="instructions" id="instructions">Drag to your bookmarks bar</p>
<a href="javascript:var t;function fauxwerd(at){var d=document,b=d.body;try{if(!b){throw('Not ready');}var s=d.createElement('scr'+'ipt');s.src=d.location.protocol+'//fauxwerd.com/scripts/fauxwerd.js';b.appendChild(s);}catch(err){if(at == 10){clearTimeout(t);alert('Please allow the page to fully load, then click the bookmark again.');}else{t=setTimeout(fauxwerd,500,++at);}}}fauxwerd(1);" id="bookmarklet" class="bookmarklet">Fauxwerd</a>
      <p><a href="http://twitter.com/#!/fauxwerd"><fmt:message key="index.twitterLink"/></a></p>
-->
    </article>
    
  </body>
</html>