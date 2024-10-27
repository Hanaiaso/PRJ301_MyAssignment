package dal;

import Plan.Entity.Plan;
import Progress.Entity.Progress;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ProgressDBContext extends DBContext<Progress> {

    public Progress getProgressByPlanId(int planId) {
        Progress progress = new Progress();
        Plan plan = getPlanById(planId);

        if (plan == null) {
            return null; // Kế hoạch không tồn tại
        }

        progress.setPlan(plan);

        // Tính tổng số sản phẩm đã giao
        int totalProducts = getTotalProducts(planId);
        progress.setTotalProducts(totalProducts);

        // Tính tổng số sản phẩm đã hoàn thành
        int completedProducts = getCompletedProducts(planId);
        progress.setCompletedProducts(completedProducts);

        // Tính số sản phẩm còn lại
        int remainingProducts = totalProducts - completedProducts;
        progress.setRemainingProducts(remainingProducts);

        // Xác định trạng thái của kế hoạch
        Date today = new Date();
        if (completedProducts == totalProducts) {
            progress.setStatus("Hoàn thành");
        } else if (today.before(plan.getEnd())) {
            progress.setStatus("Đang tiến hành");
        } else {
            progress.setStatus("Muộn");
        }

        return progress;
    }

    // Lấy kế hoạch bằng ID
    public Plan getPlanById(int planId) {
        String sql = "SELECT plid, plname, startDate, endDate FROM [Plan] WHERE plid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, planId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Plan plan = new Plan();
                    plan.setId(rs.getInt("plid"));
                    plan.setName(rs.getString("plname"));
                    plan.setStart(rs.getDate("startDate"));
                    plan.setEnd(rs.getDate("endDate"));
                    return plan;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Tính tổng số sản phẩm đã giao cho kế hoạch bằng cách lặp qua các kết quả
    private int getTotalProducts(int planId) {
        String sql = "SELECT quantity FROM PlanCampain WHERE plid = ?";
        int totalProducts = 0;
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, planId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    totalProducts += rs.getInt("quantity");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return totalProducts;
    }

    // Tính tổng số sản phẩm đã hoàn thành bằng cách lặp qua các kết quả
    private int getCompletedProducts(int planId) {
        String sql = "SELECT a.Quantity FROM Attendence a "
                   + "JOIN SchedualEmployee se ON a.seid = se.seid "
                   + "JOIN SchedualCampaign sc ON se.scid = sc.scid "
                   + "JOIN PlanCampain pc ON sc.plcid = pc.plcid "
                   + "WHERE pc.plid = ?";
        int completedProducts = 0;
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, planId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    completedProducts += rs.getInt("Quantity");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return completedProducts;
    }

    @Override
    public void insert(Progress entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Progress entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Progress entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Progress> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Progress get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
