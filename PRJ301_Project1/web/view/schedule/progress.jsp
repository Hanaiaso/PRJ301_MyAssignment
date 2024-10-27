<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Progress Tracking</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
            <c:if test="${not empty progressdate}">
                <h3>Progress Details for: ${progress.plan.name}</h3>
                <p>Status: <strong>${progress.status}</strong></p>
                <p>Start Date: <strong>${progressdate.start}</strong></p>
                <p>End Date: <strong>${progressdate.end}</strong></p>
                <p>Current Date: <strong>${currentDate}</strong></p>

                <div class="container my-4">
                    <canvas id="progressChart" width="400" height="200"></canvas>
                </div>

                <script>
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
                </script>
            </c:if>
        </div>
    </body>
</html>
