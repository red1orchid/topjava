<%--
  Created by IntelliJ IDEA.
  User: PerevalovaMA
  Date: 07.06.2016
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<div>
    <table border="1" cellspacing="0" cellpadding="10">
        <tr>
            <th>Описание</th>
            <th>Дата</th>
            <th>Калории</th>
        </tr>
        <c:forEach var="meal" items="${meals}">
            <tr style="color:
            <c:choose>
            <c:when test="${meal.isExceed()}">red</c:when>
            <c:otherwise>green</c:otherwise>
            </c:choose>;">
                <td>${meal.getDescription()}</td>
                <td>${meal.getFormatedDateTime(formatter)}</td>
                <td>${meal.getCalories()}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
