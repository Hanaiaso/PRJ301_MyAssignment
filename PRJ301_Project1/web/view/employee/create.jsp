<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Create New Employee</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-5">
            <h3 class="mb-4 text-center">Create a New Employee</h3>
            <form action="create" method="POST">
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" class="form-control" required />
                </div>

                <div class="form-group">
                    <label>Gender:</label><br/>
                    <div class="form-check form-check-inline">
                        <input type="radio" id="male" name="gender" value="male" class="form-check-input" checked="checked" />
                        <label class="form-check-label" for="male">Male</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input type="radio" id="female" name="gender" value="female" class="form-check-input" />
                        <label class="form-check-label" for="female">Female</label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="dob">Date of Birth:</label>
                    <input type="date" id="dob" name="dob" class="form-control" required />
                </div>

                <div class="form-group">
                    <label for="address">Address:</label>
                    <input type="text" id="address" name="address" class="form-control" required />
                </div>

                <div class="form-group">
                    <label for="salary">Salary:</label>
                    <input type="number" id="salary" name="salary" class="form-control" required />
                </div>

                <div class="form-group">
                    <label for="department">Department:</label>
                    <select id="department" name="did" class="form-control">
                        <c:forEach items="${requestScope.depts}" var="d">
                            <option value="${d.id}">${d.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Save</button>
            </form>
            <br/>
            <a href="javascript:history.back()" class="btn btn-secondary">Back</a>
        </div>
    </body>
</html>
