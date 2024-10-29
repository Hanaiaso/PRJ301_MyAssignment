/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Department.Controller;

import Employee.Entity.Department;
import dal.DepartmentDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author LEGION
 */
public class DepartmentUpdateController extends HttpServlet {

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String departmentIdParam = request.getParameter("id");
        if (departmentIdParam != null && !departmentIdParam.isEmpty()) {
            int departmentId = Integer.parseInt(departmentIdParam);

            DepartmentDBContext ddb = new DepartmentDBContext();
            Department department = ddb.get(departmentId);
            if (department != null) {
                request.setAttribute("department", department);
                request.getRequestDispatcher("/view/department/department_update.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String name = request.getParameter("dname");
        String type = request.getParameter("dtype");

        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);

            Department department = new Department();
            department.setId(id);
            department.setName(name);
            department.setType(type);

            DepartmentDBContext ddb = new DepartmentDBContext();
            ddb.update(department);

            response.sendRedirect("list");
        }
    }
}
