<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            function removeEmployee(id)
            {
                var result = confirm("Are you sure?");
                if(result)
                {
                    document.getElementById("frmRemoveEmployee"+id).submit();
                }
            }
        </script>
    </head>
    <body>
        <a href="create">
                <button>Add Employee</button>
        </a>
        <a href="deleteall"><button type="button" class="btn btn-danger mb-2">Delete All Employees</button></a>
        <a href="list"><button type="button" class="btn btn-primary mb-2">Employee's List</button></a><br>
        <form action="filter" method="GET">
            Id: <input type="text" name="id" value="${param.id}"/> <br/>
            Name: <input type="text" name="name" value="${param.name}"/> <br/>
            Gender: <input type="radio" name="gender"
                           ${param.gender ne null && param.gender eq "male"?"checked=\"checked\"":""}
                           value="male"/> Male
            <input type="radio" name="gender"
                   ${param.gender ne null && param.gender eq "female"?"checked=\"checked\"":""}
                   value="female"/> Female
            <input type="radio" name="gender" 
                   ${param.gender eq null or param.gender eq "both"?"checked=\"checked\"":""}
                   value="both"/> Both
            <br/>
            Dob - From: <input type="date" name="from" value="${param.from}"/> 
            - To: <input type="date" name="to" value="${param.to}"/> <br/>
            Address <input type="text" name="address" value="${param.address}"/> <br/>
            Department: <select name="did">
                <option value="-1">----------ALL------------</option>
                <c:forEach items="${requestScope.depts}" var="d">
                    <option
                        ${param.did ne null && param.did eq d.id?"selected=\"selected\"":""}
                        value="${d.id}">${d.name} - ${d.type}</option>
                </c:forEach>
            </select>
            <br/>
            <input type="submit" value="Search"/>
        </form>
        <table border="1px">
            <tr>
                <td>Id</td>
                <td>Name</td>
                <td>Gender</td>
                <td>Dob</td>
                <td>Address</td>
                <td>Department</td>
                <td></td>
            </tr>
            <c:forEach items="${requestScope.emps}" var="e">
                <tr>
                <td>${e.id}</td>
                <td>${e.name}</td>
                <td>${e.gender?"Male":"Female"}</td>
                <td>${e.dob}</td>
                <td>${e.address}</td>
                <td>${e.dept.name} - ${e.dept.type}</td>
                <td><a href="update?id=${e.id}">Edit |</a>
                <input type="button" value="Remove" onclick="removeEmployee(${e.id})"/>
                    <form id="frmRemoveEmployee${e.id}" action="delete" method="POST">
                        <input type="hidden" name="id" value="${e.id}"/>
                    </form>
                </td>
            </tr>
            </c:forEach>
    </body>
</html>
