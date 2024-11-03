<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Xóa Sản Phẩm</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            $(document).ready(function () {
                // Tìm kiếm sản phẩm trong dropdown
                $("#searchInput").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $("#productSelect option").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                    });
                });

                // Khi chọn sản phẩm trong dropdown
                $("#productSelect").on("change", function () {
                    var productId = $(this).val();
                    if (productId) {
                        // Gửi AJAX để lấy thông tin sản phẩm
                        $.ajax({
                            url: 'delete',
                            method: 'GET',
                            data: { pid: productId },
                            dataType: 'json',
                            success: function (data) {
                                // Hiển thị form và điền thông tin vào
                                $("#deleteForm").show();
                                $("#productId").text(data.id);
                                $("#productName").text(data.name);
                            },
                            error: function (xhr, status, error) {
                                console.error('Failed to fetch product:', status, error);
                            }
                        });
                    } else {
                        $("#deleteForm").hide();
                    }
                });

                // Khi nhấn nút Delete để xóa sản phẩm
                $("#deleteButton").on("click", function () {
                    var productId = $("#productId").text();

                    // Hiển thị thông báo xác nhận
                    var confirmDelete = confirm("Bạn có chắc chắn muốn xóa sản phẩm này không?");
                    if (confirmDelete) {
                        // Gửi AJAX để cập nhật thông tin sản phẩm
                        $.ajax({
                            url: 'delete',
                            method: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify({ id: productId }),
                            success: function () {
                                alert('Sản phẩm đã được xóa thành công!');
                                location.reload(); // Reload trang để cập nhật danh sách sản phẩm
                            },
                            error: function (xhr, status, error) {
                                console.error('Failed to delete product:', status, error);
                            }
                        });
                    }
                });
            });
        </script>
    </head>
    <body>
        <div class="container mt-5">
            <h3 class="mb-4">Xóa sản phẩm</h3>

            

            <!-- Dropdown để chọn sản phẩm muốn xóa -->
            <div class="form-group">
                <label>Chọn sản phẩm bạn muốn xóa:</label>
                <select id="productSelect" class="form-control">
                    <option value="">-- Chọn sản phẩm --</option>
                    <c:forEach items="${requestScope.plist}" var="pl">  
                        <option value="${pl.id}">${pl.name}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Form để hiển thị thông tin sản phẩm (ban đầu ẩn) -->
            <div id="deleteForm" style="display:none; margin-top: 20px;">
                <p>ID sản phẩm: <span id="productId"></span></p>
                <p>Tên sản phẩm: <span id="productName"></span></p>
                <button type="button" id="deleteButton" class="btn btn-danger">Xóa sản phẩm</button>
            </div>
            <br/>
            <a href="javascript:history.back()" class="btn btn-secondary mt-3">Quay lại</a>
        </div>
    </body>
</html>
