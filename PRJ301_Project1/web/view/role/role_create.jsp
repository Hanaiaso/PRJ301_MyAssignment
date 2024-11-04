<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Role</title>
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
        <img src="../../img/logo_3.jpeg" alt="Menu Icon" class="menu-icon" onclick="toggleSidebar()">

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
            <h1 class="my-4">Create New Role</h1>

            <form action="create" method="POST">
                <div class="form-group">
                    <label for="rname">Role Name:</label>
                    <input type="text" id="rname" name="rname" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="features">Select Features:</label>
                    <select id="features" name="features" class="form-control" multiple>
                        <c:forEach items="${features}" var="feature">
                            <option value="${feature.id}">${feature.name}</option>
                        </c:forEach>
                    </select>
                    <small class="form-text text-muted">Hold down the Ctrl (windows) or Command (Mac) button to select multiple options.</small>
                </div>

                <button type="submit" class="btn btn-primary">Create Role</button>
            </form>

            <br/>
            <a href="list" class="btn btn-secondary">Back to Role List</a>
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
