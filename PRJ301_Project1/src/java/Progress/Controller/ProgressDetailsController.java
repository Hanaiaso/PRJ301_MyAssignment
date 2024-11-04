/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Progress.Controller;

import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;
import Plan.Entity.Plan;
import Progress.Entity.ProductProgress;
import dal.PlanDBContext;
import dal.ProgressDBContext;
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
public class ProgressDetailsController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        String planIdParam = req.getParameter("planId");
        if (planIdParam != null && !planIdParam.isEmpty()) {
            int planId = Integer.parseInt(planIdParam);

            PlanDBContext planDB = new PlanDBContext();
            Plan plan = planDB.get(planId);
            req.setAttribute("plan", plan);

            ProgressDBContext progressDB = new ProgressDBContext();
            ArrayList<ProductProgress> productProgresses = progressDB.getProductProgressByPlanId(planId);
            req.setAttribute("productProgresses", productProgresses);
        }

        req.getRequestDispatcher("/view/schedule/progressDetails.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
