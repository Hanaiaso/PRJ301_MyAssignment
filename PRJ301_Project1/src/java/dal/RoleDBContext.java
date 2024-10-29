package dal;

import Login.Entity.Feature;
import Login.Entity.Role;
import java.util.ArrayList;
import java.sql.*;

public class RoleDBContext extends DBContext<Role> {

    @Override
    public void insert(Role entity) {
        String sqlInsertRole = "INSERT INTO [Role] (rname) VALUES (?)";
        String sqlSelectIdentity = "SELECT @@IDENTITY as rid";
        String sqlInsertRoleFeature = "INSERT INTO RoleFeature (rid, fid) VALUES (?, ?)";

        try {
            connection.setAutoCommit(false);

            // Insert new Role
            try (PreparedStatement stmInsertRole = connection.prepareStatement(sqlInsertRole)) {
                stmInsertRole.setString(1, entity.getName());
                stmInsertRole.executeUpdate();
            }

            // Get generated Role ID
            try (PreparedStatement stmSelectIdentity = connection.prepareStatement(sqlSelectIdentity)) {
                ResultSet rs = stmSelectIdentity.executeQuery();
                if (rs.next()) {
                    entity.setId(rs.getInt("rid"));
                }
            }

            // Insert Features for the Role
            if (entity.getFeatures() != null && !entity.getFeatures().isEmpty()) {
                try (PreparedStatement stmInsertRoleFeature = connection.prepareStatement(sqlInsertRoleFeature)) {
                    for (Feature feature : entity.getFeatures()) {
                        stmInsertRoleFeature.setInt(1, entity.getId());
                        stmInsertRoleFeature.setInt(2, feature.getId());
                        stmInsertRoleFeature.executeUpdate();
                    }
                }
            }

            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    @Override
    public void update(Role entity) {
        String sqlUpdateRole = "UPDATE [Role] SET rname = ? WHERE rid = ?";
        String sqlDeleteRoleFeatures = "DELETE FROM RoleFeature WHERE rid = ?";
        String sqlInsertRoleFeature = "INSERT INTO RoleFeature (rid, fid) VALUES (?, ?)";

        try {
            connection.setAutoCommit(false);

            // Update role name
            try (PreparedStatement stmUpdateRole = connection.prepareStatement(sqlUpdateRole)) {
                stmUpdateRole.setString(1, entity.getName());
                stmUpdateRole.setInt(2, entity.getId());
                stmUpdateRole.executeUpdate();
            }

            // Delete all features associated with the role
            try (PreparedStatement stmDeleteRoleFeatures = connection.prepareStatement(sqlDeleteRoleFeatures)) {
                stmDeleteRoleFeatures.setInt(1, entity.getId());
                stmDeleteRoleFeatures.executeUpdate();
            }

            // Add new features
            if (entity.getFeatures() != null && !entity.getFeatures().isEmpty()) {
                try (PreparedStatement stmInsertRoleFeature = connection.prepareStatement(sqlInsertRoleFeature)) {
                    for (Feature feature : entity.getFeatures()) {
                        stmInsertRoleFeature.setInt(1, entity.getId());
                        stmInsertRoleFeature.setInt(2, feature.getId());
                        stmInsertRoleFeature.executeUpdate();
                    }
                }
            }

            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Role entity) {
        String sql = "UPDATE [Role] SET isWork = 0 WHERE rid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, entity.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Role> list() {
        ArrayList<Role> roles = new ArrayList<>();
        String sql = "SELECT r.rid, r.rname, f.fid, f.fname, f.url "
                + "FROM [Role] r "
                + "LEFT JOIN RoleFeature rf ON r.rid = rf.rid "
                + "LEFT JOIN Feature f ON f.fid = rf.fid "
                + "WHERE r.isWork = 1 " // Only select active roles
                + "ORDER BY r.rid";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();

            Role currentRole = new Role();
            currentRole.setId(-1);
            while (rs.next()) {
                int roleId = rs.getInt("rid");

                if (roleId != currentRole.getId()) {
                    if (currentRole.getId() != -1) {
                        roles.add(currentRole);
                    }
                    // Initialize new role
                    currentRole = new Role();
                    currentRole.setId(roleId);
                    currentRole.setName(rs.getString("rname"));
                    currentRole.setFeatures(new ArrayList<>());
                }

                int featureId = rs.getInt("fid");
                if (!rs.wasNull()) {
                    Feature feature = new Feature();
                    feature.setId(featureId);
                    feature.setName(rs.getString("fname"));
                    feature.setUrl(rs.getString("url"));
                    currentRole.getFeatures().add(feature);
                }
            }

            if (currentRole.getId() != -1) {
                roles.add(currentRole);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return roles;
    }

    @Override
    public Role get(int id) {
        String sql = "SELECT r.rid, r.rname, f.fid, f.fname, f.url "
                + "FROM [Role] r "
                + "LEFT JOIN RoleFeature rf ON r.rid = rf.rid "
                + "LEFT JOIN Feature f ON f.fid = rf.fid "
                + "WHERE r.rid = ? AND r.isWork = 1";

        Role role = null;

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                role = new Role();
                role.setId(rs.getInt("rid"));
                role.setName(rs.getString("rname"));
                role.setFeatures(new ArrayList<>());

                do {
                    int featureId = rs.getInt("fid");
                    if (!rs.wasNull()) {
                        Feature feature = new Feature();
                        feature.setId(featureId);
                        feature.setName(rs.getString("fname"));
                        feature.setUrl(rs.getString("url"));
                        role.getFeatures().add(feature);
                    }
                } while (rs.next());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return role;
    }
}
