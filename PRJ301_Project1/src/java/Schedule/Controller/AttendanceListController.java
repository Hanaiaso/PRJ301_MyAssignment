/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Schedule.Controller;

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
public class AttendanceListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Khởi tạo AttendanceDBContext để lấy danh sách Attendance
        AttendanceDBContext attendanceDB = new AttendanceDBContext();
        // Lấy danh sách tất cả các bản ghi attendance
        ArrayList<Attendance> attendances = attendanceDB.list();
      
        // Đặt danh sách attendance vào request để truyền cho JSP
        request.setAttribute("attendances", attendances);
        request.getRequestDispatcher("../view/schedule/attendance_list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
