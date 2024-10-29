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
public class RoleCreateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FeatureDBContext featureDB = new FeatureDBContext();
        ArrayList<Feature> features = featureDB.list();
        request.setAttribute("features", features);
        request.getRequestDispatcher("/view/role/role_create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roleName = request.getParameter("rname");
        String[] selectedFeatures = request.getParameterValues("features");

        Role role = new Role();
        role.setName(roleName);

        if (selectedFeatures != null) {
            ArrayList<Feature> features = new ArrayList<>();
            for (String featureId : selectedFeatures) {
                Feature feature = new Feature();
                feature.setId(Integer.parseInt(featureId));
                features.add(feature);
            }
            role.setFeatures(features);
        }

        RoleDBContext roleDB = new RoleDBContext();
        roleDB.insert(role);

        response.sendRedirect("list");  // Redirect to the role list page after successful creation
    }
}
