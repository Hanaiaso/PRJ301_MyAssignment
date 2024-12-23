package Schedule.Controller;

import Employee.Entity.Employee;
import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;
import Plan.Entity.Plan;
import Plan.Entity.PlanCampain;
import Schedule.Entity.ScheduleCampain;
import Schedule.Entity.ScheduleEmployee;
import Schedule.Entity.Attendance;
import com.google.gson.Gson;
import dal.PlanDBContext;
import dal.PlanCampainDBContext;
import dal.ScheduleCampainDBContext;
import dal.ScheduleEmployeeDBContext;
import dal.AttendanceDBContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class EmployeeAttendanceController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        // Lấy tham số "plid", "plcid", "scid" từ yêu cầu
        String planIdParam = req.getParameter("plid");
        String planCampainIdParam = req.getParameter("plcid");
        String scheduleIdParam = req.getParameter("scid");

        if (planIdParam != null) {
            int plid = Integer.parseInt(planIdParam);
            PlanCampainDBContext planCampainDB = new PlanCampainDBContext();
            ArrayList<PlanCampain> planCampains = planCampainDB.getCampainsByPlanId(plid);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            String json = new Gson().toJson(planCampains);
            resp.getWriter().print(json);
        } else if (planCampainIdParam != null) {
            int plcid = Integer.parseInt(planCampainIdParam);
            ScheduleCampainDBContext scheduleCampainDB = new ScheduleCampainDBContext();
            ArrayList<ScheduleCampain> scheduleCampains = scheduleCampainDB.getSchedulesByPlanCampainId(plcid);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            String json = new Gson().toJson(scheduleCampains);
            resp.getWriter().print(json);
        } else if (scheduleIdParam != null) {
            int scid = Integer.parseInt(scheduleIdParam);
            ScheduleEmployeeDBContext scheduleEmployeeDB = new ScheduleEmployeeDBContext();
            ArrayList<ScheduleEmployee> scheduleEmployees = scheduleEmployeeDB.getEmployeesByScheduleId(scid);

            // Kiểm tra xem nhân viên đã được chấm công chưa
            AttendanceDBContext attendanceDB = new AttendanceDBContext();
            for (ScheduleEmployee scheduleEmployee : scheduleEmployees) {
                boolean isRecorded = attendanceDB.isAttendanceRecorded(scheduleEmployee.getId());
                scheduleEmployee.setAttendanceRecorded(isRecorded);
            }

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            String json = new Gson().toJson(scheduleEmployees);
            resp.getWriter().print(json);
        } else {
            PlanDBContext planDB = new PlanDBContext();
            ArrayList<Plan> plans = planDB.list();
            req.setAttribute("plans", plans);
            req.getRequestDispatcher("../view/schedule/attendance.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        // Lấy scheduleCampainId từ form đã gửi
        String scheduleCampainIdParam = req.getParameter("scheduleCampainId");

        if (scheduleCampainIdParam == null || scheduleCampainIdParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Schedule Campaign ID is missing");
            return;
        }

        int scheduleCampainId = Integer.parseInt(scheduleCampainIdParam);

        // Khởi tạo AttendanceDBContext và ScheduleEmployeeDBContext
        AttendanceDBContext attendanceDB = new AttendanceDBContext();
        ScheduleEmployeeDBContext scheduleEmployeeDB = new ScheduleEmployeeDBContext();

        // Lấy danh sách ScheduleEmployee dựa trên scheduleCampainId
        ArrayList<ScheduleEmployee> scheduleEmployees = scheduleEmployeeDB.getEmployeesByScheduleId(scheduleCampainId);

        // Tạo danh sách các đối tượng Attendance để chèn vào database
        List<Attendance> attendances = new ArrayList<>();

        for (ScheduleEmployee scheduleEmployee : scheduleEmployees) {
            int seid = scheduleEmployee.getId(); // ScheduleEmployee ID
            int employeeId = scheduleEmployee.getEmployee().getId();
            String quantityParam = req.getParameter("quantity-" + employeeId);
            String alphaParam = req.getParameter("alpha-" + employeeId);

            // Kiểm tra xem giá trị quantity và alpha có không rỗng và hợp lệ
            if (quantityParam != null && !quantityParam.isEmpty()
                    && alphaParam != null && !alphaParam.isEmpty()) {

                try {
                    // Kiểm tra xem nhân viên này đã được chấm công hay chưa
                    if (attendanceDB.isAttendanceRecorded(seid)) {
                        continue; // Nếu đã chấm công rồi, bỏ qua
                    }

                    int quantity = Integer.parseInt(quantityParam);
                    double alpha = Double.parseDouble(alphaParam);

                    // Tạo đối tượng Attendance và thêm vào danh sách attendances
                    Attendance attendance = new Attendance();
                    attendance.setScheduleEmployee(scheduleEmployee); // Tham chiếu đến ScheduleEmployee
                    attendance.setQuantity(quantity);
                    attendance.setAlpha(alpha);

                    attendances.add(attendance);
                } catch (NumberFormatException e) {
                    // Xử lý ngoại lệ khi chuyển đổi số thất bại
                    e.printStackTrace();
                }
            }
        }

        // Chèn tất cả các bản ghi Attendance vào database
        if (!attendances.isEmpty()) {
            attendanceDB.insertMultiple(attendances);
        }

        // Chuyển hướng lại trang để hiển thị thông báo thành công
        resp.sendRedirect("attendance");
    }

}
