<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Department List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
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
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.dl}" var="d">
                        <tr>
                            <td>${d.id}</td>
                            <td>${d.name}</td>
                            <td>${d.type}</td>
                            <td>${d.totalEmployees}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Form để thêm department mới -->
            <h3>Add New Department</h3>
            <form action="department/add" method="POST">
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
