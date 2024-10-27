<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
    <style>
        /* Để cải thiện bố cục */
        div {
            display: flex;
            flex-direction: column;
            gap: 10px;
            max-width: 300px;
        }
        button {
            width: 100%;
            padding: 10px;
            font-size: 16px;
        }
    </style>
</head>
<body>
    <div>
        <h1>Main Menu</h1>

        <!-- Form không có input thì không cần thiết -->
        <form action="main" method="POST"></form>

        <!-- Nút danh sách nhân viên -->
        <a href="employee/list">
            <button type="button">Employee's List</button>
        </a>

        <!-- Nút lọc nhân viên -->
        <a href="employee/filter">
            <button type="button">Employee's Filter</button>
        </a>

        <!-- Nút tạo nhân viên mới -->
        <a href="employee/create">
            <button type="button">Create Employee</button>
        </a>

        <!-- Nút xóa tất cả nhân viên -->
        <a href="employee/deleteall">
            <button type="button">Delete All Employees</button>
        </a>

        <!-- Nút tạo kế hoạch mới -->
        <a href="plan/create">
            <button type="button">Create Plan</button>
        </a>

        <!-- Nút danh sách kế hoạch -->
        <a href="plan/list">
            <button type="button">List Plans</button>
        </a>

        <!-- Nút phân công nhân viên vào kế hoạch -->
        <a href="plan/select">
            <button type="button">Assign Employee to Plan</button>
        </a>

        <!-- Nút danh sách nhân viên đã được phân công -->
        <a href="scheduleemployee/list">
            <button type="button">Assigned Employee List</button>
        </a>

        <!-- Nút chấm công cho nhân viên -->
        <a href="employee/attendance">
            <button type="button">Employee Attendance</button>
        </a>
        
        <!-- Nút danh sách chấm công nhân viên -->
        <a href="attendance/list">
            <button type="button">Employee Attendance List</button>
        </a>
        
        <!-- Nút theo dõi tiến độ -->
        <a href="progress">
            <button type="button">Progress</button>
        </a>
    </div>
</body>
</html>
