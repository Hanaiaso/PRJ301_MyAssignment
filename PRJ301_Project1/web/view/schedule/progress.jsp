<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Progress Tracking</title>
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
    <img src="menu-icon.png" alt="Menu Icon" class="menu-icon" onclick="toggleSidebar()">

    <!-- Sidebar -->
    <div class="sidebar" id="sidebar">
        <br>
        <br>
  
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
        <h1 class="my-4">Plan Progress Tracker</h1>

        <!-- Dropdown to select a plan -->
        <form method="get" action="progress">
            <div class="form-group">
                <label for="planSelect">Select Plan:</label>
                <select id="planSelect" name="planId" class="form-control" onchange="this.form.submit()">
                    <option value="">-- Select a Plan --</option>
                    <c:forEach var="plan" items="${plans}">
                        <option value="${plan.id}" <c:if test="${param.planId == plan.id}">selected</c:if>>${plan.name}</option>
                    </c:forEach>
                </select>
            </div>
        </form>

        <!-- Section to display progress chart if data is available -->
        <c:if test="${not empty progress}">
            <div class="my-4">
                <h3>Progress Details for: <strong>${progress.plan.name}</strong></h3>
                <p><strong>Department:</strong> ${progress.department.name}</p>
                <p id="status" style="font-weight: bold;">
                    Status: <span id="statusText">${progress.status}</span>
                </p>
                <p><strong>Start Date:</strong> ${progress.plan.start}</p>
                <p><strong>End Date:</strong> ${progress.plan.end}</p>
                <p><strong>Current Date:</strong> ${currentDate}</p>
                <button class="btn btn-primary" onclick="location.href = 'progressdetails?planId=${progress.plan.id}'">View Details</button>
                
            </div>
                
            <div class="container my-4">
                <canvas id="progressChart" width="400" height="200"></canvas>
            </div>
        </c:if>
        <button onclick="javascript:history.back()" class="btn btn-primary">Back</button>
    </div>

    <script>
        $(document).ready(function () {
            // Update status color based on progress status
            var status = "${progress.status}";
            var statusElement = $('#statusText');

            if (status === 'Muộn') {
                statusElement.css('color', 'red');
            } else if (status === 'Đang tiến hành') {
                statusElement.css('color', 'blue');
            } else if (status === 'Hoàn thành') {
                statusElement.css('color', 'green');
            }

            // Display progress chart
            if (status) {
                var ctx = document.getElementById('progressChart').getContext('2d');
                var progressChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: ['Total Products', 'Completed Products', 'Remaining Products'],
                        datasets: [{
                            label: 'Progress',
                            data: [${progress.totalProducts}, ${progress.completedProducts}, ${progress.remainingProducts}],
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
            }
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
