package dal;
import Employee.Entity.Department;
import Plan.Entity.Plan;
import Plan.Entity.PlanCampain;
import Plan.Entity.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
public class PlanDBContext extends DBContext<Plan> {
    @Override
    public void insert(Plan entity) {
        String sqlInsertPlan = "INSERT INTO [Plan] ([plname], [StartDate], [EndDate], [did]) VALUES (?, ?, ?, ?)";
        String sqlSelectPlan = "SELECT @@IDENTITY as plid";
        String sqlInsertCampain = "INSERT INTO [PlanCampain] ([plid], [pid], [Quantity], [Estimate]) VALUES (?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement stmInsertPlan = connection.prepareStatement(sqlInsertPlan); PreparedStatement stmSelectPlan = connection.prepareStatement(sqlSelectPlan)) {
                stmInsertPlan.setString(1, entity.getName());
                stmInsertPlan.setDate(2, entity.getStart());
                stmInsertPlan.setDate(3, entity.getEnd());
                stmInsertPlan.setInt(4, entity.getDept().getId());
                stmInsertPlan.executeUpdate();
                ResultSet rs = stmSelectPlan.executeQuery();
                if (rs.next()) {
                    entity.setId(rs.getInt("plid"));
                }
            }
            for (PlanCampain campain : entity.getCampains()) {
                try (PreparedStatement stmInsertCampain = connection.prepareStatement(sqlInsertCampain)) {
                    stmInsertCampain.setInt(1, entity.getId());
                    stmInsertCampain.setInt(2, campain.getProduct().getId());
                    stmInsertCampain.setInt(3, campain.getQuantity());
                    stmInsertCampain.setFloat(4, campain.getCost());
                    stmInsertCampain.executeUpdate();
                }
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true); 
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @Override
    public void update(Plan plan) {
        PreparedStatement stmtUpdatePlan = null;
        PreparedStatement stmtUpdateCampain = null;
        try {
            connection.setAutoCommit(false);
            String sqlUpdatePlan = "UPDATE [Plan] SET [plname] = ?, [StartDate] = ?, [EndDate] = ?, [did] = ? WHERE [plid] = ? AND [isDone = 0]";
            stmtUpdatePlan = connection.prepareStatement(sqlUpdatePlan);
            stmtUpdatePlan.setString(1, plan.getName());
            stmtUpdatePlan.setDate(2, plan.getStart());
            stmtUpdatePlan.setDate(3, plan.getEnd());
            stmtUpdatePlan.setInt(4, plan.getDept().getId());
            stmtUpdatePlan.setInt(5, plan.getId());
            stmtUpdatePlan = connection.prepareStatement(sqlUpdatePlan);
            String sqlUpdateCampain = "UPDATE [PlanCampain] SET [Quantity] = ?, [Estimate] = ? WHERE [plcid] = ?";
            stmtUpdateCampain = connection.prepareStatement(sqlUpdateCampain);
            for (PlanCampain campain : plan.getCampains()) {
                stmtUpdateCampain.setInt(1, campain.getQuantity());
                stmtUpdateCampain.setFloat(2, campain.getCost());
                stmtUpdateCampain.setInt(3, campain.getId());
                stmtUpdateCampain.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmtUpdatePlan != null) {
                    stmtUpdatePlan.close();
                }
                if (stmtUpdateCampain != null) {
                    stmtUpdateCampain.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @Override
    public void delete(Plan entity) {
        try {
            PreparedStatement stmtUpdatePlan = null;
            String sqlUpdatePlan = "UPDATE [Plan] SET [isDone] = 1 WHERE [plid] = ?";
            stmtUpdatePlan = connection.prepareStatement(sqlUpdatePlan);
            stmtUpdatePlan.setInt(1, entity.getId());            
            stmtUpdatePlan.executeUpdate();          
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }   
    @Override
    public ArrayList<Plan> list() {
        ArrayList<Plan> plans = new ArrayList<>();
        String sql = "SELECT p.[plid], [plname], [StartDate], [EndDate], p.[did], d.dname, pr.pname, "
                + "pc.plcid, pc.pid, pc.Quantity, pc.Estimate "
                + "FROM [Plan] p "
                + "INNER JOIN [Department] d ON p.[did] = d.[did] "
                + "LEFT JOIN PlanCampain pc ON p.plid = pc.plid "
                + "LEFT JOIN [Product] pr ON pr.pid = pc.pid WHERE [isDone] = 0"; 
        try (PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            Plan currentPlan = null; 
            while (rs.next()) {
                int planId = rs.getInt("plid");
                if (currentPlan == null || currentPlan.getId() != planId) {
                    currentPlan = new Plan();
                    currentPlan.setId(planId);
                    currentPlan.setName(rs.getString("plname"));
                    currentPlan.setStart(rs.getDate("StartDate"));
                    currentPlan.setEnd(rs.getDate("EndDate"));
                    Department d = new Department();
                    d.setId(rs.getInt("did"));
                    d.setName(rs.getString("dname"));
                    currentPlan.setDept(d);
                    currentPlan.setCampains(new ArrayList<>());
                    plans.add(currentPlan); 
                }
                if (rs.getInt("plcid") > 0) {
                    PlanCampain pc = new PlanCampain();
                    Product p = new Product();
                    p.setId(rs.getInt("pid"));
                    p.setName(rs.getString("pname"));
                    pc.setProduct(p);
                    pc.setId(rs.getInt("plcid"));
                    pc.setQuantity(rs.getInt("Quantity"));
                    pc.setCost(rs.getFloat("Estimate"));
                    currentPlan.getCampains().add(pc);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plans;
    }
    @Override
    public Plan get(int id) {
        Plan plan = null;
        String sql = "SELECT p.[plid], p.[plname], p.[StartDate], p.[EndDate], d.[did], d.[dname], d.[type] "
                + "FROM [Plan] p "
                + "INNER JOIN [Department] d ON p.[did] = d.[did] "
                + "WHERE p.[plid] = ? AND isDone = 0";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id); 
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    plan = new Plan();
                    plan.setId(rs.getInt("plid"));
                    plan.setName(rs.getString("plname"));
                    plan.setStart(rs.getDate("StartDate"));
                    plan.setEnd(rs.getDate("EndDate"));
                    Department d = new Department();
                    d.setId(rs.getInt("did"));
                    d.setName(rs.getString("dname"));
                    d.setType(rs.getString("type"));
                    plan.setDept(d);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plan; 
    }
}
