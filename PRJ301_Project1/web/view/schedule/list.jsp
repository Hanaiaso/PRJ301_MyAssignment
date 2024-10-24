<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách các kế hoạch đã tạo</title>
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
        <h1>Danh sách các kế hoạch đã tạo</h1>
        <c:if test="${empty schedules}">
            <p>Không có kế hoạch nào đã được tạo.</p>
        </c:if>
        <c:if test="${not empty schedules}">
            <table>
                <thead>
                    <tr>
                        <th>Plan ID</th>
                        <th>Plan Name</th>
                        <th>PlanCampain ID</th>
                        <th>Product Name</th>
                        <th>Date</th>
                        <th>Shift</th>
                        <th>Quantity</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="schedule" items="${schedules}">
                        <tr>
                            <td>${schedule.plancampain.plan.id}</td>
                            <td>${schedule.plancampain.plan.name}</td>
                            <td>${schedule.plancampain.id}</td>
                            <td>${schedule.plancampain.product.name}</td>
                            <td>${schedule.date}</td>
                            <td>${schedule.shift}</td>
                            <td>${schedule.quantity}</td>
                               <td>
                                    <!-- Link đến chức năng phân công nhân viên cho kế hoạch -->
                                    <a href="../scheduleemployee/create?plcid=${schedule.plancampain.id}">Phân công nhân viên</a>
                                </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <br/>
        
        <a href="javascript:history.back()">Quay lại</a>
    </body>
</html>
