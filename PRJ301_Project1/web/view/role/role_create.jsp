<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Role</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1 class="my-4">Create New Role</h1>

        <form action="create" method="POST">
            <div class="form-group">
                <label for="rname">Role Name:</label>
                <input type="text" id="rname" name="rname" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="features">Select Features:</label>
                <select id="features" name="features" class="form-control" multiple>
                    <c:forEach items="${features}" var="feature">
                        <option value="${feature.id}">${feature.name}</option>
                    </c:forEach>
                </select>
                <small class="form-text text-muted">Hold down the Ctrl (windows) or Command (Mac) button to select multiple options.</small>
            </div>

            <button type="submit" class="btn btn-primary">Create Role</button>
        </form>

        <br/>
        <a href="list" class="btn btn-secondary">Back to Role List</a>
    </div>
</body>
</html>
