<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Role and Features List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            function confirmDelete(roleId) {
                if (confirm("Are you sure you want to delete this role?")) {
                    window.location.href = 'delete?id=' + roleId;
                }
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
    </head>
    <body>
        <div class="container">
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
                                <button class="btn btn-danger" onclick="confirmDelete(${role.id})">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>