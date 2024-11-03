<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Nhân viên Đã Được Phân Công</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
    <script>
        $(document).ready(function () {
            // Search functionality for the assigned employee table
            $("#searchInput").on("keyup", function () {
                var value = $(this).val().toLowerCase();
                $("#assignedEmployeesTable tbody tr").filter(function () {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                });
            });
        });
    </script>
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4 text-center">Danh sách Nhân viên Đã Được Phân Công</h1>

        <!-- Search Bar -->
        <div class="form-group">
            <input type="text" id="searchInput" class="form-control" placeholder="Tìm kiếm nhân viên...">
        </div>

        <c:if test="${empty assignedEmployees}">
            <div class="alert alert-info" role="alert">
                Không có nhân viên nào được phân công.
            </div>
        </c:if>

        <c:if test="${not empty assignedEmployees}">
            <table class="table table-bordered table-striped" id="assignedEmployeesTable">
                <thead class="thead-dark">
                    <tr>
                        <th>Plan Name</th>
                        <th>PlanCampain ID</th>
                        <th>Department Name</th>
                        <th>Date</th>
                        <th>Shift</th>
                        <th>Employee ID</th>
                        <th>Employee Name</th>
                        <th>Số Lượng Được Phân Công</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="assignment" items="${assignedEmployees}">
                        <tr>
                            <td>${assignment.planName}</td>
                            <td>${assignment.planCampainId}</td>
                            <td>${assignment.departmentName}</td>
                            <td>${assignment.date}</td>
                            <td>${assignment.shift}</td>
                            <td>${assignment.employee.id}</td>
                            <td>${assignment.employee.name}</td>
                            <td>${assignment.quantity}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <br/>
        <a href="javascript:history.back()" class="btn btn-secondary">Quay lại</a>
    </div>
</body>
</html>
