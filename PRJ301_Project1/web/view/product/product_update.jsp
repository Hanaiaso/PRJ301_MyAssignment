<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <h3>Update a product</h3>
        
        <!-- Dropdown để chọn sản phẩm muốn cập nhật -->
        <label>Chọn sản phẩm bạn muốn cập nhập:</label>
        <select id="productSelect">
            <option value="">-- Select a Product --</option>
            <c:forEach items="${requestScope.plist}" var="pl">  
                <option value="${pl.id}">${pl.name}</option>
            </c:forEach>
        </select>

        <!-- Form cập nhật sản phẩm (ban đầu ẩn) -->
        <div id="updateForm" style="display:none; margin-top: 20px;">
            <form id="updateProductForm" method="POST">
                <input type="text" id="productId" name="pid" value="" readonly="" />
                <label>Tên sản phẩm:</label>
                <input type="text" id="productName" name="pname" value="" />
                <button type="button" id="updateButton">Update</button>
            </form>
        </div>
         <br/>
    <a href="javascript:history.back()">Quay lại</a>
        <script>
            $(document).ready(function () {
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
                            alert('Product updated successfully!');
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
