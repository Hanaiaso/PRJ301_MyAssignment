package Plan.Controller;
import Employee.Entity.Department;
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
public class PlanCreateController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDBContext dbProduct = new ProductDBContext();
        DepartmentDBContext dbDepts = new DepartmentDBContext();       
        request.setAttribute("products", dbProduct.list());
        request.setAttribute("depts", dbDepts.get("WS"));        
        request.getRequestDispatcher("../view/plan/create.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Plan plan = new Plan();
        plan.setName(request.getParameter("name"));
        plan.setStart(Date.valueOf(request.getParameter("from")));
        plan.setEnd(Date.valueOf(request.getParameter("to")));
        Department d = new Department();
        d.setId(Integer.parseInt(request.getParameter("did")));
        plan.setDept(d);      
        String[] pids = request.getParameterValues("pid");
        for (String pid : pids) {
            PlanCampain c = new PlanCampain();           
            Product p = new Product();
            p.setId(Integer.parseInt(pid));
            c.setProduct(p);
            c.setPlan(plan);           
            String raw_quantity = request.getParameter("quantity"+pid);
            String raw_cost = request.getParameter("cost"+pid);    
            c.setQuantity(raw_quantity!=null&&raw_quantity.length()>0?Integer.parseInt(raw_quantity):0);
            c.setCost(raw_cost!=null&&raw_cost.length()>0?Float.parseFloat(raw_cost):0); 
            if(c.getQuantity()>0 && c.getCost()>0)
                plan.getCampains().add(c);
        }       
        if(plan.getCampains().size()>0)
        {
            PlanDBContext db = new PlanDBContext();
            db.insert(plan);
            response.sendRedirect("list");
        }
        else
        {
            response.getWriter().println("your plan does not have any products / campains");
        }
    }
}
