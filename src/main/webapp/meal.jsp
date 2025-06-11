<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add or edit meal</title>
</head>
<body>
<form method="POST" action='${pageContext.request.contextPath}/meals' name="frm-add-meal">
    <label>
    <input size="20" type="hidden" readonly="readonly" name="mealId"
           value="${meal.mealId}"/>
    </label> <br/>
    Date : <label>
    <input size="20" type="datetime-local" name="mealDate"
           value="${meal.dateTime}"/>
    </label> <br/>
    Description : <label>
    <input size="20" type="text" name="description"
          value="${meal.description}"/>
    </label> <br/>
    Calories : <label>
    <input size="20" type="text" name="calories"
           value="${meal.calories}"/>
    </label> <br/>

    <input type="submit" value="Submit"/>
</form>
</body>
</html>