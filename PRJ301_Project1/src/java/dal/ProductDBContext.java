package dal;

import Plan.Entity.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDBContext extends DBContext<Product> {

    @Override
    public void insert(Product entity) {
        String sql_insert = "INSERT INTO [Product]\n"
                + "           ([pname])\n"
                + "     VALUES\n"
                + "           (?)";
        String sql_select = "SELECT @@IDENTITY as pid";

        PreparedStatement stm_insert = null;
        PreparedStatement stm_select = null;
        try {
            connection.setAutoCommit(false);
            stm_insert = connection.prepareStatement(sql_insert);
            stm_insert.setString(1, entity.getName());
            stm_insert.executeUpdate();
            stm_select = connection.prepareStatement(sql_select);
            ResultSet rs = stm_select.executeQuery();
            if (rs.next()) {
                entity.setId(rs.getInt("pid"));
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Product entity) {
        String sql_update = "UPDATE [Product]\n"
                + "   SET [pname] = ?\n"
                + " WHERE pid = ?";
        PreparedStatement stm_update = null;
        try {
            stm_update = connection.prepareStatement(sql_update);
            stm_update.setString(1, entity.getName());
            stm_update.setInt(2, entity.getId());
            stm_update.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Product entity) {
        String sql_update = "UPDATE [Product]\n"
                + "   SET [isOn] = 0\n"
                + " WHERE pid = ?";
        PreparedStatement stm_update = null;
        try {
            stm_update = connection.prepareStatement(sql_update);
            stm_update.setInt(1, entity.getId());
            stm_update.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Product> list() {
        ArrayList<Product> products = new ArrayList<>();
        PreparedStatement command = null;
        try {
            String sql = "SELECT [pid]\n"
                    + "      ,[pname]\n"
                    + "  FROM [Product]"
                    + "  WHERE isOn = 1";
            command = connection.prepareStatement(sql);
            ResultSet rs = command.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("pid"));
                p.setName(rs.getString("pname"));
                products.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                command.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return products;
    }

    @Override
    public Product get(int id) {
        String sql = "SELECT pid, pname FROM [Product] WHERE isOn = 1 and pid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("pid"));
                    product.setName(rs.getString("pname"));
                    return product;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy sản phẩm
    }

}
