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

public class ScheduleEmployeeDBContext extends DBContext<ScheduleEmployee> {

    public ArrayList<ScheduleEmployee> getEmployeesByScheduleId(int scheduleId) {
        ArrayList<ScheduleEmployee> assignedEmployees = new ArrayList<>();
        String sql = "SELECT se.seid, se.scid, se.Quantity, sc.Date, sc.Shift, "
                + "pl.plid, pl.plname, pc.plcid, d.did, d.dname, d.type, e.eid, e.ename, e.salary "
                + "FROM SchedualEmployee se "
                + "JOIN SchedualCampaign sc ON se.scid = sc.scid "
                + "JOIN PlanCampain pc ON sc.plcid = pc.plcid "
                + "JOIN [Plan] pl ON pc.plid = pl.plid "
                + "JOIN [Department] d ON pl.did = d.did "
                + "JOIN Employee e ON se.eid = e.eid "
                + "WHERE e.isWork = 1 AND sc.scid = ? "
                + "ORDER BY e.ename";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, scheduleId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    // Tạo đối tượng ScheduleEmployee
                    ScheduleEmployee scheduleEmployee = new ScheduleEmployee();
                    scheduleEmployee.setId(rs.getInt("seid")); // Gán giá trị ID đúng từ kết quả truy vấn
                    scheduleEmployee.setQuantity(rs.getInt("Quantity"));

                    // Tạo đối tượng ScheduleCampain
                    ScheduleCampain scheduleCampain = new ScheduleCampain();
                    scheduleCampain.setId(rs.getInt("scid"));
                    scheduleCampain.setDate(rs.getDate("Date"));
                    scheduleCampain.setShift(rs.getInt("Shift"));

                    // Tạo đối tượng Plan
                    Plan plan = new Plan();
                    plan.setId(rs.getInt("plid"));
                    plan.setName(rs.getString("plname"));

                    // Tạo đối tượng PlanCampain
                    PlanCampain planCampain = new PlanCampain();
                    planCampain.setId(rs.getInt("plcid"));
                    planCampain.setPlan(plan);

                    // Gán PlanCampain vào ScheduleCampain
                    scheduleCampain.setPlancampain(planCampain);

                    // Gán ScheduleCampain vào ScheduleEmployee
                    scheduleEmployee.setSchedulecampain(scheduleCampain);

                    // Tạo đối tượng Employee và gán vào ScheduleEmployee
                    Employee employee = new Employee();
                    employee.setId(rs.getInt("eid"));
                    employee.setName(rs.getString("ename"));
                    scheduleEmployee.setEmployee(employee);

                    // Thêm ScheduleEmployee vào danh sách
                    assignedEmployees.add(scheduleEmployee);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return assignedEmployees;
    }

