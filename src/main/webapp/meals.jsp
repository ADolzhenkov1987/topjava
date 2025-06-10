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
<p><a href="${pageContext.request.contextPath}/meals?action=insert">Add Meal</a></p>
<H4>Meals</H4>
<table border=1>
  <tr>
    <th>Date</th>
    <th>Description</th>
    <th>Calories</th>
    <th>Update</th>
    <th>Delete</th>
  </tr>
  <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
  <c:forEach items="${mealsTo}" var="meal">
    <tr style = "text-align:left; color: ${meal.excess ? 'red': 'green'}">
      <f:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parcedDate" type="both"/>
      <f:formatDate pattern="yyyy-MM-dd HH:mm" value="${parcedDate}" var="formattedDate"/>
      <th>${formattedDate}</th>
      <th>${meal.description}</th>
      <th>${meal.calories}</th>
      <th><a href="${pageContext.request.contextPath}/meals?action=edit&mealId=<c:out value="${meal.mealId}"/>">Update</a></th>
      <th><a href="${pageContext.request.contextPath}/meals?action=delete&mealId=<c:out value="${meal.mealId}"/>">Delete</a></th>
    </tr>
  </c:forEach>
</table>
</body>
</html>