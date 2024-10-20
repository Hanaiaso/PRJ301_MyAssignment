package dal;

import Employee.Entity.Department;
import Plan.Entity.Plan;
import Plan.Entity.PlanCampain;
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

            try (PreparedStatement stmInsertPlan = connection.prepareStatement(sqlInsertPlan);
                 PreparedStatement stmSelectPlan = connection.prepareStatement(sqlSelectPlan)) {

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
        String sqlUpdatePlan = "UPDATE [Plan] SET [plname] = ?, [StartDate] = ?, [EndDate] = ?, [did] = ? WHERE [plid] = ?";
        String sqlUpdateCampain = "UPDATE [PlanCampain] SET [Quantity] = ?, [Estimate] = ? WHERE [plid] = ? AND [pid] = ?";
        String sqlInsertCampain = "INSERT INTO [PlanCampain] ([plid], [pid], [Quantity], [Estimate]) VALUES (?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmUpdatePlan = connection.prepareStatement(sqlUpdatePlan)) {
                // Set values for the update Plan query
                stmUpdatePlan.setString(1, entity.getName());
                stmUpdatePlan.setDate(2, entity.getStart());
                stmUpdatePlan.setDate(3, entity.getEnd());
                stmUpdatePlan.setInt(4, entity.getDept().getId());
                stmUpdatePlan.setInt(5, entity.getId());

                // Execute the update
                stmUpdatePlan.executeUpdate();
            }

            // Update or insert campaigns
            for (PlanCampain campain : entity.getCampains()) {
                try (PreparedStatement stmUpdateCampain = connection.prepareStatement(sqlUpdateCampain)) {
                    // Set values for the update PlanCampain query
                    stmUpdateCampain.setInt(1, campain.getQuantity());
                    stmUpdateCampain.setFloat(2, campain.getCost());
                    stmUpdateCampain.setInt(3, entity.getId());
                    stmUpdateCampain.setInt(4, campain.getProduct().getId());

                    // Execute the update
                    int updatedRows = stmUpdateCampain.executeUpdate();

                    // If no rows were updated, insert the new campaign
                    if (updatedRows == 0) {
                        try (PreparedStatement stmInsertCampain = connection.prepareStatement(sqlInsertCampain)) {
                            // Set values for the insert PlanCampain query
                            stmInsertCampain.setInt(1, entity.getId());
                            stmInsertCampain.setInt(2, campain.getProduct().getId());
                            stmInsertCampain.setInt(3, campain.getQuantity());
                            stmInsertCampain.setFloat(4, campain.getCost());

                            // Execute the insert query for PlanCampain
                            stmInsertCampain.executeUpdate();
                        }
                    }
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
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Plan entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Not implemented
    }

    @Override
    public ArrayList<Plan> list() {
        ArrayList<Plan> plans = new ArrayList<>();
        String sql = "SELECT p.[plid], p.[plname], p.[StartDate], p.[EndDate], d.[did], d.[dname], d.[type] "
                + "FROM [Plan] p "
                + "INNER JOIN [Department] d ON p.[did] = d.[did]";

        try (PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            // Process the result set
            while (rs.next()) {
                Plan plan = new Plan();
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
                plans.add(plan);
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
    
  public ArrayList<PlanCampain> getCampaignsByPlanId(int planId) {
    ArrayList<PlanCampain> campaigns = new ArrayList<>();
    
    // SQL query to retrieve campaigns for the specified plan ID
    String sql = "SELECT * FROM PlanCampain WHERE plid = ?"; // Giả sử plid là khóa ngoại liên kết với bảng Plan

    // Use try-with-resources for better resource management
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, planId); // Set the plan ID in the query
        try (ResultSet resultSet = statement.executeQuery()) {
            // Iterate through the result set
            while (resultSet.next()) {
                PlanCampain campaign = new PlanCampain();
                campaign.setId(resultSet.getInt("plcid")); // Set campaign ID   
                campaign.setQuantity(resultSet.getInt("Quantity")); // Set quantity
                campaign.setCost(resultSet.getFloat("Estimate")); // Set estimated cost

                // Sử dụng productId từ cơ sở dữ liệu (giả sử bạn có cột productId trong bảng PlanCampain)
               // campaign.setProduct(getProduct("pid")); // Lấy pid từ cơ sở dữ liệu

                campaigns.add(campaign); // Add campaign to the list
            }
        }
    } catch (SQLException e) {
        // Log the exception (you can replace with your logger)
        Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, "Error retrieving campaigns", e);
    }
    
    return campaigns; // Return the list of campaigns
}

}



