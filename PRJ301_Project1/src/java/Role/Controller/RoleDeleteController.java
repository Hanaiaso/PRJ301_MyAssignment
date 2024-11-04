/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Role.Controller;

import Login.Controller.BaseRBACCOntroller;
import Login.Entity.Role;
import Login.Entity.User;
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
public class RoleDeleteController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        String roleIdParam = req.getParameter("id");
        if (roleIdParam != null && !roleIdParam.isEmpty()) {
            try {
                int roleId = Integer.parseInt(roleIdParam);
                RoleDBContext roleDB = new RoleDBContext();
                Role role = roleDB.get(roleId);

                if (role != null) {
                    roleDB.delete(role);
                }

                // Redirect to the list page after deletion
                resp.sendRedirect("list");
            } catch (NumberFormatException e) {
                resp.getWriter().println("Invalid role ID format.");
            }
        } else {
            resp.getWriter().println("Role ID is required for deletion.");
        }
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
