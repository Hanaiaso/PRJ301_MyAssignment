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
        
        // Lấy danh sách các ScheduleCampain từ cơ sở dữ liệu bằng phương thức list()
        ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();
        ArrayList<ScheduleCampain> schedules = scheduleDB.list();

        // Kiểm tra danh sách có phần tử nào không
        System.out.println("Total schedules from servlet: " + schedules.size());

        // Gửi danh sách sang JSP để hiển thị
        request.setAttribute("schedules", schedules);
        request.getRequestDispatcher("../view/schedule/list.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
    }

   
}
