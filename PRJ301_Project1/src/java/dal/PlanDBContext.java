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

                // Set values for the Plan insert query
                stmInsertPlan.setString(1, entity.getName());
                stmInsertPlan.setDate(2, entity.getStart());
                stmInsertPlan.setDate(3, entity.getEnd());
                stmInsertPlan.setInt(4, entity.getDept().getId());

                // Execute the insert query for Plan
                stmInsertPlan.executeUpdate();

                // Get the last inserted ID for the plan
                ResultSet rs = stmSelectPlan.executeQuery();
                if (rs.next()) {
                    entity.setId(rs.getInt("plid"));
                }
            }

            // Insert related PlanCampain entries
            for (PlanCampain campain : entity.getCampains()) {
                try (PreparedStatement stmInsertCampain = connection.prepareStatement(sqlInsertCampain)) {
                    // Set values for the PlanCampain insert query
                    stmInsertCampain.setInt(1, entity.getId());
                    stmInsertCampain.setInt(2, campain.getProduct().getId());
                    stmInsertCampain.setInt(3, campain.getQuantity());
                    stmInsertCampain.setFloat(4, campain.getCost());

                    // Execute the insert query for PlanCampain
                    stmInsertCampain.executeUpdate();
                }
            }

            // Commit the transaction
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
                connection.setAutoCommit(true); // Reset auto-commit mode
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

            // 1. Update the plan
            String sqlUpdatePlan = "UPDATE [Plan] SET [plname] = ?, [StartDate] = ?, [EndDate] = ?, [did] = ? WHERE [plid] = ?";
            stmtUpdatePlan = connection.prepareStatement(sqlUpdatePlan);
            stmtUpdatePlan.setString(1, plan.getName());
            stmtUpdatePlan.setDate(2, plan.getStart());
            stmtUpdatePlan.setDate(3, plan.getEnd());
            stmtUpdatePlan.setInt(4, plan.getDept().getId());
            stmtUpdatePlan.setInt(5, plan.getId());
            stmtUpdatePlan.executeUpdate();

            // 2. Update campains
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

//    private ArrayList<PlanCampain> getExistingCampains(int planId) throws SQLException {
//        ArrayList<PlanCampain> existingCampains = new ArrayList<>();
//        String sql = "SELECT [pid], [quantity], [cost] FROM [PlanCampain] WHERE [planid] = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, planId);
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    PlanCampain campain = new PlanCampain();
//                    Product product = new Product();
//                    product.setId(rs.getInt("pid"));
//                    campain.setProduct(product);
//                    campain.setQuantity(rs.getInt("quantity"));
//                    campain.setCost(rs.getFloat("cost"));
//                    existingCampains.add(campain);
//                }
//            }
//        }
//        return existingCampains;
//    }

    @Override
    public void delete(Plan entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Not implemented
    }

    @Override
    public ArrayList<Plan> list() {
        ArrayList<Plan> plans = new ArrayList<>();
        String sql = "SELECT p.[plid], [plname], [StartDate], [EndDate], p.[did], d.dname, pr.pname, "
                + "pc.plcid, pc.pid, pc.Quantity, pc.Estimate "
                + "FROM [Plan] p "
                + "INNER JOIN [Department] d ON p.[did] = d.[did] "
                + "LEFT JOIN PlanCampain pc ON p.plid = pc.plid "
                + "LEFT JOIN [Product] pr ON pr.pid = pc.pid"; // Change INNER JOIN to LEFT JOIN

        try (PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            Plan currentPlan = null; // To hold the current plan
            while (rs.next()) {
                int planId = rs.getInt("plid");

                // Check if we are still in the same plan
                if (currentPlan == null || currentPlan.getId() != planId) {
                    // If we are on a new plan, create a new Plan object
                    currentPlan = new Plan();
                    currentPlan.setId(planId);
                    currentPlan.setName(rs.getString("plname"));
                    currentPlan.setStart(rs.getDate("StartDate"));
                    currentPlan.setEnd(rs.getDate("EndDate"));

                    // Create Department object and set info
                    Department d = new Department();
                    d.setId(rs.getInt("did"));
                    d.setName(rs.getString("dname"));

                    // Set Department for Plan
                    currentPlan.setDept(d);

                    // Create a list for campaigns
                    currentPlan.setCampains(new ArrayList<>());
                    plans.add(currentPlan); // Add the new plan to the list
                }

                // Now process campaigns, if available
                if (rs.getInt("plcid") > 0) {
                    PlanCampain pc = new PlanCampain();
                    Product p = new Product();
                    p.setId(rs.getInt("pid"));
                    p.setName(rs.getString("pname"));
                    pc.setProduct(p);

                    pc.setId(rs.getInt("plcid"));
                    pc.setQuantity(rs.getInt("Quantity"));
                    pc.setCost(rs.getFloat("Estimate"));

                    // Add the campaign to the current plan's campaign list
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
                + "WHERE p.[plid] = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id); // Set the plid

            // Execute query and map results
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    plan = new Plan();
                    plan.setId(rs.getInt("plid"));
                    plan.setName(rs.getString("plname"));
                    plan.setStart(rs.getDate("StartDate"));
                    plan.setEnd(rs.getDate("EndDate"));

                    // Create Department object and set info
                    Department d = new Department();
                    d.setId(rs.getInt("did"));
                    d.setName(rs.getString("dname"));
                    d.setType(rs.getString("type"));

                    // Set Department for Plan
                    plan.setDept(d);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return plan; // Return found Plan or null
    }

}
