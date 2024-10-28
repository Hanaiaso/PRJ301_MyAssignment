/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Progress.Controller;

import Employee.Entity.Department;
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

public class ProgressController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PlanDBContext planDB = new PlanDBContext();
        ArrayList<Plan> plans = planDB.list();
        request.setAttribute("plans", plans);

        String planIdParam = request.getParameter("planId");
        if (planIdParam != null && !planIdParam.isEmpty()) {
            int planId = Integer.parseInt(planIdParam);
            ProgressDBContext progressDB = new ProgressDBContext();
            Progress progress = progressDB.getProgressByPlanId(planId);

            if (progress != null) {
                request.setAttribute("progress", progress);
                request.setAttribute("currentDate", new Date());
            } else {
                request.setAttribute("error", "No progress data found for the selected plan.");
            }
        }

        request.getRequestDispatcher("/view/schedule/progress.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
