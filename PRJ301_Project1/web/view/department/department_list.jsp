<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Department List</title>
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
        <h1 class="my-4">Department List</h1>

        <!-- Search Bar -->
        <div class="form-group">
            <input type="text" id="searchInput" class="form-control" placeholder="Search for departments...">
        </div>

        <table class="table table-bordered table-striped" id="departmentTable">
            <thead>
                <tr>
                    <th>Department ID</th>
                    <th>Department Name</th>
                    <th>Type</th>
                    <th>Total Employees</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.dl}" var="d">
                    <tr>
                        <td>${d.id}</td>
                        <td>${d.name}</td>
                        <td>${d.type}</td>
                        <td>${d.totalEmployees}</td>
                        <td>
                            <button class="btn btn-danger" onclick="confirmDelete(${d.id})">Delete</button>
                            <button class="btn btn-primary" onclick="updateDepartment(${d.id})">Update</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Form to Add New Department -->
        <h3>Add New Department</h3>
        <form action="create" method="POST">
            <div class="form-group">
                <label for="deptName">Department Name:</label>
                <input type="text" id="deptName" name="dname" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="deptType">Department Type:</label>
                <input type="text" id="deptType" name="dtype" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary">Add Department</button>
        </form>
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

        // Search functionality for department table
        $(document).ready(function () {
            $("#searchInput").on("keyup", function () {
                var value = $(this).val().toLowerCase();
                $("#departmentTable tbody tr").filter(function () {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                });
            });
        });

        function confirmDelete(departmentId) {
            if (confirm("Are you sure you want to delete this department?")) {
                window.location.href = 'delete?id=' + departmentId;
            }
        }

        function updateDepartment(departmentId) {
            window.location.href = 'update?id=' + departmentId;
        }
    </script>
</body>
</html>
