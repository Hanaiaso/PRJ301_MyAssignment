/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package User.Controller;

import Login.Entity.Role;
import Login.Entity.User;
import com.google.gson.Gson;
import dal.RoleDBContext;
import dal.UserDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author LEGION
 */
public class UserCreateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách roles để hiển thị cho người dùng chọn
        RoleDBContext roleDB = new RoleDBContext();
        ArrayList<Role> roles = roleDB.list();
        request.setAttribute("roles", roles);

        // Chuyển tiếp đến JSP để nhập thông tin người dùng
        request.getRequestDispatcher("/view/user/user_create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Đọc JSON từ yêu cầu
        StringBuilder json = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }

        // Chuyển đổi từ JSON sang đối tượng User
        Gson gson = new Gson();
        User user = gson.fromJson(json.toString(), User.class);

        // Thêm người dùng mới vào cơ sở dữ liệu
        UserDBContext udb = new UserDBContext();
        udb.insert(user);

        // Trả về phản hồi thành công
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("{\"status\": \"success\"}");
    }



}
