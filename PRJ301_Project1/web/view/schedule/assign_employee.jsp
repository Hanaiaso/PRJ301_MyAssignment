<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Phân công nhân viên</title>
    <script>
        function toggleQuantityInput(checkbox, inputId) {
            document.getElementById(inputId).disabled = !checkbox.checked;
            if (!checkbox.checked) {
                document.getElementById(inputId).value = ""; // Clear value when unchecked
            }
        }
    </script>
</head>
<body>
    <h1>Phân công công việc cho nhân viên</h1>

    <c:if test="${not empty schedule}">
        <h2>Ngày: ${schedule.date} - Ca: ${schedule.shift} - Số lượng còn lại: ${schedule.quantity - schedule.assignedQuantity}</h2>
    </c:if>

    <c:if test="${not empty successMessage}">
        <p style="color: green;">${successMessage}</p>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <p style="color: red;">${errorMessage}</p>
    </c:if>

    <form action="create" method="POST">
        <input type="hidden" name="scid" value="${schedule.id}" />
        <table>
            <thead>
                <tr>
                    <th>Chọn</th>
                    <th>Tên nhân viên</th>
                    <th>Số lượng phân công</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="employee" items="${employees}">
                    <tr>
                        <td>
                            <input type="checkbox" name="eid" value="${employee.id}" onclick="toggleQuantityInput(this, 'quantity-${employee.id}')" <c:if test="${schedule.quantity - schedule.assignedQuantity == 0}">disabled</c:if> />
                        </td>
                        <td>${employee.name}</td>
                        <td>
                            <input type="number" id="quantity-${employee.id}" name="quantity-${employee.id}" min="1" disabled max="${schedule.quantity - schedule.assignedQuantity}" />
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <button type="submit" <c:if test="${schedule.quantity - schedule.assignedQuantity == 0}">disabled</c:if>>Phân công</button>
    </form>

    <br/>
    <a href="javascript:history.back()">Quay lại</a>
</body>
</html>
