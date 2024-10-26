package dal;

import Schedule.Entity.Attendance;
import Employee.Entity.Employee;
import Schedule.Entity.ScheduleCampain;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDBContext extends DBContext<Attendance> {

    // Insert a single Attendance record
    public void insertAttendance(Attendance attendance) {
        String sql = "INSERT INTO Attendance (employee_id, schedule_id, date, quantity, alpha) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, attendance.getEmployee().getId());
            stm.setInt(2, attendance.getScheduleCampain().getId());
            stm.setDate(3, attendance.getDate());
            stm.setInt(4, attendance.getQuantity());
            stm.setDouble(5, attendance.getAlpha());
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Insert multiple Attendance records in one batch
    public void insertMultipleAttendances(List<Attendance> attendances) {
        String sql = "INSERT INTO Attendance (employee_id, schedule_id, date, quantity, alpha) VALUES (?, ?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false); // Disable auto-commit for batch operation

            PreparedStatement stm = connection.prepareStatement(sql);
            for (Attendance attendance : attendances) {
                stm.setInt(1, attendance.getEmployee().getId());
                stm.setInt(2, attendance.getScheduleCampain().getId());
                stm.setDate(3, attendance.getDate());
                stm.setInt(4, attendance.getQuantity());
                stm.setDouble(5, attendance.getAlpha());
                stm.addBatch();
            }
            stm.executeBatch(); // Execute all statements as a batch
            connection.commit(); // Commit the transaction
        } catch (SQLException ex) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback if there is an error
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Retrieve Attendance records by schedule ID
    public ArrayList<Attendance> getAttendanceByScheduleId(int scheduleId) {
        ArrayList<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT a.id, a.employee_id, a.schedule_id, a.date, a.quantity, a.alpha, " +
                     "e.eid, e.ename, sc.scid, sc.Date AS schedule_date " +
                     "FROM Attendance a " +
                     "JOIN Employee e ON a.employee_id = e.eid " +
                     "JOIN SchedualCampaign sc ON a.schedule_id = sc.scid " +
                     "WHERE a.schedule_id = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, scheduleId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Attendance attendance = new Attendance();

                // Set Attendance fields
                attendance.setId(rs.getInt("id"));
                attendance.setDate(rs.getDate("date"));
                attendance.setQuantity(rs.getInt("quantity"));
                attendance.setAlpha(rs.getDouble("alpha"));

                // Set Employee
                Employee employee = new Employee();
                employee.setId(rs.getInt("employee_id"));
                employee.setName(rs.getString("ename"));
                attendance.setEmployee(employee);

                // Set Schedule Campaign
                ScheduleCampain scheduleCampain = new ScheduleCampain();
                scheduleCampain.setId(rs.getInt("schedule_id"));
                scheduleCampain.setDate(rs.getDate("schedule_date"));
                attendance.setScheduleCampain(scheduleCampain);

                // Add to the list
                attendances.add(attendance);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return attendances;
    }

    // Update Attendance record for an employee in a specific schedule
    public void updateAttendance(Attendance attendance) {
        String sql = "UPDATE Attendance SET quantity = ?, alpha = ? WHERE employee_id = ? AND schedule_id = ? AND date = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, attendance.getQuantity());
            stm.setDouble(2, attendance.getAlpha());
            stm.setInt(3, attendance.getEmployee().getId());
            stm.setInt(4, attendance.getScheduleCampain().getId());
            stm.setDate(5, attendance.getDate());
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Get attendance by employee ID and schedule ID
    public Attendance getAttendanceByEmployeeAndSchedule(int employeeId, int scheduleId) {
        Attendance attendance = null;
        String sql = "SELECT * FROM Attendance WHERE employee_id = ? AND schedule_id = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, employeeId);
            stm.setInt(2, scheduleId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                attendance = new Attendance();
                attendance.setId(rs.getInt("id"));
                attendance.setQuantity(rs.getInt("quantity"));
                attendance.setAlpha(rs.getDouble("alpha"));
                attendance.setDate(rs.getDate("date"));

                // Set Employee
                Employee employee = new Employee();
                employee.setId(rs.getInt("employee_id"));
                attendance.setEmployee(employee);

                // Set ScheduleCampaign
                ScheduleCampain scheduleCampain = new ScheduleCampain();
                scheduleCampain.setId(rs.getInt("schedule_id"));
                attendance.setScheduleCampain(scheduleCampain);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return attendance;
    }

    @Override
    public void insert(Attendance entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Attendance entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Attendance entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Attendance> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Attendance get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
