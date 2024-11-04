<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi tiết chiến dịch</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            function validateTotalQuantity(totalQuantity) {
                var totalInput = 0;
                var inputs = document.querySelectorAll('input[type="number"]');
                inputs.forEach(function (input) {
                    totalInput += parseInt(input.value) || 0;
                });
                if (totalInput > totalQuantity) {
                    alert("Tổng số lượng bạn đã nhập vượt quá tổng số lượng sản phẩm phải làm!");
                    return false;
                }
                return true;
            }
        </script>
        <style>
            body {
                background-color: #f5f5f5;
                font-family: Arial, sans-serif;
                padding-top: 20px;
            }
            .form-container {
                max-width: 900px;
                margin: 0 auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
        </style>
    </head>
    <body>

        <div class="container">
            <div class="form-container">
                <h1 class="text-center mb-4">Chi tiết chiến dịch</h1>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:if>

                <h3 class="mb-3">Kế hoạch: ${plan.name}</h3>
                <h4 class="mb-3">Sản phẩm: ${planCampain.product.name}</h4>
                <h4 class="mb-3">Tổng số lượng sản phẩm phải làm: ${planCampain.quantity}</h4>
                <h4 class="mb-3">Tổng số lượng đã nhập: ${totalInputQuantity}</h4>

                <form action="create" method="POST" onsubmit="return validateTotalQuantity(${planCampain.quantity})">
                    <input type="hidden" name="plcid" value="${planCampain.id}"/>
                    <table class="table table-bordered">
                        <thead class="thead-light">
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
                                    <c:forEach var="shift" begin="1" end="3">
                                        <td>
                                            <c:set var="existingQuantity" value="0"/>
                                            <c:forEach var="schedule" items="${existingSchedules}">
                                                <c:if test="${schedule.date.toString() == date && schedule.shift == shift}">
                                                    <c:set var="existingQuantity" value="${schedule.quantity}"/>
                                                </c:if>
                                            </c:forEach>
                                            <input type="number" name="quantity${date}Shift${shift}" class="form-control" min="0" value="${existingQuantity}" required/>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <br/>
                    <button type="submit" class="btn btn-primary btn-block">Lưu</button>
                </form>
            </div>
        </div>

    </body>
</html>