    public ArrayList<ScheduleEmployee> getAllAssignedEmployees() {
        ArrayList<ScheduleEmployee> assignedEmployees = new ArrayList<>();
        String sql = "SELECT se.scid, se.eid, se.Quantity, sc.Date, sc.Shift, "
                + "pl.plname, pc.plcid, d.dname, e.eid, e.ename "
                + "FROM SchedualEmployee se "
                + "JOIN SchedualCampaign sc ON se.scid = sc.scid "
                + "JOIN PlanCampain pc ON sc.plcid = pc.plcid "
                + "JOIN [Plan] pl ON pc.plid = pl.plid "
                + "JOIN [Department] d ON pl.did = d.did "
                + "JOIN Employee e ON se.eid = e.eid "
                + "ORDER BY pl.plname, sc.Date, sc.Shift, e.ename";

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
                //ArrayList<Employee> employeeList = new ArrayList<>();
                //employeeList.add(employee);
                scheduleEmployee.setEmployee(employee);

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
        ArrayList<ScheduleEmployee> assignedEmployees = new ArrayList<>();
        String sql = "SELECT se.scid, se.eid, se.Quantity, sc.Date, sc.Shift, "
                + "pl.plid, pl.plname, pc.plcid, d.did, d.dname, e.eid, e.ename "
                + "FROM SchedualEmployee se "
                + "JOIN SchedualCampaign sc ON se.scid = sc.scid "
                + "JOIN PlanCampain pc ON sc.plcid = pc.plcid "
                + "JOIN [Plan] pl ON pc.plid = pl.plid "
                + "JOIN [Department] d ON pl.did = d.did "
                + "JOIN Employee e ON se.eid = e.eid "
                + "ORDER BY pl.plname, sc.Date, sc.Shift, e.ename";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                // Check if there's an existing ScheduleEmployee with the same scid in the list
                ScheduleEmployee scheduleEmployee = null;
                for (ScheduleEmployee se : assignedEmployees) {
                    if (se.getSchedulecampain().getId() == rs.getInt("scid")) {
                        scheduleEmployee = se;
                        break;
                    }
                }

                // If no existing ScheduleEmployee, create a new one
                if (scheduleEmployee == null) {
                    scheduleEmployee = new ScheduleEmployee();
                    scheduleEmployee.setQuantity(rs.getInt("Quantity"));

                    // Create ScheduleCampain object
                    ScheduleCampain scheduleCampain = new ScheduleCampain();
                    scheduleCampain.setId(rs.getInt("scid"));
                    scheduleCampain.setDate(rs.getDate("Date"));
                    scheduleCampain.setShift(rs.getInt("Shift"));

                    // Create Plan object
                    Plan plan = new Plan();
                    plan.setId(rs.getInt("plid"));
                    plan.setName(rs.getString("plname"));

                    // Create Department object
                    Department dept = new Department();
                    dept.setId(rs.getInt("did"));
                    dept.setName(rs.getString("dname"));

                    // Set Plan and Department to PlanCampain and PlanCampain to ScheduleCampain
                    PlanCampain planCampain = new PlanCampain();
                    planCampain.setId(rs.getInt("plcid"));
                    planCampain.setPlan(plan);
                    plan.setDept(dept);
                    scheduleCampain.setPlancampain(planCampain);

                    // Set ScheduleCampain to ScheduleEmployee
                    scheduleEmployee.setSchedulecampain(scheduleCampain);

                    // Add to the list
                    assignedEmployees.add(scheduleEmployee);
                }

                // Create Employee object and add to ScheduleEmployee
                Employee employee = new Employee();
                employee.setId(rs.getInt("eid"));
                employee.setName(rs.getString("ename"));
                scheduleEmployee.getEmployee();
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

        return assignedEmployees;
    }

    @Override
    public ScheduleEmployee get(int id) {
        ScheduleEmployee scheduleEmployee = null;
        String sql = "SELECT se.scid, se.eid, se.Quantity, sc.Date, sc.Shift, "
                + "pl.plid, pl.plname, pc.plcid, d.did, d.dname, e.eid, e.ename "
                + "FROM SchedualEmployee se "
                + "JOIN SchedualCampaign sc ON se.scid = sc.scid "
                + "JOIN PlanCampain pc ON sc.plcid = pc.plcid "
                + "JOIN [Plan] pl ON pc.plid = pl.plid "
                + "JOIN [Department] d ON pl.did = d.did "
                + "JOIN Employee e ON se.eid = e.eid "
                + "WHERE se.scid = ?";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                // Create ScheduleEmployee
                scheduleEmployee = new ScheduleEmployee();
                scheduleEmployee.setId(rs.getInt("scid"));
                scheduleEmployee.setQuantity(rs.getInt("Quantity"));

                // Create ScheduleCampain object
                ScheduleCampain scheduleCampain = new ScheduleCampain();
                scheduleCampain.setId(rs.getInt("scid"));
                scheduleCampain.setDate(rs.getDate("Date"));
                scheduleCampain.setShift(rs.getInt("Shift"));

                // Create Plan object
                Plan plan = new Plan();
                plan.setId(rs.getInt("plid"));
                plan.setName(rs.getString("plname"));

                // Create Department object
                Department dept = new Department();
                dept.setId(rs.getInt("did"));
                dept.setName(rs.getString("dname"));

                // Set Plan and Department to PlanCampain and PlanCampain to ScheduleCampain
                PlanCampain planCampain = new PlanCampain();
                planCampain.setId(rs.getInt("plcid"));
                planCampain.setPlan(plan);
                plan.setDept(dept);
                scheduleCampain.setPlancampain(planCampain);

                // Set ScheduleCampain to ScheduleEmployee
                scheduleEmployee.setSchedulecampain(scheduleCampain);

                // Create Employee object and add to ScheduleEmployee
                Employee employee = new Employee();
                employee.setId(rs.getInt("eid"));
                employee.setName(rs.getString("ename"));
                //ArrayList<Employee> employees = new ArrayList<>();
                //employees.add(employee);
                scheduleEmployee.setEmployee(employee);
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

        return scheduleEmployee;
    }
}
