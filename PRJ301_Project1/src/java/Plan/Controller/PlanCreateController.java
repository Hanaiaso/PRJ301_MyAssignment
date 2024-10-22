/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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

/**
 *
 * @author LEGION
 */
public class PlanCreateController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PlanCreateController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PlanCreateController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDBContext dbProduct = new ProductDBContext();
        DepartmentDBContext dbDepts = new DepartmentDBContext();
        
        request.setAttribute("products", dbProduct.list());
        request.setAttribute("depts", dbDepts.get("WS"));
        
        request.getRequestDispatcher("../view/plan/create.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
            //insert
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
