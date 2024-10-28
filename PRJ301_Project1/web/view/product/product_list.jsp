<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <style>
            #createForm {
                display: none; /* Ẩn form lúc đầu */
                margin-top: 20px;
                padding: 15px;
                border: 1px solid #ccc;
                background-color: #f9f9f9;
            }
            #searchBar {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <h1>Product List</h1>

        <a href="javascript:void(0)" id="createProductButton">Create product</a>

        <!-- Form để tạo sản phẩm mới -->
        <div id="createForm">
            <h3>Create a new product</h3>
            <form action="create" method="POST">
                Hãy điền tên sản phẩm mà bạn muốn thêm vào: 
                <input type="text" name="pname" value="" />
                <input type="submit" value="Save" />
            </form>
            <button id="closeFormButton">Close</button>
        </div>

        <a href="update">Update product</a>
        <a href="delete">Delete product</a>

        <!-- Thanh tìm kiếm sản phẩm -->
        <div id="searchBar">
            <label for="searchInput">Search Product:</label>
            <input type="text" id="searchInput" placeholder="Enter product name...">
        </div>

        <!-- Bảng hiển thị danh sách sản phẩm -->
        <table border="1" id="productTable" style="margin-top: 20px;">
            <thead>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.pro}" var="p">
                    <tr>
                        <td class="productId">${p.id}</td>
                        <td class="productName">${p.name}</td>
                    </tr>
                </c:forEach>   
            </tbody>
        </table>

        <script>
            $(document).ready(function () {
                // Khi người dùng nhấn vào "Create product"
                $("#createProductButton").on("click", function () {
                    $("#createForm").show(); // Hiển thị form
                });

                // Khi người dùng nhấn vào nút "Close"
                $("#closeFormButton").on("click", function () {
                    $("#createForm").hide(); // Ẩn form
                });

                // Khi người dùng nhập từ khóa vào thanh tìm kiếm
                $("#searchInput").on("keyup", function () {
                    var value = $(this).val().toLowerCase(); // Lấy giá trị tìm kiếm và chuyển sang chữ thường
                    $("#productTable tbody tr").filter(function () {
                        // Lọc các dòng sản phẩm dựa vào giá trị tìm kiếm
                        $(this).toggle($(this).find(".productName").text().toLowerCase().indexOf(value) > -1);
                    });
                });
            });
        </script>
    </body>
</html>
