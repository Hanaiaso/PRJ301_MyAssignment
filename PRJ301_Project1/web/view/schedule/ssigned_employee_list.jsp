<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Nhân viên Đã Được Phân Công</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>Danh sách Nhân viên Đã Được Phân Công</h1>

    <c:if test="${empty assignedEmployees}">
        <p>Không có nhân viên nào được phân công.</p>
    </c:if>

    <c:if test="${not empty assignedEmployees}">
        <table>
            <thead>
                <tr>
                    <th>Plan Name</th>
                    <th>PlanCampain ID</th>
                    <th>Department Name</th>
                    <th>Date</th>
                    <th>Shift</th>
                    <th>Employee ID</th>
                    <th>Employee Name</th>
                    <th>Số Lượng Được Phân Công</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="assignment" items="${assignedEmployees}">
                    <c:forEach var="employee" items="${assignment.employee}">
                        <tr>
                            <td>${assignment.planName}</td>
                            <td>${assignment.planCampainId}</td>
                            <td>${assignment.departmentName}</td>
                            <td>${assignment.date}</td>
                            <td>${assignment.shift}</td>
                            <td>${employee.id}</td>
                            <td>${employee.name}</td>
                            <td>${assignment.quantity}</td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <br/>
    <a href="javascript:history.back()">Quay lại</a>
</body>
</html>
