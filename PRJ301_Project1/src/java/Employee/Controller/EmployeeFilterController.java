package Employee.Controller;
import Employee.Entity.Department;
import Employee.Entity.Employee;
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
public class EmployeeFilterController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String raw_id = request.getParameter("id");
        String raw_name = request.getParameter("name");
        String raw_gender = request.getParameter("gender");
        String raw_address = request.getParameter("address");
        String raw_from = request.getParameter("from");
        String raw_to = request.getParameter("to");
        String raw_did = request.getParameter("did");
        Integer id = (raw_id != null && !raw_id.isBlank())?Integer.parseInt(raw_id):null;
        String name = raw_name;
        Boolean gender = (raw_gender!=null) && !raw_gender.equals("both")?raw_gender.equals("male"):null;
        String address = raw_address;
        Date from = (raw_from !=null && !raw_from.isBlank())?Date.valueOf(raw_from):null;
        Date to = (raw_to !=null && !raw_to.isBlank())?Date.valueOf(raw_to):null;
        Integer did = (raw_did != null && !raw_did.equals("-1"))?Integer.parseInt(raw_did):null;     
        EmployeeDBContext dbEmp = new EmployeeDBContext();
        DepartmentDBContext dbDept = new DepartmentDBContext();
        ArrayList<Employee> emps = dbEmp.search(id, name, gender, address, from, to, did);
        request.setAttribute("emps", emps);      
        ArrayList<Department> depts = dbDept.list();
        request.setAttribute("depts", depts);       
        request.getRequestDispatcher("../view/employee/filter.jsp").forward(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
