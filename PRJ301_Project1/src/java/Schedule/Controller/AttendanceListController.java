/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Schedule.Controller;

import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;
import Schedule.Entity.Attendance;
import dal.AttendanceDBContext;
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
public class AttendanceListController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        // Khởi tạo AttendanceDBContext để lấy danh sách Attendance
        AttendanceDBContext attendanceDB = new AttendanceDBContext();
        // Lấy danh sách tất cả các bản ghi attendance
        ArrayList<Attendance> attendances = attendanceDB.list();

        // Đặt danh sách attendance vào request để truyền cho JSP
        req.setAttribute("attendances", attendances);
        req.getRequestDispatcher("../view/schedule/attendance_list.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
