<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete User</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <div class="container mt-5">
            <h3 class="mb-4 text-center">Delete User</h3>

            <!-- Dropdown để chọn người dùng muốn xóa -->
            <div class="form-group">
                <label for="userSelect">Chọn người dùng bạn muốn xóa:</label>
                <select id="userSelect" class="form-control">
                    <option value="">-- Select a User --</option>
                    <c:forEach items="${userList}" var="u">  
                        <option value="${u.username}">${u.displayname} (${u.username})</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Form xác nhận xóa người dùng (ban đầu ẩn) -->
            <div id="deleteForm" class="border p-4" style="display:none;">
                <form id="deleteUserForm" method="POST">
                    <input type="hidden" id="username" name="username" readonly />
                    <p>Bạn có chắc chắn muốn xóa người dùng: <strong id="displayUser"></strong> không?</p>
                    <button type="button" id="deleteButton" class="btn btn-danger">Delete</button>
                    <button type="button" id="cancelButton" class="btn btn-secondary ml-2">Cancel</button>
                </form>
            </div>
            <br/>
            <a href="javascript:history.back()" class="btn btn-secondary mt-3">Quay lại</a>
        </div>

        <script>
            $(document).ready(function () {
                // Khi chọn người dùng trong dropdown
                $("#userSelect").on("change", function () {
                    var username = $(this).val();
                    if (username) {
                        // Gửi AJAX để lấy thông tin người dùng
                        $.ajax({
                            url: 'delete',
                            method: 'GET',
                            data: {username: username},
                            dataType: 'json',
                            success: function (data) {
                                // Hiển thị form xác nhận và điền thông tin vào
                                $("#deleteForm").show();
                                $("#username").val(data.username);
                                $("#displayUser").text(data.displayname);
                            },
                            error: function (xhr, status, error) {
                                console.error('Failed to fetch user:', status, error);
                            }
                        });
                    } else {
                        $("#deleteForm").hide();
                    }
                });

                // Khi nhấn nút Delete để xóa người dùng
                $("#deleteButton").on("click", function () {
                    var userData = {
                        username: $("#username").val()
                    };

                    // Xác nhận xóa trước khi thực hiện
                    if (confirm("Bạn có chắc chắn muốn xóa người dùng này?")) {
                        // Gửi AJAX để xóa thông tin người dùng (cập nhật isWork thành 0)
                        $.ajax({
                            url: 'delete',
                            method: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify(userData),
                            success: function () {
                                alert('User deleted successfully!');
                                location.reload(); // Reload trang để cập nhật danh sách người dùng
                            },
                            error: function (xhr, status, error) {
                                console.error('Failed to delete user:', status, error);
                            }
                        });
                    }
                });

                // Khi nhấn nút Cancel để hủy
                $("#cancelButton").on("click", function () {
                    $("#deleteForm").hide();
                });
            });
        </script>
    </body>
</html>
