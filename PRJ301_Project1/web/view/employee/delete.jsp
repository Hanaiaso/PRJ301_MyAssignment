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
        <form action="delete" method="POST">
        <h2>Select Employees to Delete</h2>

        <table border="1">
            <thead>
                <tr>
                    <th>Select</th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Address</th>
                    <th>Date of Birth</th>
                    <th>Salary</th>
                    <th>Department ID</th>
                </tr>
            </thead>
            <tbody>
                
            <c:forEach items="${requestScope.e}" var="emp">
                <tr>
                    <td><input type="checkbox" name="employeeIds" value="${requestScope.e.id}"></td>
                    <td>${requestScope.e.id}</td>
                    
                </tr>
            </c:forEach>>
            </tbody>
        </table>

        <input type="submit" value="Delete Selected Employees">
    </form>
    </body>
</html>
