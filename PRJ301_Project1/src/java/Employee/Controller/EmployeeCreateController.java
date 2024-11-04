package Employee.Controller;

import Employee.Entity.Department;
import Employee.Entity.Employee;
import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;
import dal.DepartmentDBContext;
import dal.EmployeeDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

public class EmployeeCreateController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        DepartmentDBContext db = new DepartmentDBContext();
        ArrayList<Department> depts = db.list();
        req.setAttribute("depts", depts);
        req.getRequestDispatcher("../view/employee/create.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        String raw_name = req.getParameter("name");
        String raw_gender = req.getParameter("gender");
        String raw_dob = req.getParameter("dob");
        String raw_address = req.getParameter("address");
        String raw_did = req.getParameter("did");
        String raw_salary = req.getParameter("salary");
        Employee e = new Employee();
        e.setName(raw_name);
        e.setAddress(raw_address);
        e.setGender(raw_gender.equals("male"));
        e.setDob(Date.valueOf(raw_dob));
        e.setSalary(Double.parseDouble(raw_salary));
        Department d = new Department();
        d.setId(Integer.parseInt(raw_did));
        e.setDept(d);
        e.setCreatedby(account);
        EmployeeDBContext db = new EmployeeDBContext();
        db.insert(e);
        resp.sendRedirect("filter");
    }
}
