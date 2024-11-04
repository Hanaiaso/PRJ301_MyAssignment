/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package User.Controller;

import Login.Controller.BaseRBACCOntroller;
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
public class UserDeleteController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        UserDBContext udb = new UserDBContext();
        String userId = req.getParameter("username");

        if (userId != null && !userId.isEmpty()) {
            User user = udb.get(userId);
            if (user != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                Gson gson = new Gson();
                resp.getWriter().print(gson.toJson(user));
                return;
            }
        }

        ArrayList<User> userList = udb.list();
        ArrayList<Role> rolesList = new RoleDBContext().list(); // Thêm phần này để lấy danh sách các vai trò
        req.setAttribute("userList", userList);
        req.setAttribute("roles", rolesList);
        req.getRequestDispatcher("/view/user/user_delete.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        // Đọc JSON từ yêu cầu
        StringBuilder json = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        }

        // Chuyển đổi từ JSON sang đối tượng User
        Gson gson = new Gson();
        User user = gson.fromJson(json.toString(), User.class);

        // Xóa người dùng (cập nhật isWork thành 0)
        UserDBContext udb = new UserDBContext();
        udb.delete(user);

        // Trả về phản hồi thành công
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print("{\"status\": \"success\"}");
    }

}
