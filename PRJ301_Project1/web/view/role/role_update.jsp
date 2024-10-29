<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Update Role</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <h1>Update Role</h1>
            <form action="update" method="POST">
                <input type="hidden" name="roleId" value="${role.id}" />
                <div class="form-group">
                    <label for="roleName">Role Name:</label>
                    <input type="text" id="roleName" name="roleName" class="form-control" value="${role.name}" required>
                </div>
                <div class="form-group">
                    <label>Features:</label><br/>
                    <c:forEach var="feature" items="${allFeatures}">
                        <input type="checkbox" name="features" value="${feature.id}" 
                               <c:forEach var="roleFeature" items="${role.features}">
                                   <c:if test="${feature.id == roleFeature.id}">
                                       checked
                                   </c:if>
                               </c:forEach> />
                        ${feature.name} <br/>
                    </c:forEach>
                </div>
                <button type="submit" class="btn btn-primary">Save Changes</button>
            </form>
        </div>
    </body>
</html>
