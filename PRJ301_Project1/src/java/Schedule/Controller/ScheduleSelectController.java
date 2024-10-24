/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Schedule.Controller;

import Schedule.Entity.ScheduleCampain;
import dal.ScheduleCampainDBContext;
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
public class ScheduleSelectController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int plcid = Integer.parseInt(request.getParameter("plcid"));
        ScheduleCampainDBContext scDB = new ScheduleCampainDBContext();
        ArrayList<ScheduleCampain> schedules = scDB.getSchedulesByPlanCampainId(plcid);

        request.setAttribute("schedules", schedules);
        request.setAttribute("plcid", plcid);
        request.getRequestDispatcher("../view/plan/select_schedule.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int scid = Integer.parseInt(request.getParameter("scid"));
        response.sendRedirect("../scheduleemployee/create?scid=" + scid);
    }
}
