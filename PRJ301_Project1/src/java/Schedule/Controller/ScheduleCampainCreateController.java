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
public class ScheduleCampainCreateController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int plid = Integer.parseInt(request.getParameter("plid"));
        int plcid = Integer.parseInt(request.getParameter("plcid"));
        PlanDBContext planDB = new PlanDBContext();
        Plan plan = planDB.get(plid);
        if (plan == null) {
            response.getWriter().println("Không tìm thấy kế hoạch với plid: " + plid);
            return;
        }
        PlanCampainDBContext planCampainDB = new PlanCampainDBContext();
        PlanCampain planCampain = planCampainDB.get(plcid);
        if (planCampain == null) {
            response.getWriter().println("Không tìm thấy chiến dịch với plcid: " + plcid);
            return;
        }
        ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();
        boolean scheduleCreated = scheduleDB.isScheduleCreated(plcid);
        if (scheduleCreated) {
            request.setAttribute("message", "Lịch đã được tạo cho chiến dịch này.");
            request.getRequestDispatcher("../view/schedule/error.jsp").forward(request, response);
            return; 
        }
        LocalDate startDate = plan.getStart().toLocalDate(); 
        LocalDate endDate = plan.getEnd().toLocalDate();
        List<LocalDate> dates = new ArrayList<>();
        while (!startDate.isAfter(endDate)) {
            dates.add(startDate);
            startDate = startDate.plusDays(1); 
        }
        request.setAttribute("plan", plan);
        request.setAttribute("planCampain", planCampain);
        request.setAttribute("productName", planCampain.getProduct().getName()); 
        request.setAttribute("quantity", planCampain.getQuantity());
        request.setAttribute("dates", dates); 
        request.getRequestDispatcher("../view/schedule/create.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int plcid = Integer.parseInt(request.getParameter("plcid"));
        PlanCampainDBContext planCampainDB = new PlanCampainDBContext();
        PlanCampain planCampain = planCampainDB.get(plcid);
        ScheduleCampainDBContext scDB = new ScheduleCampainDBContext();
        int totalQuantity = planCampain.getQuantity();
        int totalInputQuantity = 0; 
        String[] dates = request.getParameterValues("date");
        for (String date : dates) {
            for (int shift = 1; shift <= 3; shift++) {
                String quantityShift = request.getParameter("quantity" + date + "Shift" + shift);
                if (quantityShift != null && !quantityShift.isEmpty()) {
                    int quantity = Integer.parseInt(quantityShift);
                    totalInputQuantity += quantity; 
                    if (totalInputQuantity > totalQuantity) {
                        request.setAttribute("error", "Tổng số lượng đã vượt quá giới hạn cho phép (" + totalQuantity + ")");
                        request.getRequestDispatcher("../view/schedule/create.jsp").forward(request, response);
                        return; 
                    }
                    ScheduleCampain scheduleCampain = new ScheduleCampain();
                    scheduleCampain.setPlancampain(planCampain); 
                    scheduleCampain.setDate(Date.valueOf(date));
                    scheduleCampain.setShift(shift); 
                    scheduleCampain.setQuantity(quantity); 
                    scDB.insert(scheduleCampain);
                }
            }
        }
        response.sendRedirect("../plan/list");
    }
}
