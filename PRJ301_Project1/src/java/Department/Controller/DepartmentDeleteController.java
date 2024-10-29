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
public class DepartmentDeleteController extends HttpServlet {

      @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String departmentIdParam = request.getParameter("id");
        if (departmentIdParam != null && !departmentIdParam.isEmpty()) {
            int departmentId = Integer.parseInt(departmentIdParam);

            // Tạo đối tượng Department với ID để xóa
            Department department = new Department();
            department.setId(departmentId);

            // Gọi phương thức delete để cập nhật isWork thành 0
            DepartmentDBContext ddb = new DepartmentDBContext();
            ddb.delete(department);
        }

        // Chuyển hướng về danh sách departments
        response.sendRedirect("list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
