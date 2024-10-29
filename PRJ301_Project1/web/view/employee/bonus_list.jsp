<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Bonus List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            function onPlanSelect() {
                document.getElementById("planForm").submit();
            }
        </script>
    </head>
    <body>
        <div class="container">
            <h1 class="my-4">Employee Bonus List</h1>

            <!-- Plan selection form -->
            <form id="planForm" action="bonus" method="GET">
                <div class="form-group">
                    <label for="planSelect">Select a Plan:</label>
                    <select name="planId" id="planSelect" class="form-control" required onchange="onPlanSelect()">
                        <option value="">-- Select a Plan --</option>
                        <c:forEach items="${plans}" var="plan">
                            <option value="${plan.id}" <c:if test="${selectedPlanId == plan.id}">selected</c:if>>${plan.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </form>

            <!-- Employee bonuses table -->
            <c:if test="${not empty employees}">
                <table class="table table-bordered table-striped" style="margin-top: 20px;">
                    <thead>
                        <tr>
                            <th>Employee ID</th>
                            <th>Employee Name</th>
                            <th>Additional Bonus (VND)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${employees}" var="employee">
                            <tr>
                                <td>${employee.id}</td>
                                <td>${employee.name}</td>
                                <td>${employee.additionalBonus}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty employees && not empty selectedPlanId}">
                <div class="alert alert-info" style="margin-top: 20px;">No employees found for the selected plan.</div>
            </c:if>
        </div>
    </body>
</html>
