<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Attendance Management</title>
        <!-- Thêm thư viện jQuery từ Google CDN để dễ dàng thực hiện AJAX -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            // Hàm để cập nhật danh sách PlanCampain dựa trên PlanId đã chọn
            function updatePlanCampain() {
                var planId = $("#planSelect").val(); // Lấy giá trị của Plan được chọn từ dropdown
                $.ajax({
                    url: 'attendance', // Gửi yêu cầu tới servlet attendance
                    method: 'GET', // Sử dụng phương thức GET
                    data: {plid: planId}, // Gửi dữ liệu planId
                    dataType: 'json', // Đảm bảo rằng chúng ta đang làm việc với dữ liệu JSON
                    success: function (data) {
                        var planCampainSelect = $('#planCampainSelect');
                        planCampainSelect.empty(); // Xóa các option trước đó trong dropdown PlanCampaign
                        planCampainSelect.append('<option value="">Select Plan Campaign</option>'); // Thêm option mặc định

                        // Duyệt qua danh sách dữ liệu nhận được và thêm các PlanCampaign vào dropdown
                        $.each(data, function (index, planCampain) {
                            planCampainSelect.append('<option value="' + planCampain.id + '">' + planCampain.product.name + ' - ' + planCampain.quantity + '</option>');
                        });
                    },
                    error: function (xhr, status, error) {
                        console.error('Failed to fetch plan campaigns:', status, error); // In ra lỗi nếu có
                    }
                });
            }

            // Hàm để cập nhật danh sách ScheduleCampain dựa trên PlanCampainId đã chọn
            function updateScheduleCampain() {
                var planCampainId = $("#planCampainSelect").val(); // Lấy giá trị của PlanCampain được chọn từ dropdown
                $.ajax({
                    url: 'attendance', // Gửi yêu cầu tới servlet attendance
                    method: 'GET', // Sử dụng phương thức GET
                    data: {plcid: planCampainId}, // Gửi dữ liệu planCampainId
                    dataType: 'json', // Đảm bảo rằng chúng ta đang làm việc với dữ liệu JSON
                    success: function (data) {
                        var scheduleCampainSelect = $('#scheduleCampainSelect');
                        scheduleCampainSelect.empty(); // Xóa các option trước đó trong dropdown ScheduleCampaign
                        scheduleCampainSelect.append('<option value="">Select Schedule Campaign</option>'); // Thêm option mặc định

                        // Duyệt qua danh sách dữ liệu nhận được và thêm các ScheduleCampaign vào dropdown
                        $.each(data, function (index, scheduleCampain) {
                            scheduleCampainSelect.append('<option value="' + scheduleCampain.id + '">' + scheduleCampain.date + ' - Shift: ' + scheduleCampain.shift + ' - Quantity ' + scheduleCampain.quantity + '</option>');
                        });
                    },
                    error: function (xhr, status, error) {
                        console.error('Failed to fetch schedule campaigns:', status, error); // In ra lỗi nếu có
                    }
                });
            }

            // Hàm để cập nhật danh sách ScheduleEmployee dựa trên ScheduleCampainId đã chọn
            function updateScheduleEmployee() {
                var scheduleCampainId = $("#scheduleCampainSelect").val(); // Lấy giá trị của ScheduleCampaign được chọn từ dropdown

                // Gán giá trị vào input hidden scheduleCampainId
                $("#scheduleCampainId").val(scheduleCampainId);

                $.ajax({
                    url: 'attendance', // Gửi yêu cầu tới servlet attendance
                    method: 'GET', // Sử dụng phương thức GET
                    data: {scid: scheduleCampainId}, // Gửi dữ liệu scheduleCampainId
                    dataType: 'json', // Đảm bảo rằng chúng ta đang làm việc với dữ liệu JSON
                    success: function (data) {
                        var scheduleEmployeeTable = $('#scheduleEmployeeTable tbody');
                        scheduleEmployeeTable.empty(); // Xóa các dòng trước đó trong bảng

                        // Duyệt qua danh sách dữ liệu nhận được và thêm các ScheduleEmployee vào bảng
                        $.each(data, function (index, scheduleEmployee) {
                            if (scheduleEmployee.employee) { // Kiểm tra nếu employee tồn tại trong scheduleEmployee
                                var employee = scheduleEmployee.employee;

                                // Kiểm tra xem nhân viên này đã được chấm công hay chưa
                                var isAttendanceRecorded = scheduleEmployee.isAttendanceRecorded;

                                // Tạo một hàng mới cho mỗi nhân viên
                                var row = '<tr>' +
                                        '<td>' + employee.id + '</td>' +
                                        '<td>' + employee.name + '</td>' +
                                        '<td>' + scheduleEmployee.quantity + '</td>';

                                if (isAttendanceRecorded) {
                                    // Nếu nhân viên đã được chấm công, vô hiệu hóa trường nhập liệu và hiển thị thông báo
                                    row += '<td><input type="number" name="quantity-' + employee.id + '" min="0" value="' + (scheduleEmployee.attendanceQuantity || '') + '" disabled /></td>' +
                                            '<td><input type="text" name="alpha-' + employee.id + '" value="' + (scheduleEmployee.alpha || '') + '" disabled /></td>' +
                                            '<td><span style="color: red;">Already recorded</span></td>';
                                } else {
                                    // Nếu nhân viên chưa được chấm công, cho phép nhập liệu
                                    row += '<td><input type="number" name="quantity-' + employee.id + '" min="0" value="' + (scheduleEmployee.attendanceQuantity || '') + '" /></td>' +
                                            '<td><input type="text" name="alpha-' + employee.id + '" value="' + (scheduleEmployee.alpha || '') + '" /></td>';
                                }

                                row += '</tr>';
                                scheduleEmployeeTable.append(row);
                            }
                        });
                    },
                    error: function (xhr, status, error) {
                        console.error('Failed to fetch schedule employees:', status, error); // In ra lỗi nếu có
                    }
                });
            }



            function validateForm() {
                var scheduleCampainId = $("#scheduleCampainId").val();
                if (!scheduleCampainId) {
                    alert("Please select a Schedule Campaign before submitting.");
                    return false; // Ngăn chặn việc gửi form nếu không có Schedule Campaign
                }
                return true; // Cho phép gửi form nếu mọi thứ hợp lệ
            }

        </script>
    </head>
    <body>
        <h1>Attendance Management</h1>
        <!-- Section One: Selection -->
        <div>
            <h2>Select Plan, Campaign, and Schedule</h2>

            <!-- Dropdown chọn Plan -->
            <label for="planSelect">Select Plan:</label>
            <select id="planSelect" onchange="updatePlanCampain()">
                <option value="">Select Plan</option>
                <c:forEach var="plan" items="${plans}">
                    <option value="${plan.id}">${plan.name}</option>
                </c:forEach>
            </select>

            <!-- Dropdown chọn Plan Campaign - được cập nhật động bằng AJAX -->
            <label for="planCampainSelect">Select Plan Campaign:</label>
            <select id="planCampainSelect" onchange="updateScheduleCampain()">
                <option value="">Select Plan Campaign</option>
            </select>

            <!-- Dropdown chọn Schedule Campaign - được cập nhật động bằng AJAX -->
            <label for="scheduleCampainSelect">Select Schedule Campaign:</label>
            <select id="scheduleCampainSelect" onchange="updateScheduleEmployee()">
                <option value="">Select Schedule Campaign</option>
            </select>
        </div>

        <!-- Section Two: Schedule Employees -->
        <div>
            <h2>Schedule Employees</h2>
            <form action="attendance" method="post" onsubmit="return validateForm();">
                <input type="hidden" name="scheduleCampainId" id="scheduleCampainId" />
                <table id="scheduleEmployeeTable" border="1">
                    <thead>
                        <tr>
                            <th>Employee ID</th>
                            <th>Employee Name</th>
                            <th>Assigned Quantity</th>
                            <th>Completed Quantity</th>
                            <th>Alpha</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Các ScheduleEmployee sẽ được thêm vào đây bằng AJAX -->
                    </tbody>
                </table>
                <button type="submit">Submit Attendance</button>
            </form>
        </div>

    </body>
</html>
