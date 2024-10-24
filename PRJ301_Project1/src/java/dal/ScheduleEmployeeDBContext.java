package dal;
import Employee.Entity.Department;
import Employee.Entity.Employee;
import Plan.Entity.Plan;
import Plan.Entity.PlanCampain;
import Schedule.Entity.ScheduleCampain;
import Schedule.Entity.ScheduleEmployee;
import java.util.ArrayList;
import java.sql.*;
import java.util.List;
public class ScheduleEmployeeDBContext extends DBContext<ScheduleEmployee>{
   public ArrayList<ScheduleEmployee> getAllAssignedEmployees() {
    ArrayList<ScheduleEmployee> assignedEmployees = new ArrayList<>();
    String sql = "SELECT se.scid, se.eid, se.Quantity, sc.Date, sc.Shift, " +
                 "pl.plname, pc.plcid, d.dname, e.eid, e.ename " +
                 "FROM SchedualEmployee se " +
                 "JOIN SchedualCampaign sc ON se.scid = sc.scid " +
                 "JOIN PlanCampain pc ON sc.plcid = pc.plcid " +
                 "JOIN [Plan] pl ON pc.plid = pl.plid " +
                 "JOIN [Department] d ON pl.did = d.did " +
                 "JOIN Employee e ON se.eid = e.eid " +
                 "ORDER BY pl.plname, sc.Date, sc.Shift, e.ename";

    try {
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            // Create new ScheduleEmployee for each row in the result set
            ScheduleEmployee scheduleEmployee = new ScheduleEmployee();
            scheduleEmployee.setQuantity(rs.getInt("Quantity"));

            // Create ScheduleCampain object
            ScheduleCampain scheduleCampain = new ScheduleCampain();
            scheduleCampain.setId(rs.getInt("scid"));
            scheduleCampain.setDate(rs.getDate("Date"));
            scheduleCampain.setShift(rs.getInt("Shift"));

            // Create Plan object
            Plan plan = new Plan();
            plan.setName(rs.getString("plname"));

            // Set Plan to PlanCampain and PlanCampain to ScheduleCampain
            PlanCampain planCampain = new PlanCampain();
            planCampain.setId(rs.getInt("plcid"));
            planCampain.setPlan(plan);
            scheduleCampain.setPlancampain(planCampain);

            // Set ScheduleCampain to ScheduleEmployee
            scheduleEmployee.setSchedulecampain(scheduleCampain);

            // Create Employee object and add to ScheduleEmployee
            Employee employee = new Employee();
            employee.setId(rs.getInt("eid"));
            employee.setName(rs.getString("ename"));
            ArrayList<Employee> employeeList = new ArrayList<>();
            employeeList.add(employee);
            scheduleEmployee.setEmployee(employeeList);

            // Set department name for ScheduleEmployee
            scheduleEmployee.getSchedulecampain().getPlancampain().getPlan().setDept(new Department());
            scheduleEmployee.getSchedulecampain().getPlancampain().getPlan().getDept().setName(rs.getString("dname"));

            // Add to the list
            assignedEmployees.add(scheduleEmployee);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return assignedEmployees;
}
    public void insertAssignment(int scid, int eid, int quantity) {
        String sql = "INSERT INTO SchedualEmployee (scid, eid, Quantity) VALUES (?, ?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, scid);
            stm.setInt(2, eid);
            stm.setInt(3, quantity);
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
public void insertMultipleAssignments(int scid, List<Integer> eids, List<Integer> quantities) {
    String sql = "INSERT INTO SchedualEmployee (scid, eid, Quantity) VALUES (?, ?, ?)";
    try {
        connection.setAutoCommit(false); // Tắt auto-commit để thực hiện nhiều insert trong cùng 1 giao dịch

        PreparedStatement stm = connection.prepareStatement(sql);
        for (int i = 0; i < eids.size(); i++) {
            stm.setInt(1, scid);
            stm.setInt(2, eids.get(i));
            stm.setInt(3, quantities.get(i));
            stm.addBatch();
        }
        stm.executeBatch(); // Thực hiện tất cả các insert
        connection.commit(); // Commit để lưu các thay đổi vào database
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

    public boolean isEmployeeAssigned(int scid, int eid) {
        String sql = "SELECT COUNT(*) AS total FROM SchedualEmployee WHERE scid = ? AND eid = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, scid);
            stm.setInt(2, eid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void insert(ScheduleEmployee entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public void update(ScheduleEmployee entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public void delete(ScheduleEmployee entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public ArrayList<ScheduleEmployee> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public ScheduleEmployee get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
