package dal;
import Employee.Entity.Department;
import Plan.Entity.Plan;
import Plan.Entity.PlanCampain;
import Plan.Entity.Product;
import Schedule.Entity.ScheduleCampain;
import java.util.ArrayList;
import java.sql.*;
public class ScheduleCampainDBContext extends DBContext<ScheduleCampain> {
    public ArrayList<ScheduleCampain> getSchedulesByPlanCampainId(int planCampainId) {
        ArrayList<ScheduleCampain> schedules = new ArrayList<>();
        String sql = "SELECT sc.scid, sc.plcid, sc.Date, sc.Shift, sc.Quantity, " +
                     "pl.plid, pl.plname, pc.Quantity AS totalQuantity, p.pid, p.pname " +
                     "FROM SchedualCampaign sc " +
                     "JOIN PlanCampain pc ON sc.plcid = pc.plcid " +
                     "JOIN Product p ON pc.pid = p.pid " +
                     "JOIN [Plan] pl ON pc.plid = pl.plid " +
                     "WHERE pl.isDone = 0 AND sc.plcid = ? " +
                     "ORDER BY sc.Date, sc.Shift";
        
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, planCampainId);
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

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return schedules;
    }
    public int getAssignedQuantity(int scid) {
    String sql = "SELECT SUM(Quantity) AS totalAssigned FROM SchedualEmployee WHERE scid = ?";
    int totalAssigned = 0;
    try {
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, scid);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            totalAssigned = rs.getInt("totalAssigned");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return totalAssigned;
}
    public boolean isScheduleCreated(int plcid) {
        String sql = "SELECT COUNT(*) AS total FROM SchedualCampaign WHERE plcid = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, plcid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") > 0; 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; 
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
            Plan plan = new Plan();
            plan.setId(rs.getInt("plid"));
            plan.setName(rs.getString("plname"));
            Product product = new Product();
            product.setId(rs.getInt("pid"));
            product.setName(rs.getString("pname"));
            PlanCampain planCampain = new PlanCampain();
            planCampain.setId(rs.getInt("plcid"));
            planCampain.setQuantity(rs.getInt("totalQuantity"));
            planCampain.setProduct(product);
            planCampain.setPlan(plan);
            ScheduleCampain schedule = new ScheduleCampain();
            schedule.setId(rs.getInt("scid"));
            schedule.setPlancampain(planCampain);
            schedule.setDate(rs.getDate("Date"));
            schedule.setShift(rs.getInt("Shift"));
            schedule.setQuantity(rs.getInt("Quantity"));
            schedules.add(schedule);
        }
        System.out.println("Total schedules retrieved: " + schedules.size());
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return schedules;
}
    @Override
public ScheduleCampain get(int id) {
    ScheduleCampain scheduleCampain = null;
    String sql = "SELECT sc.scid, sc.plcid, sc.Date, sc.Shift, sc.Quantity, " +
                 "pc.plcid, pc.Quantity AS totalQuantity, p.pid, p.pname, " +
                 "pl.plid, pl.plname, pl.StartDate, pl.EndDate, d.did, d.dname, d.type " +
                 "FROM SchedualCampaign sc " +
                 "JOIN PlanCampain pc ON sc.plcid = pc.plcid " +
                 "JOIN Product p ON pc.pid = p.pid " +
                 "JOIN [Plan] pl ON pc.plid = pl.plid " +
                 "JOIN [Department] d ON pl.did = d.did " +
                 "WHERE sc.scid = ?";

    try {
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            scheduleCampain = new ScheduleCampain();
            scheduleCampain.setId(rs.getInt("scid"));
            scheduleCampain.setDate(rs.getDate("Date"));
            scheduleCampain.setShift(rs.getInt("Shift"));
            scheduleCampain.setQuantity(rs.getInt("Quantity"));

            int assignedQuantity = getAssignedQuantity(rs.getInt("scid"));
            scheduleCampain.setAssignedQuantity(assignedQuantity);

                // Tạo đối tượng Plan
                Plan plan = new Plan();
                plan.setId(rs.getInt("plid"));
                plan.setName(rs.getString("plname"));
                plan.setStart(rs.getDate("StartDate"));
                plan.setEnd(rs.getDate("EndDate"));
                
                Department dept = new Department();
                dept.setId(rs.getInt("did"));
                dept.setName(rs.getString("dname"));
                dept.setType(rs.getString("type"));
                plan.setDept(dept);

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

                // Gán PlanCampain vào ScheduleCampain
                scheduleCampain.setPlancampain(planCampain);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return scheduleCampain;
    }
}
