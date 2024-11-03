<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Employee List</title>
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
        <h1 class="my-4">Employee List</h1>

        <!-- Form Buttons -->
        <a href="create" class="btn btn-primary mb-3">Add Employee</a>
        <a href="deleteall" class="btn btn-danger mb-3">Delete All Employees</a>
        <a href="list" class="btn btn-info mb-3">Employee's List</a>

        <!-- Search Form -->
        <form action="filter" method="GET" class="mb-4">
            <div class="form-row">
                <div class="form-group col-md-3">
                    <label for="id">ID</label>
                    <input type="text" name="id" value="${param.id}" class="form-control" id="id">
                </div>
                <div class="form-group col-md-3">
                    <label for="name">Name</label>
                    <input type="text" name="name" value="${param.name}" class="form-control" id="name">
                </div>
                <div class="form-group col-md-3">
                    <label>Gender</label><br>
                    <div class="form-check form-check-inline">
                        <input type="radio" name="gender" class="form-check-input" id="male" value="male"
                               ${param.gender ne null && param.gender eq "male" ? "checked" : ""}>
                        <label class="form-check-label" for="male">Male</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input type="radio" name="gender" class="form-check-input" id="female" value="female"
                               ${param.gender ne null && param.gender eq "female" ? "checked" : ""}>
                        <label class="form-check-label" for="female">Female</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input type="radio" name="gender" class="form-check-input" id="both" value="both"
                               ${param.gender eq null or param.gender eq "both" ? "checked" : ""}>
                        <label class="form-check-label" for="both">Both</label>
                    </div>
                </div>
                <div class="form-group col-md-3">
                    <label for="dobFrom">DOB - From</label>
                    <input type="date" name="from" value="${param.from}" class="form-control" id="dobFrom">
                </div>
                <div class="form-group col-md-3">
                    <label for="dobTo">To</label>
                    <input type="date" name="to" value="${param.to}" class="form-control" id="dobTo">
                </div>
                <div class="form-group col-md-3">
                    <label for="address">Address</label>
                    <input type="text" name="address" value="${param.address}" class="form-control" id="address">
                </div>
                <div class="form-group col-md-3">
                    <label for="did">Department</label>
                    <select name="did" class="form-control" id="did">
                        <option value="-1">All Departments</option>
                        <c:forEach items="${requestScope.depts}" var="d">
                            <option value="${d.id}" ${param.did ne null && param.did eq d.id ? "selected" : ""}>
                                ${d.name} - ${d.type}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <button type="submit" class="btn btn-success">Search</button>
        </form>

        <!-- Employee Table -->
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Gender</th>
                    <th>DOB</th>
                    <th>Address</th>
                    <th>Department</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.emps}" var="e">
                    <tr>
                        <td>${e.id}</td>
                        <td>${e.name}</td>
                        <td>${e.gender ? "Male" : "Female"}</td>
                        <td>${e.dob}</td>
                        <td>${e.address}</td>
                        <td>${e.dept.name} - ${e.dept.type}</td>
                        <td>
                            <button class="btn btn-primary" onclick="updateEmployee(${e.id})">Edit</button>
                            <button class="btn btn-danger" onclick="removeEmployee(${e.id})">Remove</button>
                            <form id="frmRemoveEmployee${e.id}" action="delete" method="POST" style="display:none;">
                                <input type="hidden" name="id" value="${e.id}"/>
                            </form>
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

        function removeEmployee(id) {
            var result = confirm("Are you sure?");
            if (result) {
                document.getElementById("frmRemoveEmployee" + id).submit();
            }
        }

        function updateEmployee(employeeId) {
            window.location.href = 'update?id=' + employeeId;
        }
    </script>
</body>
</html>
