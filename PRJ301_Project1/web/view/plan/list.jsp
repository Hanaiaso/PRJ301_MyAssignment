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
                <th>Thao tác</th> <!-- New column for actions -->
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty plans}">
                    <c:forEach var="plan" items="${plans}">
                        <tr>
                            <td>${plan.id}</td>
                            <td>${plan.name}</td>
                            <td>${plan.start}</td>
                            <td>${plan.end}</td>
                            <td>${plan.dept.id}</td>
                            <td>
                                <a href="update?id=${plan.id}">Chỉnh sửa</a> <!-- Link to the update controller -->
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="6">Không có kế hoạch nào.</td> <!-- Adjusted colspan to include new column -->
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>
