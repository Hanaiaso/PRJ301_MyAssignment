/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Schedule.Controller;

import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;
import Schedule.Entity.ScheduleEmployee;
import dal.ScheduleEmployeeDBContext;
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
public class ScheduleEmployeeListController extends BaseRBACCOntroller {

    @Override

    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        ScheduleEmployeeDBContext seDB = new ScheduleEmployeeDBContext();
        ArrayList<ScheduleEmployee> assignedEmployees = seDB.getAllAssignedEmployees();

        // Gửi dữ liệu sang JSP
        req.setAttribute("assignedEmployees", assignedEmployees);
        req.getRequestDispatcher("../view/schedule/ssigned_employee_list.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
