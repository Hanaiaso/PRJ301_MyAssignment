/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import Employee.Entity.Department;
import Plan.Entity.Plan;
import Plan.Entity.PlanCampain;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.List;

/**
 *
 * @author LEGION
 */
public class PlanDBContext extends DBContext<Plan> {

    @Override
    public void insert(Plan entity) {
        PreparedStatement stm_insert_plan = null;
        PreparedStatement stm_select_plan = null;
        PreparedStatement stm_insert_campain = null;
        ResultSet rs = null;

        try {
            connection.setAutoCommit(false);

            // SQL query to insert into Plan table
            String sql_insert_plan = "INSERT INTO [Plan] ([plname], [StartDate], [EndDate], [did]) VALUES (?, ?, ?, ?)";
            stm_insert_plan = connection.prepareStatement(sql_insert_plan);

            // Set values for the insert plan query
            stm_insert_plan.setString(1, entity.getName());
            stm_insert_plan.setDate(2, entity.getStart());
            stm_insert_plan.setDate(3, entity.getEnd());
            stm_insert_plan.setInt(4, entity.getDept().getId());

            // Execute the insert query for Plan
            stm_insert_plan.executeUpdate();

            // Get the last inserted ID for the plan
            String sql_select_plan = "SELECT @@IDENTITY as plid";
            stm_select_plan = connection.prepareStatement(sql_select_plan);
            rs = stm_select_plan.executeQuery();

            if (rs.next()) {
                entity.setId(rs.getInt("plid"));
            }

            // Insert related PlanCampain entries
            for (PlanCampain campain : entity.getCampains()) {
                String sql_insert_campain = "INSERT INTO [PlanCampain] ([plid], [pid], [Quantity], [Estimate]) VALUES (?, ?, ?, ?)";
                stm_insert_campain = connection.prepareStatement(sql_insert_campain);

                // Set values for the PlanCampain insert query
                stm_insert_campain.setInt(1, entity.getId());
                stm_insert_campain.setInt(2, campain.getProduct().getId());
                stm_insert_campain.setInt(3, campain.getQuantity());
                stm_insert_campain.setFloat(4, campain.getCost());

                // Execute the insert query for PlanCampain
                stm_insert_campain.executeUpdate();
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
            // Close all resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm_insert_plan != null) {
                    stm_insert_plan.close();
                }
                if (stm_select_plan != null) {
                    stm_select_plan.close();
                }
                if (stm_insert_campain != null) {
                    stm_insert_campain.close();
                }
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Plan entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Plan entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Plan> list() {
        ArrayList<Plan> plans = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            // Câu lệnh SQL để lấy tất cả các plan và thông tin Department
            String sql = "SELECT p.[plid], p.[plname], p.[StartDate], p.[EndDate], d.[did], d.[dname], d.[type] "
                    + "FROM [Plan] p "
                    + "INNER JOIN [Department] d ON p.[did] = d.[did]";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            // Duyệt qua kết quả và tạo đối tượng Plan
            while (rs.next()) {
                Plan plan = new Plan();
                plan.setId(rs.getInt("plid"));
                plan.setName(rs.getString("plname"));
                plan.setStart(rs.getDate("StartDate"));
                plan.setEnd(rs.getDate("EndDate"));

                // Tạo đối tượng Department và thiết lập thông tin
                Department d = new Department();
                d.setId(rs.getInt("did"));
                d.setName(rs.getString("dname"));
                d.setType(rs.getString("type"));

                // Gán đối tượng Department cho Plan
                plan.setDept(d);

                // Thêm Plan vào danh sách
                plans.add(plan);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Đóng các tài nguyên
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return plans;
    }

    @Override
    public Plan get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
