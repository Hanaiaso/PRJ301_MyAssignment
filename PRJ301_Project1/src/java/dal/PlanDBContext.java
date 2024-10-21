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
    public void update(Plan entity) {
        String sqlPlan = "UPDATE [Plan] SET [plname] = ?, [StartDate] = ?, [EndDate] = ?, [did] = ? WHERE [plid] = ?";
        String sqlCampaign = "UPDATE [PlanCampain] SET [pid] = ?, [Quantity] = ?, [Estimate] = ? WHERE [plcid] = ?";
       // String sqlDeleteCampaign = "DELETE FROM [PlanCampain] WHERE [plcid] = ?"; // SQL for deletion of campaigns

        try (PreparedStatement planStm = connection.prepareStatement(sqlPlan); PreparedStatement campaignStm = connection.prepareStatement(sqlCampaign); 
                /*PreparedStatement deleteCampaignStm = connection.prepareStatement(sqlDeleteCampaign)*/) {

            // Update the Plan
            planStm.setString(1, entity.getName());
            planStm.setDate(2, new java.sql.Date(entity.getStart().getTime()));
            planStm.setDate(3, new java.sql.Date(entity.getEnd().getTime()));
            planStm.setInt(4, entity.getDept().getId());
            planStm.setInt(5, entity.getId());
            planStm.executeUpdate();

            // Update associated PlanCampains
            for (PlanCampain campaign : entity.getCampains()) {
                if (campaign.getId() > 0) { 
                    campaignStm.setInt(1, campaign.getProduct().getId());
                    campaignStm.setInt(2, campaign.getQuantity());
                    campaignStm.setFloat(3, campaign.getCost());
                    campaignStm.setInt(4, campaign.getId());
                    campaignStm.executeUpdate();
                }
            }

          
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
