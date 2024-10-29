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
public class DepartmentCreateController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String name = request.getParameter("dname");
        String type = request.getParameter("dtype");

        // Tạo đối tượng Department và thiết lập các giá trị
        Department dept = new Department();
        dept.setName(name);
        dept.setType(type);

        // Lưu department vào cơ sở dữ liệu
        DepartmentDBContext ddb = new DepartmentDBContext();
        ddb.insert(dept);

        // Chuyển hướng về danh sách departments
        response.sendRedirect("list");  // Giả định rằng URL "list" sẽ dẫn tới trang danh sách departments
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("list");
    }
}
