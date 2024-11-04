/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Department.Controller;

import Employee.Entity.Department;
import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;
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
public class DepartmentUpdateController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        String departmentIdParam = req.getParameter("id");
        if (departmentIdParam != null && !departmentIdParam.isEmpty()) {
            int departmentId = Integer.parseInt(departmentIdParam);

            DepartmentDBContext ddb = new DepartmentDBContext();
            Department department = ddb.get(departmentId);
            if (department != null) {
                req.setAttribute("department", department);
                req.getRequestDispatcher("/view/department/department_update.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String name = req.getParameter("dname");
        String type = req.getParameter("dtype");

        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);

            Department department = new Department();
            department.setId(id);
            department.setName(name);
            department.setType(type);

            DepartmentDBContext ddb = new DepartmentDBContext();
            ddb.update(department);

            resp.sendRedirect("list");
        }
    }

}
