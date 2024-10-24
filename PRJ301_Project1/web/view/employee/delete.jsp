
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            function confirmDelete() {
                const checkboxes = document.querySelectorAll('input[name="eids"]:checked');
                if (checkboxes.length === 0) {
                    alert("Please select at least one employee to delete.");
                    return false; 
                }
                var result = confirm("Are you sure you want to delete the selected employees?");
                if (result) {
                    return true; 
                } else {
                    return false; 
                }
            }
        </script>
    </head>
    <body>
        <form id="frmRemoveEmployee" action="deleteall" method="post" onsubmit="return confirmDelete();">
            <table border="1">
                <thead>
                    <tr>
                        <td>Select</td> 
                        <td>Id</td>
                        <td>Name</td>
                        <td>Gender</td>
                        <td>Dob</td>
                        <td>Address</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.e}" var="e">
                        <tr>
                            <td>
                                <input type="checkbox" name="eids" value="${e.id}">
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
            <input type="submit" value="Delete Selected Employees"/>
        </form>
    </body>
</html>
