<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Attendance Management</title>
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

            // Update Plan Campaign options based on selected Plan
            function updatePlanCampain() {
                var planId = $("#planSelect").val();
                $.ajax({
                    url: 'attendance',
                    method: 'GET',
                    data: {plid: planId},
                    dataType: 'json',
                    success: function (data) {
                        var planCampainSelect = $('#planCampainSelect');
                        planCampainSelect.empty();
                        planCampainSelect.append('<option value="">Select Plan Campaign</option>');
                        $.each(data, function (index, planCampain) {
                            planCampainSelect.append('<option value="' + planCampain.id + '">' + planCampain.product.name + ' - ' + planCampain.quantity + '</option>');
                        });
                    },
                    error: function (xhr, status, error) {
                        console.error('Failed to fetch plan campaigns:', status, error);
                    }
                });
            }

            // Update Schedule Campaign options based on selected Plan Campaign
            function updateScheduleCampain() {
                var planCampainId = $("#planCampainSelect").val();
                $.ajax({
                    url: 'attendance',
                    method: 'GET',
                    data: {plcid: planCampainId},
                    dataType: 'json',
                    success: function (data) {
                        var scheduleCampainSelect = $('#scheduleCampainSelect');
                        scheduleCampainSelect.empty();
                        scheduleCampainSelect.append('<option value="">Select Schedule Campaign</option>');
                        $.each(data, function (index, scheduleCampain) {
                            scheduleCampainSelect.append('<option value="' + scheduleCampain.id + '">' + scheduleCampain.date + ' - Shift: ' + scheduleCampain.shift + ' - Quantity ' + scheduleCampain.quantity + '</option>');
                        });
                    },
                    error: function (xhr, status, error) {
                        console.error('Failed to fetch schedule campaigns:', status, error);
                    }
                });
            }

            // Update Schedule Employee options based on selected Schedule Campaign
            function updateScheduleEmployee() {
                var scheduleCampainId = $("#scheduleCampainSelect").val();
                $("#scheduleCampainId").val(scheduleCampainId);
                $.ajax({
                    url: 'attendance',
                    method: 'GET',
                    data: {scid: scheduleCampainId},
                    dataType: 'json',
                    success: function (data) {
                        var scheduleEmployeeTable = $('#scheduleEmployeeTable tbody');
                        scheduleEmployeeTable.empty();
                        $.each(data, function (index, scheduleEmployee) {
                            if (scheduleEmployee.employee) {
                                var employee = scheduleEmployee.employee;
                                var isAttendanceRecorded = scheduleEmployee.isAttendanceRecorded;
                                var row = '<tr>' +
                                        '<td>' + employee.id + '</td>' +
                                        '<td>' + employee.name + '</td>' +
                                        '<td>' + scheduleEmployee.quantity + '</td>';
                                if (isAttendanceRecorded) {
                                    row += '<td><input type="number" name="quantity-' + employee.id + '" min="0" value="' + (scheduleEmployee.attendanceQuantity || '') + '" disabled /></td>' +
                                            '<td><input type="text" name="alpha-' + employee.id + '" value="' + (scheduleEmployee.alpha || '') + '" disabled /></td>' +
                                            '<td><span style="color: red;">Already recorded</span></td>';
                                } else {
                                    row += '<td><input type="number" name="quantity-' + employee.id + '" min="0" value="' + (scheduleEmployee.attendanceQuantity || '') + '" /></td>' +
                                            '<td><input type="text" name="alpha-' + employee.id + '" value="' + (scheduleEmployee.alpha || '') + '" /></td>';
                                }
                                row += '</tr>';
                                scheduleEmployeeTable.append(row);
                            }
                        });
                    },
                    error: function (xhr, status, error) {
                        console.error('Failed to fetch schedule employees:', status, error);
                    }
                });
            }

            function validateForm() {
                var scheduleCampainId = $("#scheduleCampainId").val();
                if (!scheduleCampainId) {
                    alert("Please select a Schedule Campaign before submitting.");
                    return false;
                }
                return true;
            }

            $(document).ready(function () {
                // Search functionality for the schedule employees table
                $("#searchInput").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $("#scheduleEmployeeTable tbody tr").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                    });
                });
            });
        </script>
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
            <h1>Attendance Management</h1>
            <!-- Section One: Selection -->
            <div class="form-group">
                <h2>Select Plan, Campaign, and Schedule</h2>

                <!-- Dropdown chọn Plan -->
                <label for="planSelect">Select Plan:</label>
                <select id="planSelect" class="form-control" onchange="updatePlanCampain()">
                    <option value="">Select Plan</option>
                    <c:forEach var="plan" items="${plans}">
                        <option value="${plan.id}">${plan.name}</option>
                    </c:forEach>
                </select>

                <!-- Dropdown chọn Plan Campaign - được cập nhật động bằng AJAX -->
                <label for="planCampainSelect">Select Plan Campaign:</label>
                <select id="planCampainSelect" class="form-control" onchange="updateScheduleCampain()">
                    <option value="">Select Plan Campaign</option>
                </select>

                <!-- Dropdown chọn Schedule Campaign - được cập nhật động bằng AJAX -->
                <label for="scheduleCampainSelect">Select Schedule Campaign:</label>
                <select id="scheduleCampainSelect" class="form-control" onchange="updateScheduleEmployee()">
                    <option value="">Select Schedule Campaign</option>
                </select>
            </div>

            <!-- Section Two: Schedule Employees -->
            <div class="mt-4">
                <h2>Schedule Employees</h2>

                <!-- Search Bar -->
                <div class="form-group">
                    <input type="text" id="searchInput" class="form-control" placeholder="Tìm kiếm nhân viên...">
                </div>

                <form action="attendance" method="post" onsubmit="return validateForm();">
                    <input type="hidden" name="scheduleCampainId" id="scheduleCampainId" />
                    <table id="scheduleEmployeeTable" class="table table-bordered table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th>Employee ID</th>
                                <th>Employee Name</th>
                                <th>Assigned Quantity</th>
                                <th>Completed Quantity</th>
                                <th>Alpha</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Các ScheduleEmployee sẽ được thêm vào đây bằng AJAX -->
                        </tbody>
                    </table>
                    <button type="submit" class="btn btn-primary">Submit Attendance</button>
                </form>
            </div>
        </div>
    </body>
</html>
