package dal;

import Schedule.Entity.Attendance;
import Employee.Entity.Employee;
import Plan.Entity.Plan;
import Plan.Entity.PlanCampain;
import Plan.Entity.Product;
import Schedule.Entity.ScheduleCampain;
import Schedule.Entity.ScheduleEmployee;

import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDBContext extends DBContext<Attendance> {

    // Kiểm tra xem ScheduleEmployee có bản ghi attendance hay không
    public boolean isAttendanceRecorded(int scheduleEmployeeId) {
        String sql = "SELECT COUNT(*) AS total FROM Attendence WHERE seid = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, scheduleEmployeeId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Thêm phương thức để chèn nhiều đối tượng Attendance cùng một lúc
    public void insertMultiple(List<Attendance> attendances) {
        String sql = "INSERT INTO Attendence (seid, Quantity, Alpha) VALUES (?, ?, ?)";
        try {
            connection.setAutoCommit(false); // Tắt auto-commit để thực hiện một giao dịch

            PreparedStatement stm = connection.prepareStatement(sql);
            for (Attendance entity : attendances) {
                stm.setInt(1, entity.getScheduleEmployee().getId()); // Tham chiếu tới ScheduleEmployee ID
                stm.setInt(2, entity.getQuantity());
                stm.setDouble(3, entity.getAlpha());
                stm.addBatch();
            }
            stm.executeBatch(); // Thực hiện tất cả các insert
            connection.commit(); // Commit để lưu tất cả các thay đổi
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback nếu có lỗi xảy ra
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
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

    @Override
    public void insert(Attendance entity) {
        String sql = "INSERT INTO Attendence (seid, Quantity, Alpha) VALUES (?, ?, ?)";

        try {
            // Tạo PreparedStatement để thực hiện câu lệnh SQL
            PreparedStatement stm = connection.prepareStatement(sql);

            // Gán các giá trị cho tham số
            stm.setInt(1, entity.getScheduleEmployee().getId()); // Tham chiếu tới ScheduleEmployee ID
            stm.setInt(2, entity.getQuantity()); // Số lượng hoàn thành
            stm.setDouble(3, entity.getAlpha()); // Chỉ số alpha

            // Thực hiện câu lệnh
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Đóng kết nối sau khi sử dụng
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
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
    ArrayList<Attendance> attendances = new ArrayList<>();
    String sql = "SELECT a.aid, se.seid, e.eid, e.ename, sc.scid, sc.Date, sc.Shift, pl.plid, pc.plcid, \n"
            + "       pl.plname, pc.pid, p.pname, se.Quantity AS AssignedQuantity, a.Quantity, a.Alpha \n"
            + "FROM [Attendence] a\n"
            + "JOIN [SchedualEmployee] se ON a.seid = se.seid\n"
            + "JOIN Employee e ON se.eid = e.eid\n"
            + "JOIN [SchedualCampaign] sc ON se.scid = sc.scid\n"
            + "JOIN PlanCampain pc ON sc.plcid = pc.plcid\n"
            + "JOIN [Plan] pl ON pc.plid = pl.plid\n"
            + "JOIN Product p ON pc.pid = p.pid \n"
            + "ORDER BY sc.Date ASC;";

    try (PreparedStatement stm = connection.prepareStatement(sql);
         ResultSet rs = stm.executeQuery()) {
        while (rs.next()) {
            // Tạo đối tượng Employee
            Employee employee = new Employee();
            employee.setId(rs.getInt("eid"));
            employee.setName(rs.getString("ename"));

            // Tạo đối tượng ScheduleCampaign
            ScheduleCampain scheduleCampain = new ScheduleCampain();
            scheduleCampain.setId(rs.getInt("scid"));
            scheduleCampain.setDate(rs.getDate("Date"));
            scheduleCampain.setShift(rs.getInt("Shift"));

            // Tạo đối tượng Plan
            Plan plan = new Plan();
            plan.setId(rs.getInt("plid"));
            plan.setName(rs.getString("plname"));

            // Tạo đối tượng Product
            Product product = new Product();
            product.setId(rs.getInt("pid"));
            product.setName(rs.getString("pname"));

            // Tạo đối tượng PlanCampain và gán các giá trị phù hợp
            PlanCampain planCampain = new PlanCampain();
            planCampain.setId(rs.getInt("plcid"));
            planCampain.setPlan(plan);
            planCampain.setProduct(product);

            // Gán PlanCampain vào ScheduleCampaign
            scheduleCampain.setPlancampain(planCampain);

            // Tạo đối tượng ScheduleEmployee
            ScheduleEmployee scheduleEmployee = new ScheduleEmployee();
            scheduleEmployee.setId(rs.getInt("seid"));
            scheduleEmployee.setQuantity(rs.getInt("AssignedQuantity"));
            scheduleEmployee.setEmployee(employee);
            scheduleEmployee.setSchedulecampain(scheduleCampain);

            // Tạo đối tượng Attendance
            Attendance attendance = new Attendance();
            attendance.setId(rs.getInt("aid"));
            attendance.setScheduleEmployee(scheduleEmployee); // Tham chiếu đến ScheduleEmployee
            attendance.setQuantity(rs.getInt("Quantity"));
            attendance.setAlpha(rs.getDouble("Alpha"));

            // Thêm vào danh sách
            attendances.add(attendance);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return attendances;
}


    @Override
    public Attendance get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
