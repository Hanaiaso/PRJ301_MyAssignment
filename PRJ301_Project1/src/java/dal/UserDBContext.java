package dal;

import Employee.Entity.Employee;
import Login.Entity.Feature;
import Login.Entity.Role;
import Login.Entity.User;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDBContext extends DBContext<User> {

    boolean isUserActive(String username) {
        String sql = "SELECT isWork FROM [User] WHERE username = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("isWork");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public User get(String username, String password) {
        String sql = "SELECT username, displayname FROM [User] \n"
                + "WHERE username = ? AND [password] = ?";
        PreparedStatement stm = null;
        User user = null;
        if (isUserActive(username)) {
            try {
                stm = connection.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    user = new User();
                    user.setDisplayname(rs.getString("displayname"));
                    user.setUsername(rs.getString("username"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    stm.close();
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return user;
    }

    public ArrayList<Role> getRoles(String username) {
        String sql = "SELECT r.rid,r.rname,f.fid,f.fname,f.url FROM [User] u \n"
                + "	INNER JOIN UserRole ur ON ur.username = u.username\n"
                + "	INNER JOIN [Role] r ON r.rid = ur.rid\n"
                + "	INNER JOIN RoleFeature rf ON r.rid = rf.rid\n"
                + "	INNER JOIN Feature f ON f.fid = rf.fid\n"
                + "WHERE r.isWork = 1 AND u.username = ?\n"
                + "ORDER BY r.rid, f.fid ASC";
        PreparedStatement stm = null;
        ArrayList<Role> roles = new ArrayList<>();
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            Role c_role = new Role();
            c_role.setId(-1);
            while (rs.next()) {
                int rid = rs.getInt("rid");
                if (rid != c_role.getId()) {
                    c_role = new Role();
                    c_role.setId(rid);
                    c_role.setName(rs.getString("rname"));
                    roles.add(c_role);
                }
                Feature f = new Feature();
                f.setId(rs.getInt("fid"));
                f.setName(rs.getString("fname"));
                f.setUrl(rs.getString("url"));
                c_role.getFeatures().add(f);
                f.setRoles(roles);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return roles;
    }

    @Override
    public void insert(User entity) {
        String sqlInsertUser = "INSERT INTO [User] ([username], [password], [displayname], [isWork]) VALUES (?, ?, ?, 1)";
        try (PreparedStatement stmUser = connection.prepareStatement(sqlInsertUser)) {
            // Thêm user
            stmUser.setString(1, entity.getUsername());
            stmUser.setString(2, entity.getPassword());
            stmUser.setString(3, entity.getDisplayname());
            stmUser.executeUpdate();

            // Thêm vai trò của user nếu có
            if (entity.getRoles() != null && !entity.getRoles().isEmpty()) {
                String sqlInsertRole = "INSERT INTO [UserRole] (username, rid) VALUES (?, ?)";
                try (PreparedStatement stmRole = connection.prepareStatement(sqlInsertRole)) {
                    for (Role role : entity.getRoles()) {
                        stmRole.setString(1, entity.getUsername());
                        stmRole.setInt(2, role.getId());
                        stmRole.executeUpdate();
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE [User] SET [password] = ?, [displayname] = ? WHERE [username] = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, entity.getPassword());
            stm.setString(2, entity.getDisplayname());
            stm.setString(3, entity.getUsername());
            stm.executeUpdate();

            // Xóa tất cả các vai trò hiện tại của người dùng
            String deleteRolesSql = "DELETE FROM [UserRole] WHERE username = ?";
            try (PreparedStatement deleteStm = connection.prepareStatement(deleteRolesSql)) {
                deleteStm.setString(1, entity.getUsername());
                deleteStm.executeUpdate();
            }

            // Nếu người dùng có vai trò mới, thêm vào
            if (entity.getRoles() != null) {
                String insertRoleSql = "INSERT INTO [UserRole] (username, rid) VALUES (?, ?)";
                try (PreparedStatement insertStm = connection.prepareStatement(insertRoleSql)) {
                    for (Role role : entity.getRoles()) {
                        insertStm.setString(1, entity.getUsername());
                        insertStm.setInt(2, role.getId());
                        insertStm.executeUpdate();
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(User entity) {
        String sql = "UPDATE [User] SET [isWork] = 0 WHERE [username] = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, entity.getUsername());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<User> list() {
        String sql = "SELECT u.[username], [password], [displayname], u.[isWork], r.rid, r.rname \n"
                + "                FROM [User] u \n"
                + "                LEFT JOIN [UserRole] ur ON u.username = ur.username \n"
                + "                LEFT JOIN [Role] r ON r.rid = ur.rid \n"
                + "                WHERE u.isWork = 1";
        PreparedStatement stm = null;
        ArrayList<User> users = new ArrayList<>();

        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setIsactive(rs.getBoolean("isWork"));
                user.setDisplayname(rs.getString("displayname"));

                Role role = new Role();
                if (rs.getInt("rid") == 0) {
                    role = null;
                } else {
                    role.setId(rs.getInt("rid"));
                    role.setName(rs.getString("rname"));
                }
                ArrayList<Role> roles = new ArrayList<>();
                if (role != null) {
                    roles.add(role);
                }
                if (!roles.isEmpty()) {
                    user.setRoles(roles);
                } else {
                    user.setRoles(null);
                }
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return users;
    }

    public User get(String username) {
        String sql = "SELECT u.[username], [password], [displayname], u.[isWork], r.rid, r.rname \n"
                + "                FROM [User] u \n"
                + "                LEFT JOIN [UserRole] ur ON u.username = ur.username \n"
                + "                LEFT JOIN [Role] r ON r.rid = ur.rid \n"
                + "                WHERE u.[username] = ?";
        PreparedStatement stm = null;
        User user = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setIsactive(rs.getBoolean("isWork"));
                user.setDisplayname(rs.getString("displayname"));

                ArrayList<Role> roles = new ArrayList<>();
                do {
                    int roleId = rs.getInt("rid");
                    if (!rs.wasNull()) {
                        Role role = new Role();
                        role.setId(roleId);
                        role.setName(rs.getString("rname"));
                        roles.add(role);
                    }
                } while (rs.next());
                user.setRoles(roles);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
