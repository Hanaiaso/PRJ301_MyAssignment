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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleCampainListController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();
        ArrayList<ScheduleCampain> schedules = scheduleDB.list();
        System.out.println("Total schedules from servlet: " + schedules.size());
        req.setAttribute("schedules", schedules);
        req.getRequestDispatcher("../view/schedule/list.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
