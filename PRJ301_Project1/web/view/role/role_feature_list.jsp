<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Role and Features List</title>
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
        <h1 class="my-4">Role and Feature List</h1>

        <!-- Button to add a new role -->
        <a href="create" class="btn btn-success mb-3">Add New Role</a>

        <!-- Search bar -->
        <div class="form-group">
            <input type="text" id="searchInput" class="form-control" placeholder="Search for roles or features...">
        </div>

        <!-- Role and Features Table -->
        <table id="roleTable" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Role ID</th>
                    <th>Role Name</th>
                    <th>Features</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${roles}" var="role">
                    <tr>
                        <td>${role.id}</td>
                        <td>${role.name}</td>
                        <td>
                            <c:if test="${not empty role.features}">
                                <ul>
                                    <c:forEach items="${role.features}" var="feature">
                                        <li>${feature.name} (URL: ${feature.url})</li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                            <c:if test="${empty role.features}">
                                <em>No Features Assigned</em>
                            </c:if>
                        </td>
                        <td>
                            <a href="update?id=${role.id}" class="btn btn-primary">Update</a>
                            <a href="delete?id=${role.id}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this role?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script>
        function toggleSidebar() {
            document.getElementById("sidebar").classList.toggle("open");
        }

        // JavaScript function to filter the table rows based on user input
        $(document).ready(function () {
            $("#searchInput").on("keyup", function () {
                var value = $(this).val().toLowerCase();
                $("#roleTable tbody tr").filter(function () {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                });
            });
        });
    </script>
</body>
</html>