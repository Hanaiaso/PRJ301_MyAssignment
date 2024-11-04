<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Department</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <h3>Update Department Information</h3>
            <form action="update" method="POST">
                <input type="hidden" name="id" value="${department.id}" />
                <div class="form-group">
                    <label for="deptName">Department Name:</label>
                    <input type="text" id="deptName" name="dname" class="form-control" value="${department.name}" required />
                </div>
                <div class="form-group">
                    <label for="deptType">Department Type:</label>
                    <input type="text" id="deptType" name="dtype" class="form-control" value="${department.type}" required />
                </div>
                <button type="submit" class="btn btn-primary">Update Department</button>
            </form>
            <a href="javascript:history.back()">Go Back</a>
        </div>
    </body>
</html>
