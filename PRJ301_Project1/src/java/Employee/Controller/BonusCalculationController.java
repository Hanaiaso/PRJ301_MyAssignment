/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Employee.Controller;

import Employee.Entity.Employee;
import Plan.Entity.Plan;
import dal.AttendanceDBContext;
import dal.PlanDBContext;
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
public class BonusCalculationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Fetch list of all available plans to display
        PlanDBContext planDB = new PlanDBContext();
        ArrayList<Plan> plans = planDB.list();
        request.setAttribute("plans", plans);

        // Get selected planId parameter
        String planIdParam = request.getParameter("planId");
        if (planIdParam != null && !planIdParam.isEmpty()) {
            int planId = Integer.parseInt(planIdParam);

            // Calculate the employee bonuses for the selected plan
            AttendanceDBContext attendanceDB = new AttendanceDBContext();
            ArrayList<Employee> employees = attendanceDB.getEmployeeBonusForPlan(planId);

            // Set attributes to be used by JSP
            request.setAttribute("selectedPlanId", planId);
            request.setAttribute("employees", employees);
        }

        // Forward to JSP to display the results
        request.getRequestDispatcher("/view/employee/bonus_list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
