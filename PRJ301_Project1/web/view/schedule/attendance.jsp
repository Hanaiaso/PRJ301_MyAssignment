<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chấm Công Nhân Viên</title>
        <script>
            function toggleInput(checkbox, quantityInputId, alphaInputId) {
                document.getElementById(quantityInputId).disabled = !checkbox.checked;
                document.getElementById(alphaInputId).disabled = !checkbox.checked;
                if (!checkbox.checked) {
                    document.getElementById(quantityInputId).value = ""; // Clear value when unchecked
                    document.getElementById(alphaInputId).value = "";
                }
            }
        </script>
    </head>
    <body>
        <h1>Chấm Công Nhân Viên</h1>

        <c:if test="${not empty schedule}">
            <h2>Ngày: ${schedule.date} - Ca: ${schedule.shift} - Số lượng còn lại: ${schedule.quantity - schedule.assignedQuantity}</h2>
        </c:if>

        <c:if test="${not empty successMessage}">
            <p style="color: green;">${successMessage}</p>
        </c:if>

        <c:if test="${not empty errorMessage}">
            <p style="color: red;">${errorMessage}</p>
        </c:if>

        <form action="attendance" method="POST">
            <input type="hidden" name="scid" value="${schedule.id}" />
            <table>
                <thead>
                    <tr>
                        <th>Chọn</th>
                        <th>Tên nhân viên</th>
                        <th>Số lượng hoàn thành</th>
                        <th>Hệ số Alpha</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="employee" items="${assignedEmployees}">
                        <tr>
                            <td>
                                <input type="checkbox" name="eid" value="${employee.employee.id}" onclick="toggleInput(this, 'quantity-${employee.employee.id}', 'alpha-${employee.employee.id}')" />
                            </td>
                            <td>${employee.employee.name}</td>
                            <td>
                                <input type="number" id="quantity-${employee.employee.id}" name="quantity-${employee.employee.id}" min="0" disabled />
                            </td>
                            <td>
                                <input type="text" id="alpha-${employee.employee.id}" name="alpha-${employee.employee.id}" disabled />
                            </td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>
            <button type="submit">Chấm Công</button>
        </form>

        <br/>
        <a href="javascript:history.back()">Quay lại</a>
    </body>
</html>
