/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Role.Controller;

import Login.Entity.Role;
import dal.RoleDBContext;
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
public class RoleDeleteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roleIdParam = request.getParameter("id");
        if (roleIdParam != null && !roleIdParam.isEmpty()) {
            int roleId = Integer.parseInt(roleIdParam);

            RoleDBContext roleDB = new RoleDBContext();
            Role role = roleDB.get(roleId);

            if (role != null) {
                roleDB.delete(role);
            }
        }

        // Redirect to the list page after deletion
        response.sendRedirect("list");
    }
}
