<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chọn Lịch Làm Việc</title>
    </head>
    <body>
        <h1>Chọn Lịch Làm Việc</h1>
        <form action="select" method="POST">
            <label>Chọn lịch:</label>
            <select name="scid">
                <c:forEach var="schedule" items="${schedules}">
                    <option value="${schedule.id}">Ngày: ${schedule.date} - Ca: ${schedule.shift} - Số lượng: ${schedule.quantity}</option>
                </c:forEach>
            </select>
            <button type="submit">Chọn</button>
        </form>
    </body>
</html>
