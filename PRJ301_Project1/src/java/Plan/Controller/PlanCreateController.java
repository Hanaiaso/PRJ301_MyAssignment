package Plan.Controller;

import Employee.Entity.Department;
import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;
import Plan.Entity.Plan;
import Plan.Entity.PlanCampain;
import Plan.Entity.Product;
import dal.DepartmentDBContext;
import dal.PlanDBContext;
import dal.ProductDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

public class PlanCreateController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        ProductDBContext dbProduct = new ProductDBContext();
        DepartmentDBContext dbDepts = new DepartmentDBContext();
        req.setAttribute("products", dbProduct.list());
        req.setAttribute("depts", dbDepts.get("WS"));
        req.getRequestDispatcher("../view/plan/create.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        Plan plan = new Plan();
        plan.setName(req.getParameter("name"));
        plan.setStart(Date.valueOf(req.getParameter("from")));
        plan.setEnd(Date.valueOf(req.getParameter("to")));
        Department d = new Department();
        d.setId(Integer.parseInt(req.getParameter("did")));
        plan.setDept(d);
        String[] pids = req.getParameterValues("pid");
        for (String pid : pids) {
            PlanCampain c = new PlanCampain();
            Product p = new Product();
            p.setId(Integer.parseInt(pid));
            c.setProduct(p);
            c.setPlan(plan);
            String raw_quantity = req.getParameter("quantity" + pid);
            String raw_cost = req.getParameter("cost" + pid);
            c.setQuantity(raw_quantity != null && raw_quantity.length() > 0 ? Integer.parseInt(raw_quantity) : 0);
            c.setCost(raw_cost != null && raw_cost.length() > 0 ? Float.parseFloat(raw_cost) : 0);
            if (c.getQuantity() > 0 && c.getCost() > 0) {
                plan.getCampains().add(c);
            }
        }
        if (plan.getCampains().size() > 0) {
            PlanDBContext db = new PlanDBContext();
            db.insert(plan);
            resp.sendRedirect("list");
        } else {
            resp.getWriter().println("your plan does not have any products / campains");
        }
    }

}
