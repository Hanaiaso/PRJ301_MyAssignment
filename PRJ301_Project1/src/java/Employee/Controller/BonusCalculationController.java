/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Employee.Controller;

import Employee.Entity.Employee;
import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;
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
public class BonusCalculationController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {

        // Fetch list of all available plans to display
        PlanDBContext planDB = new PlanDBContext();
        ArrayList<Plan> plans = planDB.list();
        req.setAttribute("plans", plans);

        // Get selected planId parameter
        String planIdParam = req.getParameter("planId");
        if (planIdParam != null && !planIdParam.isEmpty()) {
            int planId = Integer.parseInt(planIdParam);

            // Calculate the employee bonuses for the selected plan
            AttendanceDBContext attendanceDB = new AttendanceDBContext();
            ArrayList<Employee> employees = attendanceDB.getEmployeeBonusForPlan(planId);

            // Set attributes to be used by JSP
            req.setAttribute("selectedPlanId", planId);
            req.setAttribute("employees", employees);
        }

        // Forward to JSP to display the results
        req.getRequestDispatcher("/view/employee/bonus_list.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        doAuthorizedGet(req, resp, account);
    }

}
