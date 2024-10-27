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
            <div class="form-group">
                <label for="planSelect">Select Plan:</label>
                <select id="planSelect" name="planId" class="form-control">
                    <option value="">-- Select a Plan --</option>
                    <c:forEach var="plan" items="${plans}">
                        <option value="${plan.id}">${plan.name}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Phần để hiển thị biểu đồ tiến độ -->
            <div class="container my-4">
                <canvas id="progressChart" width="400" height="200"></canvas>
            </div>
        </div>

        <script>
            $('#planSelect').on('change', function () {
                var planId = $(this).val();

                if (planId) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/progress',
                        method: 'GET',
                        data: { planId: planId },
                        dataType: 'json',
                        success: function (data) {
                            updateChart(data);
                        },
                        error: function (xhr, status, error) {
                            console.error('Failed to fetch progress data:', status, error);
                        }
                    });
                } else {
                    updateChart(null);
                }
            });

            var ctx = document.getElementById('progressChart').getContext('2d');
            var progressChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: [],
                    datasets: [
                        {
                            label: 'Total Products',
                            data: [],
                            backgroundColor: 'blue',
                            fill: true
                        },
                        {
                            label: 'Completed Products',
                            data: [],
                            backgroundColor: 'green',
                            fill: true
                        },
                        {
                            label: 'Remaining Products',
                            data: [],
                            backgroundColor: 'red',
                            fill: true
                        }
                    ]
                },
                options: {
                    responsive: true,
                    scales: {
                        x: {
                            beginAtZero: true
                        },
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });

            function updateChart(data) {
                if (data && data.length > 0) {
                    var labels = [];
                    var totalProductsData = [];
                    var completedProductsData = [];
                    var remainingProductsData = [];

                    data.forEach(function (progress) {
                        labels.push(progress.planName + ' - ' + progress.progressDate);
                        totalProductsData.push(progress.totalProducts);
                        completedProductsData.push(progress.completedProducts);
                        remainingProductsData.push(progress.remainingProducts);
                    });

                    progressChart.data.labels = labels;
                    progressChart.data.datasets[0].data = totalProductsData;
                    progressChart.data.datasets[1].data = completedProductsData;
                    progressChart.data.datasets[2].data = remainingProductsData;
                } else {
                    progressChart.data.labels = [];
                    progressChart.data.datasets[0].data = [];
                    progressChart.data.datasets[1].data = [];
                    progressChart.data.datasets[2].data = [];
                }
                progressChart.update();
            }
        </script>
    </body>
</html>
