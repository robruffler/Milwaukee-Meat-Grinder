<%@ page 
      contentType="text/html;charset=UTF-8"
      pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8"/>
    <title>List of Content</title>
  </head>
  <body>
    <h1>Check it out</h1>
    <ul>
      <c:forEach items="${content}" var="contentItem">
        <li>${contentItem.url}</li>
      </c:forEach>
    </ul>
  </body>
</html>