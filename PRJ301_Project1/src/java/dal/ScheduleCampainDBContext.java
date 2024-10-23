/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import Plan.Entity.Plan;
import Plan.Entity.PlanCampain;
import Plan.Entity.Product;
import Schedule.Entity.ScheduleCampain;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LEGION
 */
public class ScheduleCampainDBContext extends DBContext<ScheduleCampain> {
        
    
    // Kiểm tra nếu đã có lịch được tạo cho PlanCampain với id là plcid
    public boolean isScheduleCreated(int plcid) {
        String sql = "SELECT COUNT(*) AS total FROM SchedualCampaign WHERE plcid = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, plcid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") > 0; // Nếu tổng số bản ghi lớn hơn 0 thì đã có lịch
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // Mặc định là chưa có lịch
    }
    
    @Override
    public void insert(ScheduleCampain entity) {
        String sql = "INSERT INTO [SchedualCampaign] ([plcid], [Date], [Shift], [Quantity]) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, entity.getPlancampain().getId());
            stm.setDate(2, entity.getDate());
            stm.setInt(3, entity.getShift());
            stm.setInt(4, entity.getQuantity());
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
 

    @Override
    public void update(ScheduleCampain entity
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(ScheduleCampain entity
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
public ArrayList<ScheduleCampain> list() {
    ArrayList<ScheduleCampain> schedules = new ArrayList<>();
    String sql = "SELECT sc.scid, sc.plcid, sc.Date, sc.Shift, sc.Quantity, " +
                 "pl.plid, pl.plname, pc.Quantity AS totalQuantity, p.pid, p.pname " +
                 "FROM SchedualCampaign sc " +
                 "LEFT JOIN PlanCampain pc ON sc.plcid = pc.plcid " +
                 "LEFT JOIN Product p ON pc.pid = p.pid " +
                 "LEFT JOIN [Plan] pl ON pc.plid = pl.plid " +
                 "WHERE pl.isDOne = 0 " +
                 "ORDER BY pc.plcid, sc.Date, sc.Shift";

    try {
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            // Tạo đối tượng Plan
            Plan plan = new Plan();
            plan.setId(rs.getInt("plid"));
            plan.setName(rs.getString("plname"));

            // Tạo đối tượng Product
            Product product = new Product();
            product.setId(rs.getInt("pid"));
            product.setName(rs.getString("pname"));

            // Tạo đối tượng PlanCampain
            PlanCampain planCampain = new PlanCampain();
            planCampain.setId(rs.getInt("plcid"));
            planCampain.setQuantity(rs.getInt("totalQuantity"));
            planCampain.setProduct(product);
            planCampain.setPlan(plan);

            // Tạo đối tượng ScheduleCampain
            ScheduleCampain schedule = new ScheduleCampain();
            schedule.setId(rs.getInt("scid"));
            schedule.setPlancampain(planCampain);
            schedule.setDate(rs.getDate("Date"));
            schedule.setShift(rs.getInt("Shift"));
            schedule.setQuantity(rs.getInt("Quantity"));

            // Thêm vào danh sách
            schedules.add(schedule);
        }

        // Kiểm tra danh sách có phần tử nào không
        System.out.println("Total schedules retrieved: " + schedules.size());

    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return schedules;
}


    @Override
    public ScheduleCampain get(int id
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
