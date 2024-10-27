package dal;

import Plan.Entity.Plan;
import Progress.Entity.Progress;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ProgressDBContext extends DBContext<Progress> {

    public int getTotalProductsByPlanId(int planId) {
        String sql = "SELECT SUM(quantity) AS totalProducts FROM PlanCampain WHERE plid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, planId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("totalProducts");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

   public ArrayList<Progress> getDetailedProgressByPlanId(int planId) {
    ArrayList<Progress> progresses = new ArrayList<>();
    String sql = "SELECT pl.plid, pl.plname, pc.pid, p.pname, pl.startDate, pl.endDate, "
            + "sc.Date AS progressDate, "
            + "SUM(pc.quantity) AS totalProducts, "
            + "SUM(ISNULL(a.quantity, 0)) AS completedProducts "
            + "FROM [Plan] pl "
            + "JOIN PlanCampain pc ON pl.plid = pc.plid "
            + "JOIN Product p ON pc.pid = p.pid "
            + "LEFT JOIN SchedualCampaign sc ON pc.plcid = sc.plcid "
            + "LEFT JOIN SchedualEmployee se ON sc.scid = se.scid "
            + "LEFT JOIN Attendence a ON se.seid = a.seid "
            + "WHERE pl.plid = ? "
            + "GROUP BY pl.plid, pl.plname, pc.pid, p.pname, pl.startDate, pl.endDate, sc.Date "
            + "ORDER BY sc.Date ASC";

    try (PreparedStatement stm = connection.prepareStatement(sql)) {
        stm.setInt(1, planId);
        try (ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                Plan plan = new Plan();
                plan.setId(rs.getInt("plid"));
                plan.setName(rs.getString("plname"));
                plan.setStart(rs.getDate("startDate"));
                plan.setEnd(rs.getDate("endDate"));

                Progress progress = new Progress();
                progress.setPlan(plan);
                progress.setProgressDate(rs.getDate("progressDate"));
                progress.setStartDate(rs.getDate("startDate"));
                progress.setEndDate(rs.getDate("endDate"));
                progress.setTotalProducts(rs.getInt("totalProducts"));
                progress.setCompletedProducts(rs.getInt("completedProducts"));
                progress.setRemainingProducts(progress.getTotalProducts() - progress.getCompletedProducts());

                // Gán trạng thái cho mỗi sản phẩm
                Date today = new Date();
                if (progress.getCompletedProducts() == progress.getTotalProducts()) {
                    progress.setStatus("Hoàn thành");
                } else if (today.before(progress.getEndDate())) {
                    progress.setStatus("Đang tiến hành");
                } else {
                    progress.setStatus("Trễ hạn");
                }

                progresses.add(progress);
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return progresses;
}


    public int getCompletedProductsByPlanId(int planId) {
        String sql = "SELECT SUM(a.Quantity) AS completedProducts FROM Attendence a "
                + "JOIN SchedualEmployee se ON a.seid = se.seid "
                + "JOIN SchedualCampaign sc ON se.scid = sc.scid "
                + "JOIN PlanCampain pc ON sc.plcid = pc.plcid "
                + "WHERE pc.plid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, planId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("completedProducts");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Progress> getProgressByPlanId(int planId) {
        ArrayList<Progress> progresses = new ArrayList<>();
        String sql = "SELECT pl.plid, pl.plname, pl.startDate, pl.endDate, "
                + "SUM(pc.quantity) AS totalProducts, "
                + "SUM(ISNULL(a.quantity, 0)) AS completedProducts "
                + "FROM [Plan] pl "
                + "JOIN PlanCampain pc ON pl.plid = pc.plid "
                + "LEFT JOIN SchedualCampaign sc ON pc.plcid = sc.plcid "
                + "LEFT JOIN SchedualEmployee se ON sc.scid = se.scid "
                + "LEFT JOIN Attendence a ON se.seid = a.seid "
                + "WHERE pl.plid = ? "
                + "GROUP BY pl.plid, pl.plname, pl.startDate, pl.endDate";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, planId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Plan plan = new Plan();
                    plan.setId(rs.getInt("plid"));
                    plan.setName(rs.getString("plname"));
                    plan.setStart(rs.getDate("startDate"));
                    plan.setEnd(rs.getDate("endDate"));

                    Progress progress = new Progress();
                    progress.setPlan(plan);
                    progress.setStartDate(rs.getDate("startDate"));
                    progress.setEndDate(rs.getDate("endDate"));
                    progress.setTotalProducts(rs.getInt("totalProducts"));
                    progress.setCompletedProducts(rs.getInt("completedProducts"));
                    progress.setRemainingProducts(progress.getTotalProducts() - progress.getCompletedProducts());

                    Date today = new Date();
                    if (progress.getCompletedProducts() == progress.getTotalProducts()) {
                        progress.setStatus("Completed");
                    } else if (today.before(progress.getEndDate())) {
                        progress.setStatus("In Progress");
                    } else {
                        progress.setStatus("Overdue");
                    }

                    progresses.add(progress);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return progresses;
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
