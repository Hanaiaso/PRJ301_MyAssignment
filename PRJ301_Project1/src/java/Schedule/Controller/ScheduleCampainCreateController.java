package Schedule.Controller;

import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;
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

public class ScheduleCampainCreateController extends BaseRBACCOntroller {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        int plid = Integer.parseInt(req.getParameter("plid"));
        int plcid = Integer.parseInt(req.getParameter("plcid"));
        PlanDBContext planDB = new PlanDBContext();
        Plan plan = planDB.get(plid);
        if (plan == null) {
            resp.getWriter().println("Không tìm thấy kế hoạch với plid: " + plid);
            return;
        }
        PlanCampainDBContext planCampainDB = new PlanCampainDBContext();
        PlanCampain planCampain = planCampainDB.get(plcid);
        if (planCampain == null) {
            resp.getWriter().println("Không tìm thấy chiến dịch với plcid: " + plcid);
            return;
        }

        ScheduleCampainDBContext scheduleDB = new ScheduleCampainDBContext();
        List<ScheduleCampain> existingSchedules = scheduleDB.getSchedulesByPlanCampainId(plcid);

        // Tính tổng số lượng đã nhập
        int totalInputQuantity = existingSchedules.stream().mapToInt(ScheduleCampain::getQuantity).sum();

        LocalDate startDate = plan.getStart().toLocalDate();
        LocalDate endDate = plan.getEnd().toLocalDate();
        List<LocalDate> dates = new ArrayList<>();
        while (!startDate.isAfter(endDate)) {
            dates.add(startDate);
            startDate = startDate.plusDays(1);
        }

        req.setAttribute("plan", plan);
        req.setAttribute("planCampain", planCampain);
        req.setAttribute("productName", planCampain.getProduct().getName());
        req.setAttribute("quantity", planCampain.getQuantity());
        req.setAttribute("dates", dates);
        req.setAttribute("existingSchedules", existingSchedules);
        req.setAttribute("totalInputQuantity", totalInputQuantity);  // Tổng số lượng đã nhập
        req.getRequestDispatcher("../view/schedule/create.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        int plcid = Integer.parseInt(req.getParameter("plcid"));
        PlanCampainDBContext planCampainDB = new PlanCampainDBContext();
        PlanCampain planCampain = planCampainDB.get(plcid);
        ScheduleCampainDBContext scDB = new ScheduleCampainDBContext();
        int totalQuantity = planCampain.getQuantity();
        int totalInputQuantity = scDB.getTotalScheduledQuantity(plcid);
        String[] dates = req.getParameterValues("date");

        for (String date : dates) {
            for (int shift = 1; shift <= 3; shift++) {
                String quantityShift = req.getParameter("quantity" + date + "Shift" + shift);
                int quantity = (quantityShift == null || quantityShift.isEmpty()) ? 0 : Integer.parseInt(quantityShift);

                totalInputQuantity += quantity;
                if (totalInputQuantity > totalQuantity) {
                    req.setAttribute("error", "Tổng số lượng đã vượt quá giới hạn cho phép (" + totalQuantity + ")");
                    req.getRequestDispatcher("../view/schedule/create.jsp").forward(req, resp);
                    return;
                }

                ScheduleCampain scheduleCampain = scDB.getScheduleByDateAndShift(plcid, Date.valueOf(date), shift);
                if (scheduleCampain == null) {
                    // Nếu chưa tồn tại, thêm mới
                    scheduleCampain = new ScheduleCampain();
                    scheduleCampain.setPlancampain(planCampain);
                    scheduleCampain.setDate(Date.valueOf(date));
                    scheduleCampain.setShift(shift);
                    scheduleCampain.setQuantity(quantity);
                    scDB.insert(scheduleCampain);
                } else {
                    // Nếu đã tồn tại, cập nhật
                    scheduleCampain.setQuantity(quantity);
                    scDB.update(scheduleCampain);
                }
            }
        }
        resp.sendRedirect("../plan/list");
    }

}
