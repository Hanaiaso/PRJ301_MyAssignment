package dal;
import Plan.Entity.Plan;
import Plan.Entity.PlanCampain;
import Plan.Entity.Product;
import Schedule.Entity.ScheduleCampain;
import java.util.ArrayList;
import java.sql.*;
public class ScheduleCampainDBContext extends DBContext<ScheduleCampain> {
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
    public ScheduleCampain get(int id
    ) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
