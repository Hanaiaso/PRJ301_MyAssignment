package Plan.Controller;

import Employee.Entity.Department;
import Plan.Entity.Plan;
import Plan.Entity.PlanCampain;
import Plan.Entity.Product;
import dal.DepartmentDBContext;
import dal.PlanCampainDBContext;
import dal.PlanDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PlanUpdateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DepartmentDBContext departmentDB = new DepartmentDBContext();
        PlanCampainDBContext pcdb = new PlanCampainDBContext();
        ArrayList<Department> departments = departmentDB.list();
        request.setAttribute("departments", departments);
        String plid = request.getParameter("id");
        if (plid != null) {
            PlanDBContext planDB = new PlanDBContext();
            Plan plan = planDB.get(Integer.parseInt(plid));
            if (plan != null) {
                request.setAttribute("plan", plan);
                List<PlanCampain> campains = pcdb.getCampainsByPlanId(plan.getId());
                request.setAttribute("campains", campains);
                request.getRequestDispatcher("../view/plan/update.jsp").forward(request, response);
            } else {
                response.sendRedirect("list");
            }
        } else {
            response.sendRedirect("list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int plid = Integer.parseInt(request.getParameter("plid"));
        String plname = request.getParameter("plname");
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        int did = Integer.parseInt(request.getParameter("did"));
        Plan plan = new Plan();
        plan.setId(plid);
        plan.setName(plname);
        plan.setStart(startDate);
        plan.setEnd(endDate);
        Department dept = new Department();
        dept.setId(did);
        plan.setDept(dept);
        ArrayList<PlanCampain> campaigns = new ArrayList<>();
        String[] plcids = request.getParameterValues("plcid");
        if (plcids != null) {
            for (String plcid : plcids) {
                PlanCampain campaign = new PlanCampain();
                campaign.setId(Integer.parseInt(plcid));
                Product product = new Product();
                String productName = request.getParameter("product" + plcid);
                product.setName(productName);
                campaign.setProduct(product);
                campaign.setQuantity(Integer.parseInt(request.getParameter("quantity" + plcid)));
                campaign.setCost(Float.parseFloat(request.getParameter("estimate" + plcid)));
                campaigns.add(campaign);
            }
        }
        plan.setCampains(campaigns);
        PlanDBContext planDB = new PlanDBContext();
        planDB.update(plan);
        response.sendRedirect("list");
    }
}
