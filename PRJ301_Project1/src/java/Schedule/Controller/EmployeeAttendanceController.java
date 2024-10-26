package Schedule.Controller;

import Employee.Entity.Employee;
import Schedule.Entity.ScheduleCampain;
import Schedule.Entity.ScheduleEmployee;
import Schedule.Entity.Attendance;
import dal.AttendanceDBContext;
import dal.ScheduleCampainDBContext;
import dal.ScheduleEmployeeDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EmployeeAttendanceController extends HttpServlet {

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    int scid = Integer.parseInt(request.getParameter("scid"));
    ScheduleCampainDBContext scDB = new ScheduleCampainDBContext();
    ScheduleCampain schedule = scDB.get(scid);

    ScheduleEmployeeDBContext seDB = new ScheduleEmployeeDBContext();
    ArrayList<ScheduleEmployee> scheduleEmployees = seDB.getEmployeesByScheduleId(scid);

  

    request.setAttribute("schedule", schedule);
    request.setAttribute("scheduleEmployees", scheduleEmployees);
    request.getRequestDispatcher("../view/schedule/attendance.jsp").forward(request, response);
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int scid = Integer.parseInt(request.getParameter("scid"));
        String[] employeeIds = request.getParameterValues("eid");

        // Khởi tạo DBContext để tương tác với dữ liệu
        AttendanceDBContext attendanceDB = new AttendanceDBContext();
        ScheduleCampainDBContext scDB = new ScheduleCampainDBContext();
        ScheduleCampain schedule = scDB.get(scid);

        if (employeeIds != null && employeeIds.length > 0) {
            List<Attendance> attendances = new ArrayList<>();

            for (String employeeId : employeeIds) {
                int eid = Integer.parseInt(employeeId);
                String quantityParam = request.getParameter("quantity-" + eid);
                String alphaParam = request.getParameter("alpha-" + eid);

                if (quantityParam != null && !quantityParam.isEmpty() && alphaParam != null && !alphaParam.isEmpty()) {
                    int quantity = Integer.parseInt(quantityParam);
                    double alpha = Double.parseDouble(alphaParam);

                    Attendance attendance = new Attendance();

                    // Thiết lập các thuộc tính cho Attendance
                    Employee employee = new Employee();
                    employee.setId(eid);
                    attendance.setEmployee(employee);

                    attendance.setScheduleCampain(schedule);
                    attendance.setDate(schedule.getDate());
                    attendance.setQuantity(quantity);
                    attendance.setAlpha(alpha);

                    attendances.add(attendance);
                }
            }

            if (!attendances.isEmpty()) {
                // Chèn dữ liệu chấm công cho nhiều nhân viên
                attendanceDB.insertMultipleAttendances(attendances);
                request.setAttribute("successMessage", "Chấm công thành công.");
            } else {
                request.setAttribute("errorMessage", "Không có thông tin chấm công hợp lệ.");
            }
        } else {
            request.setAttribute("errorMessage", "Không có nhân viên nào được chọn để chấm công.");
        }

        doGet(request, response);
    }
}
