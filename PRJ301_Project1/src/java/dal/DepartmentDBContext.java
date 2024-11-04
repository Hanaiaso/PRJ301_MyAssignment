package dal;

import Employee.Entity.Department;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentDBContext extends DBContext<Department> {

    public ArrayList<Department> get(String type) {
        ArrayList<Department> depts = new ArrayList<>();
        PreparedStatement command = null;
        try {
            String sql = "SELECT [did]\n"
                    + "      ,[dname]\n"
                    + "      ,[type]\n"
                    + "  FROM [Department] WHERE [type] = ?";
            command = connection.prepareStatement(sql);
            command.setString(1, type);
            ResultSet rs = command.executeQuery();
            while (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("did"));
                d.setName(rs.getString("dname"));
                d.setType(rs.getString("type"));
                depts.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(dal.DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                command.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(dal.DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return depts;
    }

    @Override
    public void insert(Department entity) {
        String sql = "INSERT INTO [Department] (dname, type, isWork) VALUES (?, ?, 1)";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, entity.getName());
            stm.setString(2, entity.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Department entity) {
        String sql = "UPDATE [Department] SET [dname] = ?, [type] = ? WHERE [did] = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, entity.getName());
            stm.setString(2, entity.getType());
            stm.setInt(3, entity.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Department entity) {
        String sql = "UPDATE [Department] SET [isWork] = 0 WHERE [did] = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, entity.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Department> list() {
        ArrayList<Department> depts = new ArrayList<>();
        PreparedStatement command = null;
        try {
            String sql = "SELECT d.did, d.dname, d.type, "
                    + "(SELECT COUNT(*) FROM Employee e WHERE e.did = d.did AND e.isWork = 1) AS totalEmployees "
                    + "FROM Department d "
                    + "WHERE d.isWork = 1";
            command = connection.prepareStatement(sql);
            ResultSet rs = command.executeQuery();
            while (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("did"));
                d.setName(rs.getString("dname"));
                d.setType(rs.getString("type"));
                d.setTotalEmployees(rs.getInt("totalEmployees")); // Gán tổng số nhân viên
                depts.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (command != null) {
                    command.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return depts;
    }

    @Override
    public Department get(int id) {
        String sql = "SELECT [did], [dname], [type], [isWork] FROM [Department] WHERE [did] = ?";
        PreparedStatement stm = null;
        Department department = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                department = new Department();
                department.setId(rs.getInt("did"));
                department.setName(rs.getString("dname"));
                department.setType(rs.getString("type"));
                department.setIsWork(rs.getBoolean("isWork"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return department;
    }

}
