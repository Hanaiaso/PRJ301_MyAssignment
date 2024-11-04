<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chọn Chiến Dịch</title>
    </head>
    <body>
        <h1>Chọn Chiến Dịch</h1>
        <form action="select" method="POST">
            <label>Chọn chiến dịch:</label>
            <select name="plcid">
                <c:forEach var="campain" items="${planCampains}">
                    <option value="${campain.id}">${campain.product.name} - Số lượng: ${campain.quantity}</option>
                </c:forEach>
            </select>
            <button type="submit">Chọn</button>
        </form>
    </body>
</html>
