package Employee.Controller;


import Employee.Entity.Employee;
import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;

import dal.EmployeeDBContext;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.ArrayList;

public class EmployeeDeleteAllController extends BaseRBACCOntroller {

    @Override
     protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        EmployeeDBContext db = new EmployeeDBContext();
        ArrayList<Employee> e = db.list();
        req.setAttribute("e", e);
        req.getRequestDispatcher("../view/employee/delete.jsp").forward(req, resp);
    }

    @Override
   protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        String[] eids = req.getParameterValues("eids");
        EmployeeDBContext db = new EmployeeDBContext();
        db.delete(eids);
        resp.sendRedirect("filter");
    }

   
}
