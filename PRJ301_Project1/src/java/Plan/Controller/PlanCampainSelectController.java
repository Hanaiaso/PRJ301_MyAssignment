/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Plan.Controller;

import Plan.Entity.PlanCampain;
import dal.PlanCampainDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author LEGION
 */
public class PlanCampainSelectController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int plid = Integer.parseInt(request.getParameter("plid"));
        PlanCampainDBContext planCampainDB = new PlanCampainDBContext();
        List<PlanCampain> planCampains = planCampainDB.getCampainsByPlanId(plid);

        // Gửi danh sách chiến dịch sang JSP
        request.setAttribute("planCampains", planCampains);
        request.setAttribute("plid", plid);
        request.getRequestDispatcher("../view/plan/select_campain.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int plcid = Integer.parseInt(request.getParameter("plcid"));
        response.sendRedirect("../schedule/select?plcid=" + plcid);
    }
}
