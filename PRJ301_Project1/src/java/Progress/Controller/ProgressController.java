/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Progress.Controller;

import Plan.Entity.Plan;
import Progress.Entity.Progress;
import com.google.gson.Gson;
import dal.PlanDBContext;
import dal.ProgressDBContext;
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
public class ProgressController extends HttpServlet {

   @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    // Lấy danh sách kế hoạch từ DBContext
    PlanDBContext planDB = new PlanDBContext();
    ArrayList<Plan> plans = planDB.list();
    request.setAttribute("plans", plans);

    String planIdParam = request.getParameter("planId");
    if (planIdParam != null && !planIdParam.isEmpty()) {
        int planId = Integer.parseInt(planIdParam);
        ProgressDBContext progressDB = new ProgressDBContext();
        ArrayList<Progress> progresses = progressDB.getDetailedProgressByPlanId(planId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        try (PrintWriter out = response.getWriter()) {
            out.print(gson.toJson(progresses));
            out.flush();
        }
    } else {
        request.getRequestDispatcher("/view/schedule/progress.jsp").forward(request, response);
    }
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Không cần xử lý POST cho chức năng này
    }

//    // Lớp để lưu thông tin tiến độ kế hoạch (sử dụng cho việc chuyển đổi thành JSON)
//    private class PlanProgressResponse {
//
//        private String planName;
//        private int totalProducts;
//        private int completedProducts;
//        private int remainingProducts;
//
//        // Getters and setters
//        public String getPlanName() {
//            return planName;
//        }
//
//        public void setPlanName(String planName) {
//            this.planName = planName;
//        }
//
//        public int getTotalProducts() {
//            return totalProducts;
//        }
//
//        public void setTotalProducts(int totalProducts) {
//            this.totalProducts = totalProducts;
//        }
//
//        public int getCompletedProducts() {
//            return completedProducts;
//        }
//
//        public void setCompletedProducts(int completedProducts) {
//            this.completedProducts = completedProducts;
//        }
//
//        public int getRemainingProducts() {
//            return remainingProducts;
//        }
//
//        public void setRemainingProducts(int remainingProducts) {
//            this.remainingProducts = remainingProducts;
//        }
//    }
}
