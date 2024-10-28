<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <h1 class="my-4">User List</h1>

            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Password</th>
                        <th>Display Name</th>
                        <th>Role</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${user}" var="u">
                        <tr>
                            <td>${u.username}</td>
                            <td>***</td> <!-- Hiển thị mật khẩu bị ẩn -->
                            <td>${u.displayname}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${u.roles == null}">
                                        Not added yet
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${u.roles}" var="r">
                                            ${r.name}<br/>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>   
                </tbody>
            </table>
        </div>
    </body>
</html>
