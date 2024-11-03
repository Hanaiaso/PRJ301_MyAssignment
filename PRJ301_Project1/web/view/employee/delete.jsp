<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Employee Management</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            function confirmDelete() {
                const checkboxes = document.querySelectorAll('input[name="eids"]:checked');
                if (checkboxes.length === 0) {
                    alert("Please select at least one employee to delete.");
                    return false; 
                }
                var result = confirm("Are you sure you want to delete the selected employees?");
                return result;
            }

            $(document).ready(function () {
                // Tìm kiếm nhân viên theo tên
                $("#searchInput").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $("#employeeTable tbody tr").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                    });
                });
            });
        </script>
    </head>
    <body>
        <div class="container mt-5">
            <h3 class="mb-4 text-center">Employee Management</h3>

            <!-- Search Bar -->
            <div class="form-group">
                <input type="text" id="searchInput" class="form-control" placeholder="Search for employees...">
            </div>

            <form id="frmRemoveEmployee" action="deleteall" method="post" onsubmit="return confirmDelete();">
                <table class="table table-bordered table-striped" id="employeeTable">
                    <thead class="thead-dark">
                        <tr>
                            <th>Select</th>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Gender</th>
                            <th>Date of Birth</th>
                            <th>Address</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.e}" var="e">
                            <tr>
                                <td>
                                    <input type="checkbox" name="eids" value="${e.id}">
                                </td>
                                <td>${e.id}</td>
                                <td>${e.name}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${e.gender}">
                                            Male
                                        </c:when>
                                        <c:otherwise>
                                            Female
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${e.dob}</td>
                                <td>${e.address}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <br/>
                <button type="submit" class="btn btn-danger">Delete Selected Employees</button>
            </form>
        </div>
    </body>
</html>
