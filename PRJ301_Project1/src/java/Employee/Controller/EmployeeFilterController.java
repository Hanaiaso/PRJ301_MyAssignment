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

public class EmployeeFilterController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        String raw_id = req.getParameter("id");
        String raw_name = req.getParameter("name");
        String raw_gender = req.getParameter("gender");
        String raw_address = req.getParameter("address");
        String raw_from = req.getParameter("from");
        String raw_to = req.getParameter("to");
        String raw_did = req.getParameter("did");
        Integer id = (raw_id != null && !raw_id.isBlank()) ? Integer.parseInt(raw_id) : null;
        String name = raw_name;
        Boolean gender = (raw_gender != null) && !raw_gender.equals("both") ? raw_gender.equals("male") : null;
        String address = raw_address;
        Date from = (raw_from != null && !raw_from.isBlank()) ? Date.valueOf(raw_from) : null;
        Date to = (raw_to != null && !raw_to.isBlank()) ? Date.valueOf(raw_to) : null;
        Integer did = (raw_did != null && !raw_did.equals("-1")) ? Integer.parseInt(raw_did) : null;
        EmployeeDBContext dbEmp = new EmployeeDBContext();
        DepartmentDBContext dbDept = new DepartmentDBContext();
        ArrayList<Employee> emps = dbEmp.search(id, name, gender, address, from, to, did);
        req.setAttribute("emps", emps);
        ArrayList<Department> depts = dbDept.list();
        req.setAttribute("depts", depts);
        req.getRequestDispatcher("../view/employee/filter.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        doAuthorizedGet(req, resp, account);
    }
}
