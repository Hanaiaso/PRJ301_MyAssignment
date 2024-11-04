<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New User</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <div class="container mt-5">
            <h3 class="mb-4 text-center">Create a New User</h3>

            <!-- Form để nhập thông tin người dùng mới -->
            <form id="createUserForm" class="needs-validation" novalidate>
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" class="form-control" required />
                    <div class="invalid-feedback">Please provide a username.</div>
                </div>

                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" class="form-control" required />
                    <div class="invalid-feedback">Please provide a password.</div>
                </div>

                <div class="form-group">
                    <label for="displayName">Display Name:</label>
                    <input type="text" id="displayName" name="displayname" class="form-control" required />
                    <div class="invalid-feedback">Please provide a display name.</div>
                </div>

                <div class="form-group">
                    <label for="roleSelect">Role:</label>
                    <select id="roleSelect" class="form-control" multiple required>
                        <c:forEach items="${roles}" var="r">
                            <option value="${r.id}">${r.name}</option>
                        </c:forEach>
                    </select>
                    <div class="invalid-feedback">Please select at least one role.</div>
                </div>

                <button type="button" id="createButton" class="btn btn-primary">Create</button>
            </form>
            <br/>
            <a href="javascript:history.back()" class="btn btn-secondary">Quay lại</a>
        </div>

        <script>
            $(document).ready(function () {
                // Bootstrap form validation
                (function () {
                    'use strict';
                    window.addEventListener('load', function () {
                        var forms = document.getElementsByClassName('needs-validation');
                        Array.prototype.filter.call(forms, function (form) {
                            form.addEventListener('submit', function (event) {
                                if (form.checkValidity() === false) {
                                    event.preventDefault();
                                    event.stopPropagation();
                                }
                                form.classList.add('was-validated');
                            }, false);
                        });
                    }, false);
                })();

                // Khi nhấn nút Create để thêm người dùng mới
                $("#createButton").on("click", function () {
                    if ($("#createUserForm")[0].checkValidity() === false) {
                        $("#createUserForm").addClass('was-validated');
                        return;
                    }

                    var selectedRoles = [];
                    $("#roleSelect option:selected").each(function () {
                        selectedRoles.push({id: $(this).val()});
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
                            alert('Failed to create user. Please try again.');
                        }
                    });
                });
            });
        </script>
    </body>
</html>
