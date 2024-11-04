package dal;

import Employee.Entity.Department;
import Plan.Entity.Plan;
import Plan.Entity.PlanCampain;
import Plan.Entity.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlanCampainDBContext extends DBContext<PlanCampain> {

    public ArrayList<PlanCampain> getCampainsByPlanId(int plid) {
        ArrayList<PlanCampain> campains = new ArrayList<>();
        String sqlSelectCampain = "SELECT pc.plcid, pc.Quantity, pc.Estimate, p.pid, p.pname "
                + "FROM PlanCampain pc "
                + "JOIN Product p ON pc.pid = p.pid "
                + "WHERE pc.plid = ?";
        try (PreparedStatement stmSelectCampain = connection.prepareStatement(sqlSelectCampain)) {
            stmSelectCampain.setInt(1, plid);
            ResultSet rs = stmSelectCampain.executeQuery();
            while (rs.next()) {
                PlanCampain campain = new PlanCampain();
                Product product = new Product();
                product.setId(rs.getInt("pid"));
                product.setName(rs.getString("pname"));
                campain.setProduct(product);
                campain.setQuantity(rs.getInt("Quantity"));
                campain.setCost(rs.getFloat("Estimate"));
                campain.setId(rs.getInt("plcid"));
                campains.add(campain);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return campains;
    }

    @Override
    public void insert(PlanCampain entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(PlanCampain entity) {
    }

    @Override
    public void delete(PlanCampain entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<PlanCampain> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PlanCampain get(int id) {
        PlanCampain planCampain = null;
        String sql = "SELECT pc.plcid, pc.Quantity, "
                + "p.pid, p.pname, "
                + "pl.plid, pl.plname, pl.StartDate, pl.EndDate, pl.isDone, "
                + "d.did, d.dname, d.type "
                + "FROM PlanCampain pc "
                + "JOIN Product p ON pc.pid = p.pid "
                + "JOIN [Plan] pl ON pc.plid = pl.plid "
                + "LEFT JOIN Department d ON pl.did = d.did "
                + "WHERE pl.isDone = 0 AND pc.plcid = ?";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                planCampain = new PlanCampain();
                planCampain.setId(rs.getInt("plcid"));
                planCampain.setQuantity(rs.getInt("Quantity"));

                // Tạo đối tượng Product
                Product product = new Product();
                product.setId(rs.getInt("pid"));
                product.setName(rs.getString("pname"));
                planCampain.setProduct(product);

                // Tạo đối tượng Plan
                Plan plan = new Plan();
                plan.setId(rs.getInt("plid"));
                plan.setName(rs.getString("plname"));
                plan.setStart(rs.getDate("StartDate"));
                plan.setEnd(rs.getDate("EndDate"));
                plan.setIsDone(rs.getBoolean("isDone"));

                // Tạo đối tượng Department và gán vào Plan nếu có dữ liệu
                int departmentId = rs.getInt("did");
                if (!rs.wasNull()) { // Kiểm tra nếu `did` không phải là `null`
                    Department department = new Department();
                    department.setId(departmentId);
                    department.setName(rs.getString("dname"));
                    department.setType(rs.getString("type"));
                    plan.setDept(department);
                }

                // Gắn Plan vào PlanCampain
                planCampain.setPlan(plan);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return planCampain;
    }
}
