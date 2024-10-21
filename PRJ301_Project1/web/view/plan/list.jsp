<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách kế hoạch</title>
</head>
<body>
    <h1>Danh sách các kế hoạch</h1>

    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Tên kế hoạch</th>
                <th>Ngày bắt đầu</th>
                <th>Ngày kết thúc</th>
                <th>Phòng ban</th>
                <th>Sản phẩm</th> <!-- Column for Products -->
                <th>Số lượng</th> <!-- Column for Quantity -->
                <th>Ước tính</th> <!-- Column for Estimate -->
                <th>Thao tác</th> <!-- New column for actions -->
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty plans}">
                    <c:forEach var="plan" items="${plans}">
                        <c:if test="${not empty plan.campains}"> <!-- Check if the plan has campaigns -->
                            <c:forEach var="campaign" items="${plan.campains}"> <!-- Loop through campaigns -->
                                <tr>
                                    <td>${plan.id}</td>
                                    <td>${plan.name}</td>
                                    <td>${plan.start}</td>
                                    <td>${plan.end}</td>
                                    <td>${plan.dept.name}</td> <!-- Use department name -->
                                    <td>${campaign.product.name}</td> <!-- Display product name -->
                                    <td>${campaign.quantity}</td> <!-- Display quantity -->
                                    <td>${campaign.cost}</td> <!-- Display estimate cost -->
                                    <td>
                                        <a href="update?id=${plan.id}">Chỉnh sửa</a> <!-- Link to the update controller -->
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty plan.campains}"> <!-- If no campaigns, display plan info -->
                            <tr>
                                <td>${plan.id}</td>
                                <td>${plan.name}</td>
                                <td>${plan.start}</td>
                                <td>${plan.end}</td>
                                <td>${plan.dept.name}</td> <!-- Use department name -->
                                <td colspan="5">Không có sản phẩm nào.</td> <!-- Indicate no campaigns -->
                                <td>
                                    <a href="update?id=${plan.id}">Chỉnh sửa</a> <!-- Link to the update controller -->
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="9">Không có kế hoạch nào.</td> <!-- Adjusted colspan to include new columns -->
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>
