<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cập Nhật Sản Phẩm</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <div class="container mt-5">
            <h3 class="mb-4">Cập Nhật Sản Phẩm</h3>
            
           
            <!-- Dropdown để chọn sản phẩm muốn cập nhật -->
            <div class="form-group">
                <label>Chọn sản phẩm bạn muốn cập nhật:</label>
                <select id="productSelect" class="form-control">
                    <option value="">-- Chọn sản phẩm --</option>
                    <c:forEach items="${requestScope.plist}" var="pl">  
                        <option value="${pl.id}">${pl.name}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Form cập nhật sản phẩm (ban đầu ẩn) -->
            <div id="updateForm" style="display:none; margin-top: 20px;">
                <form id="updateProductForm" method="POST">
                    <div class="form-group">
                        <label for="productId">ID Sản Phẩm:</label>
                        <input type="text" id="productId" name="pid" class="form-control" readonly />
                    </div>
                    <div class="form-group">
                        <label for="productName">Tên Sản Phẩm:</label>
                        <input type="text" id="productName" name="pname" class="form-control" required />
                    </div>
                    <button type="button" id="updateButton" class="btn btn-primary">Cập Nhật</button>
                </form>
            </div>
            
            <br/>
            <a href="javascript:history.back()" class="btn btn-secondary mt-3">Quay lại</a>
        </div>

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
                            url: 'update',
                            method: 'GET',
                            data: { pid: productId },
                            dataType: 'json',
                            success: function (data) {
                                // Hiển thị form và điền thông tin vào
                                $("#updateForm").show();
                                $("#productId").val(data.id);
                                $("#productName").val(data.name);
                            },
                            error: function (xhr, status, error) {
                                console.error('Failed to fetch product:', status, error);
                            }
                        });
                    } else {
                        $("#updateForm").hide();
                    }
                });

                // Khi nhấn nút Update để cập nhật sản phẩm
                $("#updateButton").on("click", function () {
                    var productId = $("#productId").val();
                    var productName = $("#productName").val();

                    // Gửi AJAX để cập nhật thông tin sản phẩm
                    $.ajax({
                        url: 'update',
                        method: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify({ id: productId, name: productName }),
                        success: function () {
                            alert('Sản phẩm đã được cập nhật thành công!');
                            location.reload(); // Reload trang để cập nhật danh sách sản phẩm
                        },
                        error: function (xhr, status, error) {
                            console.error('Failed to update product:', status, error);
                        }
                    });
                });
            });
        </script>
    </body>
</html>
