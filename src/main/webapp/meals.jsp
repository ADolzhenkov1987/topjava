<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>

<html lang="ru">
<head>
  <title>Meals</title>
</head>
<body>
<h1><a href="index.html">Home</a></h1>
<hr>
<H4>Meals</H4>
<table border=1>
  <tr>
    <th>Date</th>
    <th>Description</th>
    <th>Calories</th>
    <th>Update</th>
    <th>Delete</th>
  </tr>
  <jsp:useBean id="mealsToStreams" scope="request" type="java.util.List"/>
  <c:forEach items="${mealsToStreams}" var="meal">
    <c:choose>
      <c:when test="${meal.excess}">
        <tr style = "text-align:left; color:red">
      </c:when>
      <c:otherwise>
        <tr style = "text-align:left; color:green">
      </c:otherwise>
    </c:choose>
    <f:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parcedDate" type="both"/>
    <f:formatDate pattern="dd.MM.yyyy HH:mm" value="${parcedDate}" var="formattedDate"/>
    <th><c:out value="${formattedDate}"/></th>
    <th><c:out value="${meal.description}"/></th>
    <th><c:out value="${meal.calories}"/></th>
    <th></th>
    <th></th>
    </tr>
  </c:forEach>
</table>
</body>
</html>