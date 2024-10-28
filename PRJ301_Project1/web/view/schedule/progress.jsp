<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Progress Tracking</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <div class="container">
            <h1 class="my-4">Plan Progress Tracker</h1>

            <!-- Dropdown để chọn kế hoạch -->
            <form method="get" action="progress">
                <div class="form-group">
                    <label for="planSelect">Select Plan:</label>
                    <select id="planSelect" name="planId" class="form-control" onchange="this.form.submit()">
                        <option value="">-- Select a Plan --</option>
                        <c:forEach var="plan" items="${plans}">
                            <option value="${plan.id}" <c:if test="${param.planId == plan.id}">selected</c:if>>${plan.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </form>

            <!-- Phần để hiển thị biểu đồ tiến độ nếu có dữ liệu -->
            <c:if test="${not empty progress}">
                <div class="my-4">
                    <h3>Progress Details for: <strong>${progress.plan.name}</strong></h3>
                    <p><strong>Department:</strong> ${progress.department.name}</p>
                    <p id="status" style="font-weight: bold;">
                        Status: <span id="statusText">${progress.status}</span>
                    </p>
                    <p><strong>Start Date:</strong> ${progress.plan.start}</p>
                    <p><strong>End Date:</strong> ${progress.plan.end}</p>
                    <p><strong>Current Date:</strong> ${currentDate}</p>
                    <button class="btn btn-primary" onclick="location.href = 'progressdetails?planId=${progress.plan.id}'">View Details</button>
                </div>

                <div class="container my-4">
                    <canvas id="progressChart" width="400" height="200"></canvas>
                </div>

                <script>
                    $(document).ready(function () {
                        // Cập nhật màu của trạng thái dựa trên giá trị của progress.status
                        var status = "${progress.status}";
                        var statusElement = $('#statusText');

                        if (status === 'Muộn') {
                            statusElement.css('color', 'red');
                        } else if (status === 'Đang tiến hành') {
                            statusElement.css('color', 'blue');
                        } else if (status === 'Hoàn thành') {
                            statusElement.css('color', 'green');
                        }

                        // Hiển thị biểu đồ tiến độ
                        var ctx = document.getElementById('progressChart').getContext('2d');
                        var progressChart = new Chart(ctx, {
                            type: 'bar',
                            data: {
                                labels: ['Total Products', 'Completed Products', 'Remaining Products'],
                                datasets: [{
                                        label: 'Progress',
                                        data: [${progress.totalProducts}, ${progress.completedProducts}, ${progress.remainingProducts}],
                                        backgroundColor: ['blue', 'green', 'red'],
                                        fill: true
                                    }]
                            },
                            options: {
                                responsive: true,
                                scales: {
                                    y: {
                                        beginAtZero: true
                                    }
                                }
                            }
                        });
                    });
                </script>
            </c:if>
                <a href="javascript:history.back()">Quay lại</a>
        </div>
    </body>
</html>
