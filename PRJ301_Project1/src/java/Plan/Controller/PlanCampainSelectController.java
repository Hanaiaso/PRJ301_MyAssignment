/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Plan.Controller;

import Login.Controller.BaseRBACCOntroller;
import Login.Entity.User;
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
public class PlanCampainSelectController extends BaseRBACCOntroller {

    @Override
     protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {

        int plid = Integer.parseInt(req.getParameter("plid"));
        PlanCampainDBContext planCampainDB = new PlanCampainDBContext();
        List<PlanCampain> planCampains = planCampainDB.getCampainsByPlanId(plid);

        // Gửi danh sách chiến dịch sang JSP
        req.setAttribute("planCampains", planCampains);
        req.setAttribute("plid", plid);
        req.getRequestDispatcher("../view/plan/select_campain.jsp").forward(req, resp);
    }

    @Override
     protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        int plcid = Integer.parseInt(req.getParameter("plcid"));
        resp.sendRedirect("../schedule/select?plcid=" + plcid);
    }

    
}
