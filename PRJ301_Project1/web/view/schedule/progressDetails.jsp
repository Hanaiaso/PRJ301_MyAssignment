<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Progress Details</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
            <br>
            <br>


            <h1>Company Name</h1>
            <a href="main" onclick="toggleSidebar()"><img src="logo.png" alt="Company Logo"></a>
            <a href="employee/list">Employee's List</a>
            <a href="employee/filter">Employee's Filter</a>
            <a href="employee/create">Create Employee</a>
            <a href="employee/deleteall">Delete All Employees</a>
            <a href="employee/bonus">Bonus</a>
            <a href="plan/create">Create Plan</a>
            <a href="plan/list">List Plans</a>
            <a href="product/list">Product</a>
            <a href="plan/select">Assign Employee to Plan</a>
            <a href="scheduleemployee/list">Assigned Employee List</a>
            <a href="employee/attendance">Employee Attendance</a>
            <a href="attendance/list">Employee Attendance List</a>
            <a href="user/list">User</a>
            <a href="role/list">Role</a>
            <a href="department/list">Department</a>
        </div>

        <!-- Main Content -->
        <div class="container" style="margin-top: 100px;">
            <h1 class="my-4">Plan Progress Details for: ${plan.name}</h1>
            <button onclick="javascript:history.back()" class="btn btn-primary">Back</button>
            <c:forEach var="productProgress" items="${productProgresses}">
                <div class="my-4">
                    <h3>Product: <strong>${productProgress.product.name}</strong></h3>
                    <canvas id="productChart-${productProgress.product.id}" width="400" height="200"></canvas>
                </div>
            </c:forEach>

        </div>

        <script>
            $(document).ready(function () {
            <c:forEach var="productProgress" items="${productProgresses}">
                var ctx = document.getElementById('productChart-${productProgress.product.id}').getContext('2d');
                var productChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: ['Total', 'Completed', 'Remaining'],
                        datasets: [{
                                label: 'Progress for ${productProgress.product.name}',
                                data: [${productProgress.totalProducts}, ${productProgress.completedProducts}, ${productProgress.remainingProducts}],
                                backgroundColor: ['blue', 'green', 'red'],
                                fill: true
                            }]
                    },
                    options: {
                        responsive: true,
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });
            </c:forEach>
            });

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
