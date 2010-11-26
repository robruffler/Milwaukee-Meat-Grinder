<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
      <p><a href="http://twitter.com/#!/fauxwerd"><fmt:message key="index.twitterLink"/></a></p>
    </article>
  </body>
</html>