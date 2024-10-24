package Schedule.Controller;
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
public class ScheduleCampainListController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {       
    } 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();
        ArrayList<ScheduleCampain> schedules = scheduleDB.list();
        System.out.println("Total schedules from servlet: " + schedules.size());
        request.setAttribute("schedules", schedules);
        request.getRequestDispatcher("../view/schedule/list.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {       
    }
}
