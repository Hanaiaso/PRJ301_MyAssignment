<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cập nhật kế hoạch</title>
    </head>
    <body>
        <h1>Cập nhật kế hoạch</h1>
        <form action="update" method="post">
            <input type="hidden" name="plid" value="${plan.id}" />

            <label for="plname">Tên kế hoạch:</label>
            <input type="text" id="plname" name="plname" value="${plan.name}" required /><br>

            <label for="startDate">Ngày bắt đầu:</label>
            <input type="date" id="startDate" name="startDate" value="${fn:substring(plan.start, 0, 10)}" required /><br>

            <label for="endDate">Ngày kết thúc:</label>
            <input type="date" id="endDate" name="endDate" value="${fn:substring(plan.end, 0, 10)}" required /><br>

            <label for="department">Phòng ban:</label>
            <select name="did" id="department" required>
                <c:forEach var="dept" items="${departments}">
                    <option value="${dept.id}" ${dept.id == plan.dept.id ? 'selected' : ''}>${dept.name}</option>
                </c:forEach>
            </select><br>

            <h3>Chi tiết chiến dịch</h3>
            <c:forEach var="campaign" items="${requestScope.campains}">
                <div class="campaign">
                    <input type="hidden" name="plcid" value="${campaign.id}" />

                    <label for="product${campaign.id}">Sản phẩm:</label>
                    <input type="text" id="product${campaign.id}" name="product${campaign.id}" value="${campaign.product.name}" readonly="" /><br>

                    <label for="quantity${campaign.id}">Số lượng:</label>
                    <input type="number" id="quantity${campaign.id}" name="quantity${campaign.id}" value="${campaign.quantity}" required /><br>

                    <label for="estimate${campaign.id}">Ước tính:</label>
                    <input type="text" id="estimate${campaign.id}" name="estimate${campaign.id}" value="${campaign.cost}" required /><br><br>
                </div>
            </c:forEach>

            <input type="submit" value="Cập nhật" />
        </form>

    </body>
</html>
