/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Progress.Controller;

import Employee.Entity.Department;
import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;
import Plan.Entity.Plan;
import Progress.Entity.Progress;
import com.google.gson.Gson;
import dal.DepartmentDBContext;
import dal.PlanDBContext;
import dal.ProgressDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class ProgressController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        PlanDBContext planDB = new PlanDBContext();
        ArrayList<Plan> plans = planDB.list();
        req.setAttribute("plans", plans);

        String planIdParam = req.getParameter("planId");
        if (planIdParam != null && !planIdParam.isEmpty()) {
            int planId = Integer.parseInt(planIdParam);
            ProgressDBContext progressDB = new ProgressDBContext();
            Progress progress = progressDB.getProgressByPlanId(planId);

            if (progress != null) {
                req.setAttribute("progress", progress);
                req.setAttribute("currentDate", new Date());
            } else {
                req.setAttribute("error", "No progress data found for the selected plan.");
            }
        }

        req.getRequestDispatcher("/view/schedule/progress.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
