/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Schedule.Controller;

import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;
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
public class ScheduleSelectController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {

        int plcid = Integer.parseInt(req.getParameter("plcid"));
        ScheduleCampainDBContext scDB = new ScheduleCampainDBContext();
        ArrayList<ScheduleCampain> schedules = scDB.getSchedulesByPlanCampainId(plcid);

        req.setAttribute("schedules", schedules);
        req.setAttribute("plcid", plcid);
        req.getRequestDispatcher("../view/plan/select_schedule.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        int scid = Integer.parseInt(req.getParameter("scid"));
        resp.sendRedirect("../scheduleemployee/create?scid=" + scid);
    }

}
