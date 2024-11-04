<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chọn Kế Hoạch</title>
    </head>
    <body>
        <h1>Chọn Kế Hoạch</h1>
        <form action="select" method="POST">
            <label>Chọn kế hoạch:</label>
            <select name="plid">
                <c:forEach var="plan" items="${plans}">
                    <option value="${plan.id}">${plan.name}</option>
                </c:forEach>
            </select>
            <button type="submit">Chọn</button>
        </form>
    </body>
</html>
