/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Role.Controller;

import Login.Controller.BaseRBACCOntroller;
import Login.Entity.Feature;
import Login.Entity.Role;
import Login.Entity.User;
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
public class RoleCreateController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        FeatureDBContext featureDB = new FeatureDBContext();
        ArrayList<Feature> features = featureDB.list();
        req.setAttribute("features", features);
        req.getRequestDispatcher("/view/role/role_create.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        String roleName = req.getParameter("rname");
        String[] selectedFeatures = req.getParameterValues("features");

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

        resp.sendRedirect("list");  // Redirect to the role list page after successful creation
    }

}
