<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Phân công nhân viên</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
        }

        /* Sidebar styling */
        .sidebar {
            position: fixed;
            top: 0;
            left: -300px;
            width: 300px;
            height: 100%;
            background-color: #d3d3d3;
            color: #333;
            padding: 20px;
            transition: all 0.3s ease;
            z-index: 1050;
        }

        .sidebar.open {
            left: 0;
        }

        .menu-icon {
            position: fixed;
            top: 20px;
            left: 20px;
            cursor: pointer;
            z-index: 1100;
        }

        .sidebar h1 {
            font-size: 2rem;
            margin-bottom: 20px;
        }

        .sidebar a {
            display: block;
            margin-bottom: 10px;
            color: #333;
            text-decoration: none;
        }

        .sidebar a:hover {
            color: #007bff;
        }
    </style>
    <script>
        function toggleSidebar() {
            document.getElementById("sidebar").classList.toggle("open");
        }

        function toggleQuantityInput(checkbox, inputId) {
            document.getElementById(inputId).disabled = !checkbox.checked;
            if (!checkbox.checked) {
                document.getElementById(inputId).value = ""; // Clear value when unchecked
            }
        }

        // Close sidebar when clicking outside
        $(document).click(function (event) {
            if (!$(event.target).closest('.sidebar, .menu-icon').length) {
                $('#sidebar').removeClass('open');
            }
        });
    </script>
</head>
<body>
    <!-- Menu Icon -->
    <img src="menu-icon.png" alt="Menu Icon" class="menu-icon" onclick="toggleSidebar()">

    <!-- Sidebar -->
    <div class="sidebar" id="sidebar">
        <br/>
        <br/>
        <h1>Company Name</h1>
        <a href="../main" onclick="toggleSidebar()"><img src="logo.png" alt="Company Logo"></a>
        <a href="../employee/filter">Employee</a>
        <a href="../employee/bonus">Bonus</a>
        <a href="../plan/list">Plans</a>
        <a href="../product/list">Product</a>
        <a href="../plan/select">Assign Employee to Plan</a>
        <a href="../scheduleemployee/list">Assigned Employee List</a>
        <a href="../employee/attendance">Employee Attendance</a>
        <a href="../attendance/list">Employee Attendance List</a>
        <a href="../user/list">User</a>
        <a href="../role/list">Role</a>
        <a href="../department/list">Department</a>
    </div>

    <!-- Main Content -->
    <div class="container" style="margin-top: 100px;">
        <h1 class="my-4">Phân công công việc cho nhân viên</h1>

        <c:if test="${not empty schedule}">
            <h3>Ngày: ${schedule.date} - Ca: ${schedule.shift} - Số lượng còn lại: ${schedule.quantity - schedule.assignedQuantity}</h3>
        </c:if>

        <c:if test="${not empty successMessage}">
            <div class="alert alert-success" role="alert">
                ${successMessage}
            </div>
        </c:if>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </c:if>

        <form action="create" method="POST">
            <input type="hidden" name="scid" value="${schedule.id}" />
            <table class="table table-bordered table-striped">
                <thead class="thead-dark">
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
                                <input type="number" id="quantity-${employee.id}" name="quantity-${employee.id}" class="form-control" min="1" disabled max="${schedule.quantity - schedule.assignedQuantity}" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <button type="submit" class="btn btn-primary" <c:if test="${schedule.quantity - schedule.assignedQuantity == 0}">disabled</c:if>>Phân công</button>
        </form>

        <a href="javascript:history.back()" class="btn btn-secondary mt-4">Quay lại</a>
    </div>
</body>
</html>
