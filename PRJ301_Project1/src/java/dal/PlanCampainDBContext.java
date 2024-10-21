/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

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

/**
 *
 * @author LEGION
 */
public class PlanCampainDBContext extends DBContext<PlanCampain>{

   public List<PlanCampain> getCampainsByPlanId(int plid) {
    List<PlanCampain> campains = new ArrayList<>();

    String sqlSelectCampain = "SELECT pc.plcid, pc.Quantity, pc.Estimate, p.pid, p.pname "
            + "FROM PlanCampain pc "
            + "JOIN Product p ON pc.pid = p.pid "
            + "WHERE pc.plid = ?";

    try (PreparedStatement stmSelectCampain = connection.prepareStatement(sqlSelectCampain)) {
        // Set the value for the plan ID
        stmSelectCampain.setInt(1, plid); // Sử dụng setInt vì plid có kiểu dữ liệu là int

        // Execute the query and retrieve the result set
        ResultSet rs = stmSelectCampain.executeQuery();

        // Loop through the result set and populate the PlanCampain list
        while (rs.next()) {
            PlanCampain campain = new PlanCampain();

            // Create and populate the Product object
            Product product = new Product();
            product.setId(rs.getInt("pid")); // Sử dụng "pid" từ bảng Product
            product.setName(rs.getString("pname")); // Tên sản phẩm
            campain.setProduct(product); // Set the product object in the PlanCampain

            // Set the properties of the PlanCampain object
            campain.setQuantity(rs.getInt("Quantity"));
            campain.setCost(rs.getFloat("Estimate"));

            // Thêm ID chiến dịch vào đối tượng PlanCampain nếu cần
            campain.setId(rs.getInt("plcid")); // Đảm bảo bạn có trường này trong cơ sở dữ liệu

            // Thêm campain object vào danh sách
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
