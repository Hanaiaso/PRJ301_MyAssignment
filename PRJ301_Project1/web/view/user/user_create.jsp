<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create New User</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <h3>Create a New User</h3>

    <!-- Form để nhập thông tin người dùng mới -->
    <form id="createUserForm">
        <label>Username:</label>
        <input type="text" id="username" name="username" required /><br/>

        <label>Password:</label>
        <input type="password" id="password" name="password" required /><br/>

        <label>Display Name:</label>
        <input type="text" id="displayName" name="displayname" required /><br/>

        <label>Role:</label>
        <select id="roleSelect" multiple>
            <c:forEach items="${roles}" var="r">
                <option value="${r.id}">${r.name}</option>
            </c:forEach>
        </select><br/><br/>

        <button type="button" id="createButton">Create</button>
    </form>
    <br/>
    <a href="javascript:history.back()">Quay lại</a>

    <script>
        $(document).ready(function () {
            // Khi nhấn nút Create để thêm người dùng mới
            $("#createButton").on("click", function () {
                var selectedRoles = [];
                $("#roleSelect option:selected").each(function () {
                    selectedRoles.push({ id: $(this).val() });
                });

                var userData = {
                    username: $("#username").val(),
                    password: $("#password").val(),
                    displayname: $("#displayName").val(),
                    roles: selectedRoles
                };

                // Gửi AJAX để thêm thông tin người dùng
                $.ajax({
                    url: 'create',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(userData),
                    success: function () {
                        alert('User created successfully!');
                        location.reload(); // Reload trang để cập nhật danh sách người dùng
                    },
                    error: function (xhr, status, error) {
                        console.error('Failed to create user:', status, error);
                    }
                });
            });
        });
    </script>
</body>
</html>
