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
public class UserUpdateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDBContext udb = new UserDBContext();
        String userId = request.getParameter("username");

        if (userId != null && !userId.isEmpty()) {
            User user = udb.get(userId);
            if (user != null) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(user));
                return;
            }
        }

        ArrayList<User> userList = udb.list();
        ArrayList<Role> rolesList = new RoleDBContext().list(); // Thêm phần này để lấy danh sách các vai trò
        request.setAttribute("userList", userList);
        request.setAttribute("roles", rolesList);
        request.getRequestDispatcher("/view/user/user_update.jsp").forward(request, response);
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

        // Cập nhật thông tin người dùng trong cơ sở dữ liệu
        UserDBContext udb = new UserDBContext();
        udb.update(user);

        // Trả về phản hồi thành công
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("{\"status\": \"success\"}");
    }

}
