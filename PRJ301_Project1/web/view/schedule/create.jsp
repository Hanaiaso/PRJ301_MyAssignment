<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi tiết chiến dịch</title>
        <script>
            function validateTotalQuantity(totalQuantity) {
                var totalInput = 0;
                var inputs = document.querySelectorAll('input[type="number"]');
                inputs.forEach(function(input) {
                    totalInput += parseInt(input.value) || 0;
                });
                if (totalInput > totalQuantity) {
                    alert("Tổng số lượng bạn đã nhập vượt quá tổng số lượng sản phẩm phải làm!");
                    return false; 
                }
                return true;
            }
        </script>
    </head>
    <body>
        <h1>Chi tiết chiến dịch</h1>
        <c:if test="${not empty error}">
            <div style="color: red;">${error}</div>
        </c:if>
        <h3>Kế hoạch: ${plan.name}</h3>
        <h4>Sản phẩm: ${planCampain.product.name}</h4>
        <h4>Tổng số lượng sản phẩm phải làm: ${planCampain.quantity}</h4>
        <form action="create" method="POST" onsubmit="return validateTotalQuantity(${planCampain.quantity})">
            <input type="hidden" name="plcid" value="${planCampain.id}"/>
            <table border="1" cellpadding="5" cellspacing="0">
                <thead>
                    <tr>
                        <th>Ngày</th>
                        <th>Ca 1</th>
                        <th>Ca 2</th>
                        <th>Ca 3</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="date" items="${dates}">
                        <tr>
                            <td>
                                <input type="hidden" name="date" value="${date}"/>
                                ${date}
                            </td>
                            <td><input type="number" name="quantity${date}Shift1" min="0" required/></td>
                            <td><input type="number" name="quantity${date}Shift2" min="0" required/></td>
                            <td><input type="number" name="quantity${date}Shift3" min="0" required/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br/>
            <input type="submit" value="Lưu"/>
        </form>
    </body>
</html>
