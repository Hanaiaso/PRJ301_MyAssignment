package Employee.Controller;
import Employee.Entity.Department;
import Employee.Entity.Employee;
import dal.DBContext;
import dal.DepartmentDBContext;
import dal.EmployeeDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
public class EmployeeDeleteAllController extends HttpServlet {   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EmployeeDBContext db = new EmployeeDBContext();
        ArrayList<Employee> e = db.list();  
        request.setAttribute("e", e);
        request.getRequestDispatcher("../view/employee/delete.jsp").forward(request, response); 
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] eids = request.getParameterValues("eids");
        EmployeeDBContext db = new EmployeeDBContext();
        db.delete(eids);
        response.sendRedirect("filter");      
    }
}
