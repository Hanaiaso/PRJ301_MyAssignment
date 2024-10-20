package Plan.Controller;

import Employee.Entity.Department;
import Plan.Entity.Plan;
import Plan.Entity.PlanCampain;
import dal.DepartmentDBContext;
import dal.PlanDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public class PlanUpdateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy ID kế hoạch từ tham số truy vấn
        int planId = Integer.parseInt(request.getParameter("id"));
        PlanDBContext planDB = new PlanDBContext();
        Plan plan = planDB.get(planId);

        // Lấy danh sách các chiến dịch liên quan đến kế hoạch
        ArrayList<PlanCampain> campains = planDB.getCampaignsByPlanId(planId);

        // Lấy danh sách các phòng ban
        DepartmentDBContext dbDept = new DepartmentDBContext();
        ArrayList<Department> depts = dbDept.list();

        // Thiết lập thuộc tính cho JSP
        request.setAttribute("depts", depts);
        request.setAttribute("plan", plan);
        request.setAttribute("campains", campains); // Thêm danh sách chiến dịch vào request

        // Chuyển hướng đến trang cập nhật kế hoạch
        RequestDispatcher dispatcher = request.getRequestDispatcher("../view/plan/update.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ request
            int planId = Integer.parseInt(request.getParameter("plid"));
            String name = request.getParameter("plname");
            Date startDate = Date.valueOf(request.getParameter("StartDate"));
            Date endDate = Date.valueOf(request.getParameter("EndDate"));
            int deptId = Integer.parseInt(request.getParameter("did"));

            // Tạo đối tượng Plan từ dữ liệu
            Plan plan = new Plan();
            plan.setId(planId);
            plan.setName(name);
            plan.setStart(startDate);
            plan.setEnd(endDate);

            // Tạo đối tượng Department
            Department dept = new Department();
            dept.setId(deptId);
            plan.setDept(dept);

            // Cập nhật các chiến dịch liên quan
            ArrayList<PlanCampain> campains = new ArrayList<>();
            String[] campaignIds = request.getParameterValues("campaignIds");

            if (campaignIds != null) {
                for (String id : campaignIds) {
                   
                    PlanCampain campaign = new PlanCampain();
                    campaign.setId(Integer.parseInt(id));
                   
                    campains.add(campaign);
                }
            }

            plan.setCampains(campains); // Thiết lập danh sách chiến dịch cho kế hoạch

            // Cập nhật kế hoạch vào cơ sở dữ liệu
            PlanDBContext planDB = new PlanDBContext();
            planDB.update(plan); // Gọi hàm update và kiểm tra kết quả

            // Chuyển hướng về trang danh sách sau khi cập nhật thành công
            response.sendRedirect("list");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("../view/plan/update.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
