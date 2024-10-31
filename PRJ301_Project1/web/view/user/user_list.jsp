<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User List</title>
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
        <h1>Company Name</h1>
        <a href="../main" onclick="toggleSidebar()"><img src="logo.png" alt="Company Logo"></a>
        <a href="../employee/list">Employee's List</a>
        <a href="../employee/filter">Employee's Filter</a>
        <a href="../employee/create">Create Employee</a>
        <a href="../employee/deleteall">Delete All Employees</a>
        <a href="../employee/bonus">Bonus</a>
        <a href="../plan/create">Create Plan</a>
        <a href="../plan/list">List Plans</a>
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
        <a href="create"><button type="button" class="btn btn-primary mb-4">Create User</button></a>
        <a href="update"><button type="button" class="btn btn-primary mb-4">Update User</button></a>
        <a href="delete"><button type="button" class="btn btn-primary mb-4">Delete User</button></a>
        
        <h1 class="my-4">User List</h1>
        
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Display Name</th>
                    <th>Role</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${user}" var="u">
                    <tr>
                        <td>${u.username}</td>
                        <td>***</td> <!-- Hiển thị mật khẩu bị ẩn -->
                        <td>${u.displayname}</td>
                        <td>
                            <c:choose>
                                <c:when test="${u.roles == null}">
                                    Not added yet
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${u.roles}" var="r">
                                        ${r.name}<br/>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
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

        // Close sidebar when clicking outside
        $(document).click(function (event) {
            if (!$(event.target).closest('.sidebar, .menu-icon').length) {
                $('#sidebar').removeClass('open');
            }
        });
    </script>
</body>
</html>
