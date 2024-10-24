<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TRE COMPANY</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f8ff;
                margin: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .container {
                background-color: #ffffff;
                padding: 30px;
                border-radius: 15px;
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
                text-align: center;
                width: 400px;
                position: relative;
            }
            .header {
                margin-bottom: 30px;
            }
            .footer {
                margin-top: 20px;
                font-size: 0.9em;
                color: #888;
            }
            img.logo {
                max-width: 80px;
                height: auto;
                border-radius: 50px;
            }

            #showFormBtn {
                background-color: #007BFF;
                color: white;
                border: none;
                border-radius: 5px;
                padding: 12px 30px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s, transform 0.3s;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
            }
            #showFormBtn:hover {
                background-color: #0056b3;
                transform: translate(-50%, -50%) scale(1.05);
            }
            form {
                margin-top: 20px;
                opacity: 0;
                visibility: hidden;
                transition: opacity 0.5s, visibility 0.5s;
            }
            input[type="text"], input[type="password"] {
                width: 100%;
                padding: 12px;
                margin: 8px 0;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
                font-size: 14px;
            }
            input[type="submit"], button.cancelBtn {
                background-color: #28a745;
                color: white;
                border: none;
                border-radius: 5px;
                padding: 12px 20px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s, transform 0.3s;
                width: 100%;
                margin-top: 10px;
            }
            input[type="submit"]:hover, button.cancelBtn:hover {
                background-color: #218838;
                transform: scale(1.05);
            }
            button.cancelBtn {
                background-color: #dc3545;
                width: 100%;
            }
            button.cancelBtn:hover {
                background-color: #c82333;
            }
            .visible {
                opacity: 1;
                visibility: visible;
            }
            .error-message {
                color: red;
                font-size: 14px;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <img src="img/images.png" alt="Logo" class="logo">           
            </div>
            <button id="showFormBtn">LOGIN</button>
            <form id="loginForm" action="login" method="POST">
                <input type="text" name="username" placeholder="Username" required /><br/>
                <input type="password" name="password" placeholder="Password" required /><br/>
                <input type="submit" value="Login" />
                <button type="button" class="cancelBtn">Cancel</button>
            </form>
            <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
            %>
            <div style="color: red;">
                <strong><%= errorMessage %></strong>
            </div>
            <%
                }
            %>
            <div class="footer">
                <p>Contact: info@trecompany.com</p> 
                <p>Phone: 0986429391</p>
            </div>
        </div>
        <script>
            const showFormBtn = document.getElementById('showFormBtn');
            const loginForm = document.getElementById('loginForm');
            const cancelBtn = document.querySelector('.cancelBtn');
            showFormBtn.addEventListener('click', function () {
                loginForm.classList.add('visible'); 
                showFormBtn.style.display = 'none'; 
            });
            cancelBtn.addEventListener('click', function () {
                loginForm.classList.remove('visible'); 
                showFormBtn.style.display = 'block'; 
                document.querySelector('input[name="username"]').value = '';
                document.querySelector('input[name="password"]').value = '';
            });
        </script>
    </body>
</html>
