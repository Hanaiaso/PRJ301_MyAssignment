package dal;

import Employee.Entity.Department;
import Plan.Entity.Plan;
import Plan.Entity.Product;
import Progress.Entity.ProductProgress;
import Progress.Entity.Progress;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ProgressDBContext extends DBContext<Progress> {

    // Lấy danh sách tiến độ cho từng sản phẩm trong kế hoạch
    public ArrayList<ProductProgress> getProductProgressByPlanId(int planId) {
        ArrayList<ProductProgress> productProgresses = new ArrayList<>();
        String sql = "SELECT p.pid, p.pname, pc.quantity, "
                   + "SUM(ISNULL(a.Quantity, 0)) AS completedQuantity "
                   + "FROM Product p "
                   + "JOIN PlanCampain pc ON p.pid = pc.pid "
                   + "LEFT JOIN SchedualCampaign sc ON pc.plcid = sc.plcid "
                   + "LEFT JOIN SchedualEmployee se ON sc.scid = se.scid "
                   + "LEFT JOIN Attendence a ON se.seid = a.seid "
                   + "WHERE pc.plid = ? "
                   + "GROUP BY p.pid, p.pname, pc.quantity";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, planId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    ProductProgress productProgress = new ProductProgress();
                    productProgress.setProduct(new Product(rs.getInt("pid"), rs.getString("pname")));
                    productProgress.setTotalProducts(rs.getInt("quantity"));
                    productProgress.setCompletedProducts(rs.getInt("completedQuantity"));
                    productProgress.setRemainingProducts(productProgress.getTotalProducts() - productProgress.getCompletedProducts());
                    productProgresses.add(productProgress);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productProgresses;
    }

    
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

    // Lấy thông tin phòng ban
    String sql = "SELECT d.did, d.dname FROM [Plan] p " +
                 "JOIN Department d ON p.did = d.did " +
                 "WHERE p.plid = ?";
    try (PreparedStatement stm = connection.prepareStatement(sql)) {
        stm.setInt(1, planId);
        try (ResultSet rs = stm.executeQuery()) {
            if (rs.next()) {
                Department department = new Department();
                department.setId(rs.getInt("did"));
                department.setName(rs.getString("dname"));
                progress.setDepartment(department);
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

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

    // Lấy thông tin department bằng ID kế hoạch
    public Department getDepartmentByPlanId(int planId) {
        String sql = "SELECT d.did, d.dname FROM Department d "
                   + "JOIN Plan p ON d.did = p.did WHERE p.plid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, planId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Department department = new Department();
                    department.setId(rs.getInt("did"));
                    department.setName(rs.getString("dname"));
                    return department;
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
