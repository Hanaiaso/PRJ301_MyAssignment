/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Progress.Controller;

import Plan.Entity.Plan;
import Progress.Entity.Progress;
import com.google.gson.Gson;
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
            request.setAttribute("progress", progress);
            
            Plan progressdate = progressDB.getPlanById(planId);
            request.setAttribute("progressdate", progressdate);
            request.setAttribute("currentDate", new Date());
        }

        request.getRequestDispatcher("/view/schedule/progress.jsp").forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}
