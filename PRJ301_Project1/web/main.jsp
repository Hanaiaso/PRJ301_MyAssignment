<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Company Main Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
        }

        /* Header styling */
        .header {
            background-color: #d3d3d3;
            color: #333;
            padding: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            position: relative;
        }

        .header img {
            height: 50px;
            cursor: pointer;
            margin-right: 10px;
        }

        .header h1 {
            font-size: 2rem;
            margin: 0 20px;
            flex-grow: 1;
            text-align: center;
        }

        .user-menu {
            position: relative;
        }

        .user-icon {
            height: 50px;
            cursor: pointer;
            margin-left: 10px;
        }

        .dropdown-menu {
            position: absolute;
            right: 0;
            top: 60px;
            background-color: #ffffff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            display: none;
            z-index: 1050;
            padding: 10px;
            border-radius: 5px;
            width: 300px;
            height: 300px;
            overflow: hidden;
        }

        .dropdown-menu.active {
            display: block;
        }

        .dropdown-item {
            padding: 10px;
            cursor: pointer;
        }

        .dropdown-item:hover {
            background-color: #f0f0f0;
        }

        /* Footer styling */
        .footer {
            background-color: #d3d3d3;
            color: #333;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .footer div {
            text-align: center;
            margin-bottom: 10px;
        }

        /* Main content styling */
        .section-title {
            margin: 20px 0;
            font-size: 1.5rem;
            color: #007bff;
        }

        .card {
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            transition: 0.3s;
        }

        .card:hover {
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            transform: scale(1.02);
        }

        button {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #5a6268;
            color: #fff;
        }
    </style>
</head>
<body>
    <!-- Header -->
    <div class="header">
        <div class="user-menu">
            <img src="img/5-hinh-anh-ngay-moi-hanh-phuc-sieu-cute-inkythuatso-09-13-36-23.jpg" alt="User Icon" class="user-icon" onclick="toggleDropdown()">
            
            <div class="dropdown-menu" id="userDropdown">
                <div class="dropdown-item"><strong>WELCOME</strong> </div>
                <div class="dropdown-item"><strong>Username: </strong> ${requestScope.name}</div>
                <div class="dropdown-item"><strong>Displayname: </strong> ${requestScope.dname}</div>
                <a href="logout"><div class="dropdown-item"><button type="button" >Logout</button></div></a>
            </div>
        </div>
        
        <h1>TRE COMPANY</h1>
        <a href="main"><img src="img/images.png" alt="Company Logo"></a>
      
    </div>

    <!-- Main Content -->
    <div class="container mt-5">
        <div class="row">
            <!-- Progress Section -->
            <div class="col-md-6 mb-4">
                <div class="section-title">Progress</div>
                <div class="card">
                    <div class="card-body">
                        <a href="progress"><button type="button" class="btn btn-primary">Progress</button></a>
                    </div>
                </div>
            </div>

            <!-- Employee Section -->
            <div class="col-md-6 mb-4">
                <div class="section-title">Employee</div>
                <div class="card">
                    <div class="card-body">
                        
                        <a href="employee/filter"><button type="button" class="btn btn-primary mb-2">Employee's Filter</button></a>
                        
                        
                        <a href="employee/bonus"><button type="button" class="btn btn-success mb-2">Bonus</button></a>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <!-- Plans Section -->
            <div class="col-md-6 mb-4">
                <div class="section-title">Plans</div>
                <div class="card">
                    <div class="card-body">
                        <a href="product/list"><button type="button" class="btn btn-primary mb-2">Product</button></a>
                        
                        <a href="plan/list"><button type="button" class="btn btn-primary mb-2">List Plans</button></a>
                    </div>
                </div>
            </div>

            <!-- Attendance Section -->
            <div class="col-md-6 mb-4">
                <div class="section-title">Attendance</div>
                <div class="card">
                    <div class="card-body">
                        <a href="plan/select"><button type="button" class="btn btn-primary mb-2">Assign Employee to Plan</button></a>
                        <a href="scheduleemployee/list"><button type="button" class="btn btn-primary mb-2">Assigned Employee List</button></a>
                        <a href="employee/attendance"><button type="button" class="btn btn-primary mb-2">Employee Attendance</button></a>
                        <a href="attendance/list"><button type="button" class="btn btn-primary mb-2">Employee Attendance List</button></a>
                    </div>
                </div>
            </div>

            <!-- User Section -->
            <div class="col-md-6 mb-4">
                <div class="section-title">User</div>
                <div class="card">
                    <div class="card-body">
                        <a href="user/list"><button type="button" class="btn btn-primary mb-2">User</button></a>
                        
                        <a href="role/list"><button type="button" class="btn btn-primary mb-2">Role</button></a>
                        <a href="department/list"><button type="button" class="btn btn-primary mb-2">Department</button></a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <div class="footer">
        <div>
            <h5>Contact Information</h5>
            <p>Email: contact@company.com</p>
            <p>Phone: +123 456 7890</p>
        </div>
        <div>
            <h5>About the Company</h5>
            <p>Company XYZ specializes in providing top-notch employee and project management solutions.</p>
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        function toggleDropdown() {
            document.getElementById("userDropdown").classList.toggle("active");
        }
    </script>
</body>
</html>
