<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Product List</title>
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

        #createForm {
            display: none;
            margin-top: 20px;
            padding: 15px;
            border: 1px solid #ccc;
            background-color: #f9f9f9;
        }

        #searchBar {
            margin-bottom: 20px;
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
        <h1 class="my-4">Product List</h1>

        <a href="javascript:void(0)" id="createProductButton" class="btn btn-success mb-3">Create Product</a>
        <a href="delete" class="btn btn-success mb-3">Delete Product</a>
        <a href="update" class="btn btn-success mb-3">Update Product</a>

        <!-- Form to Create New Product -->
        <div id="createForm">
            <h3>Create a new product</h3>
            <form action="create" method="POST">
                <div class="form-group">
                    <label for="pname">Product Name:</label>
                    <input type="text" name="pname" class="form-control" required>
                </div>
                <input type="submit" value="Save" class="btn btn-primary">
            </form>
            <button id="closeFormButton" class="btn btn-secondary mt-3">Close</button>
        </div>

        <!-- Search Bar for Products -->
        <div id="searchBar">
            <label for="searchInput">Search Product:</label>
            <input type="text" id="searchInput" class="form-control" placeholder="Enter product name...">
        </div>

        <!-- Product Table -->
        <table class="table table-bordered table-striped" id="productTable">
            <thead>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.pro}" var="p">
                    <tr>
                        <td class="productId">${p.id}</td>
                        <td class="productName">${p.name}</td>
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

        // jQuery for product list interactions
        $(document).ready(function () {
            // Show create product form
            $("#createProductButton").on("click", function () {
                $("#createForm").show();
            });

            // Close create product form
            $("#closeFormButton").on("click", function () {
                $("#createForm").hide();
            });

            // Search functionality for product table
            $("#searchInput").on("keyup", function () {
                var value = $(this).val().toLowerCase();
                $("#productTable tbody tr").filter(function () {
                    $(this).toggle($(this).find(".productName").text().toLowerCase().indexOf(value) > -1);
                });
            });
        });
    </script>
</body>
</html>
