<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Add new meal</title>
</head>
<body>
<form method="POST" action='meal.jsp' name="frmAddMeal">
    Meal ID : <label>
    <input size="20" type="text" readonly="readonly" name="mealId"
           value="<c:out value="${meal.mealId}" />"/>
</label> <br/>
    Date : <label>
    <input size="20" type="datetime-local" name="date"
           value="<c:out value="${meal.dateTime}" />"/>
</label> <br/>
    Description : <label>
    <input size="20" type="text" name="description"
                         value="<c:out value="${meal.description}" />"/>
</label> <br/>
    Calories : <label>
    <input size="20" type="text" name="calories"
                      value="<c:out value="${meal.calories}" />"/>
</label> <br/>

    <input type="submit" value="Submit"/>
</form>
</body>
</html>