<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Progress Details</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    </head>
    <body>
        <div class="container">
            <h1 class="my-4">Plan Progress Details for: ${plan.name}</h1>

            <c:forEach var="productProgress" items="${productProgresses}">
                <div class="my-4">
                    <h3>Product: <strong>${productProgress.product.name}</strong></h3>
                    <canvas id="productChart-${productProgress.product.id}" width="400" height="200"></canvas>
                </div>
            </c:forEach>
            <a href="javascript:history.back()">Quay lại</a>
        </div>
            
        <script>
            <c:forEach var="productProgress" items="${productProgresses}">
                var ctx = document.getElementById('productChart-${productProgress.product.id}').getContext('2d');
                var productChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: ['Total', 'Completed', 'Remaining'],
                        datasets: [{
                            label: 'Progress for ${productProgress.product.name}',
                            data: [${productProgress.totalProducts}, ${productProgress.completedProducts}, ${productProgress.remainingProducts}],
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
            </c:forEach>

        </script>
        
    </body>
</html>
