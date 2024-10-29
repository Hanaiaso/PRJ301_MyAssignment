<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Department List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            function confirmDelete(departmentId) {
                if (confirm("Are you sure you want to delete this department?")) {
                    window.location.href = 'delete?id=' + departmentId;
                }
            }
            function updateDepartment(departmentId) {
                window.location.href = 'update?id=' + departmentId;
            }
        </script>
    </head>
    <body>
        <div class="container">
            <h1>Department List</h1>
            <table border="1" class="table table-striped">
                <thead>
                    <tr>
                        <th>Department ID</th>
                        <th>Department Name</th>
                        <th>Type</th>
                        <th>Total Employees</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.dl}" var="d">
                        <tr>
                            <td>${d.id}</td>
                            <td>${d.name}</td>
                            <td>${d.type}</td>
                            <td>${d.totalEmployees}</td>
                            <td>
                                <button class="btn btn-danger" onclick="confirmDelete(${d.id})">Delete</button>
                                <button class="btn btn-primary" onclick="updateDepartment(${d.id})">Update</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Form để thêm department mới -->
            <h3>Add New Department</h3>
            <form action="create" method="POST">
                <div class="form-group">
                    <label for="deptName">Department Name:</label>
                    <input type="text" id="deptName" name="dname" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="deptType">Department Type:</label>
                    <input type="text" id="deptType" name="dtype" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-primary">Add Department</button>
            </form>
        </div>
    </body>
</html>
