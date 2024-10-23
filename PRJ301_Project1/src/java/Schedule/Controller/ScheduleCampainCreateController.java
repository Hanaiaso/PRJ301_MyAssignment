/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Schedule.Controller;

import Plan.Entity.Plan;
import Plan.Entity.PlanCampain;
import Schedule.Entity.ScheduleCampain;
import dal.PlanCampainDBContext;
import dal.PlanDBContext;
import dal.ScheduleCampainDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LEGION
 */
public class ScheduleCampainCreateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         // Lấy plid và plcid từ request
        int plid = Integer.parseInt(request.getParameter("plid"));
        int plcid = Integer.parseInt(request.getParameter("plcid"));

        // Lấy thông tin kế hoạch từ database
        PlanDBContext planDB = new PlanDBContext();
        Plan plan = planDB.get(plid);
        if (plan == null) {
            response.getWriter().println("Không tìm thấy kế hoạch với plid: " + plid);
            return;
        }

        // Lấy thông tin chiến dịch từ database
        PlanCampainDBContext planCampainDB = new PlanCampainDBContext();
        PlanCampain planCampain = planCampainDB.get(plcid);
        if (planCampain == null) {
            response.getWriter().println("Không tìm thấy chiến dịch với plcid: " + plcid);
            return;
        }

        // Kiểm tra nếu lịch đã được tạo cho PlanCampain này
        ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();
        boolean scheduleCreated = scheduleDB.isScheduleCreated(plcid);

        if (scheduleCreated) {
            // Nếu lịch đã được tạo, chỉ hiển thị thông báo và nút quay lại
            request.setAttribute("message", "Lịch đã được tạo cho chiến dịch này.");
            request.getRequestDispatcher("../view/schedule/error.jsp").forward(request, response);
            return; // Ngừng xử lý tiếp nếu đã có lịch
        }

        // Tạo danh sách các ngày từ StartDate đến EndDate
        LocalDate startDate = plan.getStart().toLocalDate(); // Chuyển đổi Date -> LocalDate
        LocalDate endDate = plan.getEnd().toLocalDate();
        List<LocalDate> dates = new ArrayList<>();

        while (!startDate.isAfter(endDate)) {
            dates.add(startDate);
            startDate = startDate.plusDays(1); // Tăng ngày thêm 1
        }

        // Gửi thông tin kế hoạch, chiến dịch và sản phẩm sang JSP
        request.setAttribute("plan", plan);
        request.setAttribute("planCampain", planCampain);
        request.setAttribute("productName", planCampain.getProduct().getName()); // Gửi tên sản phẩm
        request.setAttribute("quantity", planCampain.getQuantity()); // Tổng số lượng sản phẩm
        request.setAttribute("dates", dates); // Danh sách các ngày

        // Chuyển sang trang detail.jsp để nhập thông tin chi tiết
        request.getRequestDispatcher("../view/schedule/create.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Lấy thông tin từ form
        int plcid = Integer.parseInt(request.getParameter("plcid"));
        PlanCampainDBContext planCampainDB = new PlanCampainDBContext();
        PlanCampain planCampain = planCampainDB.get(plcid);

        ScheduleCampainDBContext scDB = new ScheduleCampainDBContext();

        // Lấy tổng số lượng sản phẩm từ chiến dịch
        int totalQuantity = planCampain.getQuantity();
        int totalInputQuantity = 0; // Tổng số lượng mà người dùng đã nhập

        // Lấy danh sách các ngày từ request
        String[] dates = request.getParameterValues("date");

        // Tạo đối tượng ScheduleCampain cho từng ngày và từng ca
        for (String date : dates) {
            for (int shift = 1; shift <= 3; shift++) {
                String quantityShift = request.getParameter("quantity" + date + "Shift" + shift);
                if (quantityShift != null && !quantityShift.isEmpty()) {
                    int quantity = Integer.parseInt(quantityShift);
                    totalInputQuantity += quantity; // Cộng dồn số lượng nhập cho tất cả các ngày

                    // Kiểm tra xem tổng số lượng có vượt quá giới hạn không
                    if (totalInputQuantity > totalQuantity) {
                        request.setAttribute("error", "Tổng số lượng đã vượt quá giới hạn cho phép (" + totalQuantity + ")");
                        request.getRequestDispatcher("../view/schedule/create.jsp").forward(request, response);
                        return; // Ngừng xử lý nếu vượt quá giới hạn
                    }

                    // Tạo mới đối tượng ScheduleCampain và thiết lập các thuộc tính
                    ScheduleCampain scheduleCampain = new ScheduleCampain();
                    scheduleCampain.setPlancampain(planCampain); // Gán đối tượng PlanCampain
                    scheduleCampain.setDate(Date.valueOf(date)); // Thiết lập ngày
                    scheduleCampain.setShift(shift); // Thiết lập ca làm việc
                    scheduleCampain.setQuantity(quantity); // Thiết lập số lượng

                    // Lưu dữ liệu vào cơ sở dữ liệu
                    scDB.insert(scheduleCampain);
                }
            }
        }

        // Quay lại trang danh sách kế hoạch sau khi lưu thành công
     
        response.sendRedirect("../plan/list");
    }

}
