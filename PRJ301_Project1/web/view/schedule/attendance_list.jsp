<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Attendance List</title>
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

            // Close sidebar when clicking outside
            $(document).click(function (event) {
                if (!$(event.target).closest('.sidebar, .menu-icon').length) {
                    $('#sidebar').removeClass('open');
                }
            });

            // Search functionality for the table
            $(document).ready(function () {
                $("#searchInput").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $("#attendanceTable tbody tr").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                    });
                });
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
            <h1 class="my-4 text-center">Attendance List</h1>

            <!-- Search Bar -->
            <div class="form-group">
                <input type="text" id="searchInput" class="form-control" placeholder="Search for employees...">
            </div>

            <!-- Attendance Table -->
            <table class="table table-bordered table-striped" id="attendanceTable">
                <thead class="thead-dark">
                    <tr>
                        <th>Plan Name</th>
                        <th>Employee Name</th>
                        <th>Product Name</th>
                        <th>Date</th>
                        <th>Shift</th>
                        <th>Assigned Quantity</th>
                        <th>Completed Quantity</th>
                        <th>Alpha</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="attendance" items="${attendances}">
                        <tr>
                            <td>${attendance.scheduleEmployee.schedulecampain.plancampain.plan.name}</td>
                            <td>${attendance.scheduleEmployee.employee.name}</td>
                            <td>${attendance.scheduleEmployee.schedulecampain.plancampain.product.name}</td>
                            <td>${attendance.scheduleEmployee.schedulecampain.date}</td>
                            <td>${attendance.scheduleEmployee.schedulecampain.shift}</td>
                            <td>${attendance.scheduleEmployee.quantity}</td>
                            <td>${attendance.quantity}</td>
                            <td>${attendance.alpha}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
