<%-- 
    Document   : delete
    Created on : Oct 19, 2024, 9:02:14 PM
    Author     : LEGION
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="deleteall" method="post">
            <table border="1">
                <thead>
                    <tr>
                        <td>Id</td>
                        <td>Name</td>
                        <td>Gender</td>
                        <td>Dob</td>
                        <td>Address</td>

                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.emps}" var="e">
                        <tr>
                            <td>
                                <input type="checkbox" name="employeeIds" value="${e.id}">
                            </td>
                            <td>${e.id}</td>
                            <td>${e.name}</td>
                            <td>
                                <c:if test="${e.gender}">
                                    Male
                                </c:if>
                                <c:if test="${!e.gender}">
                                    Female
                                </c:if>

                            </td>
                            <td>${e.dob}</td>
                            <td>${e.address}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <br>
            <input type="submit" value="Delete Selected Employees">
        </form>

    </body>
</html>
