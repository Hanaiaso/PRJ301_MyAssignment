<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Plan List</title>
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
        <h1 class="my-4">Plan List</h1>

        <!-- Search Bar -->
        <div class="form-group">
            <input type="text" id="searchInput" class="form-control" placeholder="Search for plans...">
        </div>

        <a href="create" class="btn btn-primary mb-4">Create Plan</a>
        <a href="../schedulecampain/list" class="btn btn-primary mb-4">Detail Plan</a>
        

        <table class="table table-bordered table-striped" id="planTable">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Plan Name</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Department</th>
                    <th>Product</th>
                    <th>Quantity</th>
                    <th>Estimate</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty plans}">
                        <c:forEach var="plan" items="${plans}">
                            <c:if test="${not empty plan.campains}">
                                <c:forEach var="campaign" items="${plan.campains}">
                                    <tr>
                                        <td>${plan.id}</td>
                                        <td>${plan.name}</td>
                                        <td>${plan.start}</td>
                                        <td>${plan.end}</td>
                                        <td>${plan.dept.name}</td>
                                        <td>${campaign.product.name}</td>
                                        <td>${campaign.quantity}</td>
                                        <td>${campaign.cost}</td>
                                        <td>
                                            <button class="btn btn-primary" onclick="updatePlan(${plan.id})">Update</button>
                                            <button class="btn btn-danger" onclick="confirmDelete(${plan.id})">Delete</button>
                                            <form id="frmRemoveEmployee${plan.id}" action="delete" method="POST" style="display:none;">
                                                <input type="hidden" name="id" value="${plan.id}"/>
                                            </form>
                                            <a href="../schedulecampain/create?plid=${plan.id}&plcid=${campaign.id}" class="btn btn-success mt-2">Add Detailed Plan</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty plan.campains}">
                                <tr>
                                    <td>${plan.id}</td>
                                    <td>${plan.name}</td>
                                    <td>${plan.start}</td>
                                    <td>${plan.end}</td>
                                    <td>${plan.dept.name}</td>
                                    <td colspan="3">No product assigned.</td>
                                    <td>
                                        <button class="btn btn-primary" onclick="updatePlan(${plan.id})">Update</button>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="9">No plans available.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
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

        // Search functionality for plan table
        $(document).ready(function () {
            $("#searchInput").on("keyup", function () {
                var value = $(this).val().toLowerCase();
                $("#planTable tbody tr").filter(function () {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                });
            });
        });

        function confirmDelete(planId) {
            if (confirm("Are you sure you want to delete this plan?")) {
                document.getElementById("frmRemoveEmployee" + planId).submit();
            }
        }

        function updatePlan(planId) {
            window.location.href = 'update?id=' + planId;
        }
    </script>
</body>
</html>
