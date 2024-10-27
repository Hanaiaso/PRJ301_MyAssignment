<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Attendance List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <h1 class="my-4">Attendance List</h1>
            <table class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th>Plan Name</th>
                        <th>Employee Name</th>
                        <th>Product Name</th>
                        <th>Date</th>
                        <th>Shift</th>
                        <th>Assigned Quantity</th>
                        <th>Completed Quantity</th>
                        <th>Alpha</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="attendance" items="${attendances}">
                        <tr>
                            <td>${attendance.scheduleEmployee.schedulecampain.plancampain.plan.name}</td>
                            <td>${attendance.scheduleEmployee.employee.name}</td>
                            <td>${attendance.scheduleEmployee.schedulecampain.plancampain.product.name}</td>
                            <td>${attendance.scheduleEmployee.schedulecampain.date}</td>
                            <td>${attendance.scheduleEmployee.schedulecampain.shift}</td>
                            <td>${attendance.scheduleEmployee.quantity}</td>
                            <td>${attendance.quantity}</td>
                            <td>${attendance.alpha}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
