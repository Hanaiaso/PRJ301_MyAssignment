<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table border="1px">
            <tr>
                <td>Id</td>
                <td>Name</td>
                <td>Gender</td>
                <td>Dob</td>
                <td>Address</td>               
            </tr>
            <c:forEach items="${requestScope.emps}" var="e">
             <tr>
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
        </table>
    </body>
</html>
