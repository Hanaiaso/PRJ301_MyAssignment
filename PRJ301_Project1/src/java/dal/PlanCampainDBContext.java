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
public class PlanCampainDBContext extends DBContext<PlanCampain> {
    public List<PlanCampain> getCampainsByPlanId(int plid) {
        List<PlanCampain> campains = new ArrayList<>();
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
        String sql = "SELECT pc.plcid, pc.Quantity, pc.Estimate, p.pid, p.pname, pl.plid, pl.plname "
                + "FROM PlanCampain pc "
                + "JOIN Product p ON pc.pid = p.pid "
                + "JOIN [Plan] pl ON pc.plid = pl.plid "
                + "WHERE pl.isDone = 0 AND pc.plcid = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);  
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("pid"));
                product.setName(rs.getString("pname"));
                Plan plan = new Plan();
                plan.setId(rs.getInt("plid"));
                plan.setName(rs.getString("plname"));
                planCampain = new PlanCampain();
                planCampain.setId(rs.getInt("plcid"));
                planCampain.setProduct(product);  
                planCampain.setQuantity(rs.getInt("Quantity"));  
                planCampain.setCost(rs.getFloat("Estimate"));  
                planCampain.setPlan(plan);  
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanCampainDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return planCampain; 
    }
}
