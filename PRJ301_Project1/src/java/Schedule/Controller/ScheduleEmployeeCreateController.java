package Schedule.Controller;

import Employee.Entity.Employee;
import Plan.Entity.PlanCampain;
import Schedule.Entity.ScheduleCampain;
import dal.EmployeeDBContext;
import dal.PlanCampainDBContext;
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

public class ScheduleEmployeeCreateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int scid = Integer.parseInt(request.getParameter("scid"));
        ScheduleCampainDBContext scDB = new ScheduleCampainDBContext();
        ScheduleCampain schedule = scDB.get(scid);
        int departmentId = schedule.getPlancampain().getPlan().getDept().getId();
        EmployeeDBContext employeeDB = new EmployeeDBContext();
        ArrayList<Employee> employees = employeeDB.getEmployeesByDepartmentId(departmentId);
        System.out.println("Department ID: " + departmentId);
        System.out.println("Total employees retrieved: " + employees.size());
        // Gửi dữ liệu sang JSP
        request.setAttribute("schedule", schedule);
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("../view/schedule/assign_employee.jsp").forward(request, response);
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    int scid = Integer.parseInt(request.getParameter("scid"));
    String[] employeeIds = request.getParameterValues("eid");

    ScheduleEmployeeDBContext seDB = new ScheduleEmployeeDBContext();
    ScheduleCampainDBContext scDB = new ScheduleCampainDBContext();
    ScheduleCampain schedule = scDB.get(scid);

    if (employeeIds != null && employeeIds.length > 0) {
        int totalAssigned = 0;
        boolean errorOccurred = false;
        
        List<Integer> eids = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        for (String employeeId : employeeIds) {
            int eid = Integer.parseInt(employeeId);
            String quantityParam = request.getParameter("quantity-" + eid);

            if (quantityParam != null && !quantityParam.isEmpty()) {
                int quantity = Integer.parseInt(quantityParam);
                totalAssigned += quantity;

                // Kiểm tra nếu số lượng phân công vượt quá số lượng còn lại
                if (totalAssigned > (schedule.getQuantity() - schedule.getAssignedQuantity())) {
                    request.setAttribute("errorMessage", "Tổng số lượng phân công vượt quá giới hạn cho phép.");
                    errorOccurred = true;
                    break;
                }

                // Thêm vào danh sách để insert sau
                eids.add(eid);
                quantities.add(quantity);
            }
        }

        if (!errorOccurred) {
            // Sử dụng hàm insertMultipleAssignments để lưu tất cả nhân viên cùng một lúc
            seDB.insertMultipleAssignments(scid, eids, quantities);
            request.setAttribute("successMessage", "Phân công thành công.");
        }
    } else {
        request.setAttribute("errorMessage", "Không có nhân viên nào được chọn để phân công.");
    }

    doGet(request, response);
}
}
