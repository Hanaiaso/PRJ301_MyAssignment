<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Employee Bonus List</title>
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
        <h1 class="my-4">Employee Bonus List</h1>

        <!-- Plan Selection Form -->
        <form id="planForm" action="bonus" method="GET" class="mb-4">
            <div class="form-group">
                <label for="planSelect">Select a Plan:</label>
                <select name="planId" id="planSelect" class="form-control" required onchange="document.getElementById('planForm').submit();">
                    <option value="">-- Select a Plan --</option>
                    <c:forEach items="${plans}" var="plan">
                        <option value="${plan.id}" <c:if test="${selectedPlanId == plan.id}">selected</c:if>>${plan.name}</option>
                    </c:forEach>
                </select>
            </div>
        </form>

        <!-- Search Bar -->
        <div class="form-group">
            <input type="text" id="searchInput" class="form-control" placeholder="Search for employees...">
        </div>

        <!-- Employee Bonuses Table -->
        <c:if test="${not empty employees}">
            <table class="table table-bordered table-striped mt-4" id="employeeTable">
                <thead class="thead-dark">
                    <tr>
                        <th>Employee ID</th>
                        <th>Employee Name</th>
                        <th>Additional Bonus (VND)</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${employees}" var="employee">
                        <tr>
                            <td>${employee.id}</td>
                            <td>${employee.name}</td>
                            <td>${employee.additionalBonus}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty employees && not empty selectedPlanId}">
            <div class="alert alert-info mt-4">No employees found for the selected plan.</div>
        </c:if>

        <a href="javascript:history.back()" class="btn btn-secondary mt-4">Go Back</a>
    </div>

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

        // Search functionality
        $(document).ready(function () {
            $("#searchInput").on("keyup", function () {
                var value = $(this).val().toLowerCase();
                $("#employeeTable tbody tr").filter(function () {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                });
            });
        });
    </script>
</body>
</html>
