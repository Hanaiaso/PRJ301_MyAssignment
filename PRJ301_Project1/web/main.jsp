<%-- 
    Document   : main
    Created on : Oct 11, 2024, 12:41:42 PM
    Author     : LEGION
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div>
            <form action="main" method="POST">

            </form>

            <a href="employee/list">
                <button>Employee's List</button>
            </a>
            <a href="employee/filter">
                <button>Employee's Filter</button>
            </a>
            <a href="employee/create">
                <button>Employee's Create</button>
            </a>
             <a href="employee/deleteall">
                <button>Employee's Delete</button>
            </a>
             <a href="plan/create">
                <button>Create Plan</button>
            </a>
             <a href="plan/list">
                <button>List Plan</button>
            </a>
        </div>
    </body>
</html>
