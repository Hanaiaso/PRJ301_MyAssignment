<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Update Employee Information</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <div class="container mt-5">
            <h3 class="mb-4 text-center">Update Employee Information</h3>



            <!-- Employee Update Form -->
            <form action="update" method="POST" id="employeeUpdateForm">
                <div class="form-group">
                    <label for="empId">Employee ID:</label>
                    <input type="text" id="empId" class="form-control" value="${requestScope.e.id}" readonly />
                    <input type="hidden" name="id" value="${requestScope.e.id}" />
                </div>

                <div class="form-group">
                    <label for="empName">Name:</label>
                    <input type="text" id="empName" name="name" class="form-control" value="${requestScope.e.name}" required />
                </div>

                <div class="form-group">
                    <label>Gender:</label><br>
                    <div class="form-check form-check-inline">
                        <input type="radio" id="genderMale" name="gender" value="male" class="form-check-input"
                               ${requestScope.e.gender ? "checked=\"checked\"" : ""} />
                        <label class="form-check-label" for="genderMale">Male</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input type="radio" id="genderFemale" name="gender" value="female" class="form-check-input"
                               ${!requestScope.e.gender ? "checked=\"checked\"" : ""} />
                        <label class="form-check-label" for="genderFemale">Female</label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="empDob">Date of Birth:</label>
                    <input type="date" id="empDob" name="dob" class="form-control" value="${requestScope.e.dob}" required />
                </div>

                <div class="form-group">
                    <label for="empAddress">Address:</label>
                    <input type="text" id="empAddress" name="address" class="form-control" value="${requestScope.e.address}" required />
                </div>

                <div class="form-group">
                    <label for="empSalary">Salary:</label>
                    <input type="number" id="empSalary" name="salary" class="form-control" value="${requestScope.e.salary}" required />
                </div>

                <div class="form-group">
                    <label for="empDept">Department:</label>
                    <select id="empDept" name="did" class="form-control">
                        <c:forEach items="${requestScope.depts}" var="d">
                            <option 
                                ${requestScope.e.dept.id eq d.id ? "selected=\"selected\"" : ""}
                                value="${d.id}">${d.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Created By:</label>
                    <input type="text" class="form-control" value="${requestScope.e.createdby.displayname}" readonly />
                </div>

                <button type="submit" class="btn btn-primary">Save</button>
                <a href="javascript:history.back()" class="btn btn-secondary ml-2">Cancel</a>
            </form>
        </div>

        <script>
            $(document).ready(function () {
                // Tìm kiếm nhân viên
                $("#searchInput").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $("#employeeUpdateForm input, #employeeUpdateForm select").each(function () {
                        if ($(this).val().toLowerCase().indexOf(value) > -1) {
                            $(this).closest('.form-group').show();
                        } else {
                            $(this).closest('.form-group').hide();
                        }
                    });
                });
            });
        </script>
    </body>
</html>
