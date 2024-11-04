package Plan.Controller;

import Employee.Entity.Department;
import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;
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

public class PlanUpdateController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        DepartmentDBContext departmentDB = new DepartmentDBContext();
        PlanCampainDBContext pcdb = new PlanCampainDBContext();
        ArrayList<Department> departments = departmentDB.list();
        req.setAttribute("departments", departments);
        String plid = req.getParameter("id");
        if (plid != null) {
            PlanDBContext planDB = new PlanDBContext();
            Plan plan = planDB.get(Integer.parseInt(plid));
            if (plan != null) {
                req.setAttribute("plan", plan);
                List<PlanCampain> campains = pcdb.getCampainsByPlanId(plan.getId());
                req.setAttribute("campains", campains);
                req.getRequestDispatcher("../view/plan/update.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("list");
            }
        } else {
            resp.sendRedirect("list");
        }
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        int plid = Integer.parseInt(req.getParameter("plid"));
        String plname = req.getParameter("plname");
        Date startDate = Date.valueOf(req.getParameter("startDate"));
        Date endDate = Date.valueOf(req.getParameter("endDate"));
        int did = Integer.parseInt(req.getParameter("did"));
        Plan plan = new Plan();
        plan.setId(plid);
        plan.setName(plname);
        plan.setStart(startDate);
        plan.setEnd(endDate);
        Department dept = new Department();
        dept.setId(did);
        plan.setDept(dept);
        ArrayList<PlanCampain> campaigns = new ArrayList<>();
        String[] plcids = req.getParameterValues("plcid");
        if (plcids != null) {
            for (String plcid : plcids) {
                PlanCampain campaign = new PlanCampain();
                campaign.setId(Integer.parseInt(plcid));
                Product product = new Product();
                String productName = req.getParameter("product" + plcid);
                product.setName(productName);
                campaign.setProduct(product);
                campaign.setQuantity(Integer.parseInt(req.getParameter("quantity" + plcid)));
                campaign.setCost(Float.parseFloat(req.getParameter("estimate" + plcid)));
                campaigns.add(campaign);
            }
        }
        plan.setCampains(campaigns);
        PlanDBContext planDB = new PlanDBContext();
        planDB.update(plan);
        resp.sendRedirect("list");
    }

}
