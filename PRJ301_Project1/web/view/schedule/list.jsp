<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách các kế hoạch đã tạo</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            $(document).ready(function () {
                $("#searchInput").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $("#scheduleTable tbody tr").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                    });
                });
            });
        </script>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="mb-4 text-center">Danh sách các kế hoạch đã tạo</h1>

            <!-- Search Bar -->
            <div class="form-group mb-4">
                <input type="text" id="searchInput" class="form-control" placeholder="Tìm kiếm kế hoạch...">
            </div>

            <c:if test="${empty schedules}">
                <div class="alert alert-info" role="alert">
                    Không có kế hoạch nào đã được tạo.
                </div>
            </c:if>

            <c:if test="${not empty schedules}">
                <table id="scheduleTable" class="table table-bordered table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th>Plan ID</th>
                            <th>Tên kế hoạch</th>
                            <th>PlanCampain ID</th>
                            <th>Tên sản phẩm</th>
                            <th>Ngày</th>
                            <th>Ca</th>
                            <th>Số lượng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="schedule" items="${schedules}">
                            <tr>
                                <td>${schedule.plancampain.plan.id}</td>
                                <td>${schedule.plancampain.plan.name}</td>
                                <td>${schedule.plancampain.id}</td>
                                <td>${schedule.plancampain.product.name}</td>
                                <td>${schedule.date}</td>
                                <td>${schedule.shift}</td>
                                <td>${schedule.quantity}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <a href="javascript:history.back()" class="btn btn-secondary mt-3">Quay lại</a>
        </div>
    </body>
</html>
