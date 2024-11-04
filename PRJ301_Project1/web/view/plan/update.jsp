<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cập nhật kế hoạch</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
                padding-top: 20px;
            }
            .form-container {
                max-width: 800px;
                margin: 0 auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
        </style>
    </head>
    <body>

        <div class="container">
            <div class="form-container">
                <h1 class="text-center mb-4">Cập nhật kế hoạch</h1>
                <form action="update" method="post">
                    <input type="hidden" name="plid" value="${plan.id}" />

                    <div class="form-group">
                        <label for="plname">Tên kế hoạch:</label>
                        <input type="text" id="plname" name="plname" value="${plan.name}" class="form-control" required>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="startDate">Ngày bắt đầu:</label>
                            <input type="date" id="startDate" name="startDate" value="${fn:substring(plan.start, 0, 10)}" class="form-control" required>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="endDate">Ngày kết thúc:</label>
                            <input type="date" id="endDate" name="endDate" value="${fn:substring(plan.end, 0, 10)}" class="form-control" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="department">Phòng ban:</label>
                        <select name="did" id="department" class="form-control" required>
                            <c:forEach var="dept" items="${departments}">
                                <option value="${dept.id}" ${dept.id == plan.dept.id ? 'selected' : ''}>${dept.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <h3 class="mt-4">Chi tiết chiến dịch</h3>
                    <c:forEach var="campaign" items="${requestScope.campains}">
                        <div class="campaign p-3 mb-3 border rounded">
                            <input type="hidden" name="plcid" value="${campaign.id}" />

                            <div class="form-group">
                                <label for="product${campaign.id}">Sản phẩm:</label>
                                <input type="text" id="product${campaign.id}" name="product${campaign.id}" value="${campaign.product.name}" class="form-control" readonly>
                            </div>

                            <div class="form-group">
                                <label for="quantity${campaign.id}">Số lượng:</label>
                                <input type="number" id="quantity${campaign.id}" name="quantity${campaign.id}" value="${campaign.quantity}" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="estimate${campaign.id}">Ước tính:</label>
                                <input type="number" id="estimate${campaign.id}" name="estimate${campaign.id}" value="${campaign.cost}" class="form-control" step="0.01" required>
                            </div>
                        </div>
                    </c:forEach>

                    <button type="submit" class="btn btn-primary btn-block">Cập nhật</button>
                </form>
            </div>
        </div>

    </body>
</html>
