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
public class DepartmentCreateController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        resp.sendRedirect("list");
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String name = req.getParameter("dname");
        String type = req.getParameter("dtype");

        // Tạo đối tượng Department và thiết lập các giá trị
        Department dept = new Department();
        dept.setName(name);
        dept.setType(type);

        // Lưu department vào cơ sở dữ liệu
        DepartmentDBContext ddb = new DepartmentDBContext();
        ddb.insert(dept);

        // Chuyển hướng về danh sách departments
        resp.sendRedirect("list");  // Giả định rằng URL "list" sẽ dẫn tới trang danh sách departments    }
    }
}
