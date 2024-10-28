<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update User</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <h3>Update User Information</h3>

        <!-- Dropdown để chọn user muốn cập nhật -->
        <label>Chọn người dùng bạn muốn cập nhật:</label>
        <select id="userSelect">
            <option value="">-- Select a User --</option>
            <c:forEach items="${userList}" var="u">  
                <option value="${u.username}">${u.displayname} (${u.username})</option>
            </c:forEach>
        </select>

        <!-- Form cập nhật người dùng (ban đầu ẩn) -->
        <div id="updateForm" style="display:none; margin-top: 20px;">
            <form id="updateUserForm" method="POST">
                <input type="text" id="username" name="username" readonly />
                <label>Tên hiển thị:</label>
                <input type="text" id="displayName" name="displayname" />
                <label>Mật khẩu:</label>
                <input type="password" id="password" name="password" />
                <label>Vai trò:</label>
                <select id="roleSelect" name="roles">
                    <option value="">-- Select Role --</option>
                    <c:forEach items="${roles}" var="r">  
                        <option value="${r.id}">${r.name}</option>
                    </c:forEach>
                </select>
                <button type="button" id="updateButton">Save</button>
            </form>
        </div>
        <br/>
        <a href="javascript:history.back()">Quay lại</a>

        <script>
            $(document).ready(function () {
                // Khi chọn người dùng trong dropdown
                $("#userSelect").on("change", function () {
                    var username = $(this).val();
                    if (username) {
                        // Gửi AJAX để lấy thông tin người dùng
                        $.ajax({
                            url: 'update',
                            method: 'GET',
                            data: {username: username},
                            dataType: 'json',
                            success: function (data) {
                                // Hiển thị form và điền thông tin vào
                                $("#updateForm").show();
                                $("#username").val(data.username);
                                $("#displayName").val(data.displayname);
                                // Để tránh vấn đề bảo mật, mật khẩu không hiển thị trực tiếp
                                $("#password").val("");

                                // Kiểm tra vai trò và gán giá trị
                                if (data.roles && data.roles.length > 0) {
                                    $("#roleSelect").val(data.roles[0].id);
                                } else {
                                    $("#roleSelect").val("");
                                }
                            },
                            error: function (xhr, status, error) {
                                console.error('Failed to fetch user:', status, error);
                            }
                        });
                    } else {
                        $("#updateForm").hide();
                    }
                });

                // Khi nhấn nút Update để cập nhật người dùng
                $("#updateButton").on("click", function () {
                    var userData = {
                        username: $("#username").val(),
                        displayname: $("#displayName").val(),
                        password: $("#password").val(),
                        roles: ($("#roleSelect").val()) ? [{id: $("#roleSelect").val()}] : []
                    };

                    // Gửi AJAX để cập nhật thông tin người dùng
                    $.ajax({
                        url: 'update',
                        method: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify(userData),
                        success: function () {
                            alert('User updated successfully!');
                            location.reload(); // Reload trang để cập nhật danh sách người dùng
                        },
                        error: function (xhr, status, error) {
                            console.error('Failed to update user:', status, error);
                        }
                    });
                });
            });
        </script>
    </body>
</html>
