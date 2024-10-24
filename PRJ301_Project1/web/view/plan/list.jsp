<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách kế hoạch</title>
        <script>
            function removeEmployee(id) {
                var result = confirm("Are you sure?");
                if (result) {
                    document.getElementById("frmRemoveEmployee" + id).submit();
                }
            }
        </script>
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
                    <th>Sản phẩm</th>
                    <th>Số lượng</th>
                    <th>Ước tính</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty plans}">
                        <c:forEach var="plan" items="${plans}">
                            <c:if test="${not empty plan.campains}"> 
                                <c:forEach var="campaign" items="${plan.campains}"> 
                                    <tr>
                                        <td>${plan.id}</td>
                                        <td>${plan.name}</td>
                                        <td>${plan.start}</td>
                                        <td>${plan.end}</td>
                                        <td>${plan.dept.name}</td>
                                        <td>${campaign.product.name}</td>
                                        <td>${campaign.quantity}</td>
                                        <td>${campaign.cost}</td>
                                        <td>
                                            
                                            <a href="update?id=${plan.id}">Chỉnh sửa</a> |
                                            <a href="#" onclick="removeEmployee(${plan.id})">Xóa</a>
                                            <form id="frmRemoveEmployee${plan.id}" action="delete" method="POST" style="display:none;">
                                                <input type="hidden" name="id" value="${plan.id}"/>
                                            </form> | 
                                            <a href="../schedulecampain/list?plcid=${campaign.id}&plid=${plan.id}">Chi tiết</a>
                                        </td>
                                        <td>

                                            <a href="../schedulecampain/create?plid=${plan.id}&plcid=${campaign.id}">Thêm Kế Hoạch Chi tiết</a>
                                        </td>
                                     
                                    </tr>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty plan.campains}"> 
                                <tr>
                                    <td>${plan.id}</td>
                                    <td>${plan.name}</td>
                                    <td>${plan.start}</td>
                                    <td>${plan.end}</td>
                                    <td>${plan.dept.name}</td>
                                    <td colspan="5">Không có sản phẩm nào.</td>
                                    <td>
                                        <a href="update?id=${plan.id}">Chỉnh sửa</a>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                       
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="9">Không có kế hoạch nào.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
        <a href="../schedulecampain/list" class="view-schedule-button">Danh sách chi tiết các kế hoạch</a>
    </body>
</html>
