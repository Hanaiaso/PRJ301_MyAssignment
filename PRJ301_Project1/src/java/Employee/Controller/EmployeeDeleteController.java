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
import java.util.ArrayList;
public class EmployeeDeleteController extends BaseRBACCOntroller {
    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
         resp.sendError(403,"you cannot access the feature using this way!");
    }
    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Employee e = new Employee();
        e.setId(id);
        EmployeeDBContext db = new EmployeeDBContext();
        db.delete(e);
        resp.sendRedirect("filter");     
    }
}
