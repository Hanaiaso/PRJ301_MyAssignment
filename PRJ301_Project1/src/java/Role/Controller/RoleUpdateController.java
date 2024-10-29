/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Role.Controller;

import Login.Entity.Feature;
import Login.Entity.Role;
import dal.FeatureDBContext;
import dal.RoleDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author LEGION
 */
public class RoleUpdateController extends HttpServlet {
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roleIdParam = request.getParameter("id");
        if (roleIdParam != null) {
            int roleId = Integer.parseInt(roleIdParam);

            RoleDBContext roleDB = new RoleDBContext();
            FeatureDBContext featureDB = new FeatureDBContext();

            Role role = roleDB.get(roleId);
            ArrayList<Feature> allFeatures = featureDB.list();

            request.setAttribute("role", role);
            request.setAttribute("allFeatures", allFeatures);
        }

        request.getRequestDispatcher("/view/role/role_update.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        String roleName = request.getParameter("roleName");
        String[] featureIds = request.getParameterValues("features");

        Role role = new Role();
        role.setId(roleId);
        role.setName(roleName);

        if (featureIds != null) {
            ArrayList<Feature> features = new ArrayList<>();
            for (String featureId : featureIds) {
                Feature feature = new Feature();
                feature.setId(Integer.parseInt(featureId));
                features.add(feature);
            }
            role.setFeatures(features);
        }

        RoleDBContext roleDB = new RoleDBContext();
        roleDB.update(role);

        response.sendRedirect("list"); // Redirect back to the role list page after updating
    }
}
