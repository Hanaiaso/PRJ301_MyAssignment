<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Lịch đã được tạo</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }
            .message {
                color: red;
                font-size: 18px;
                margin-bottom: 20px;
            }
            .back-button {
                background-color: #4CAF50;
                color: white;
                border: none;
                padding: 10px 20px;
                font-size: 16px;
                cursor: pointer;
            }
            .back-button:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <div class="message">
            <strong>${message}</strong>
        </div>

        <!-- Nút quay lại -->
        <form action="javascript:history.back()">
            <button class="back-button">Quay lại</button>
        </form>
    </body>
</html>
