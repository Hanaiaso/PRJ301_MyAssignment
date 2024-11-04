package Schedule.Entity;

import Employee.Entity.Employee;
import java.sql.*;

/**
 *
 * @author LEGION
 */
public class Attendance {

    private int id;
    private ScheduleEmployee scheduleEmployee; // Thêm trường ScheduleEmployee
    private Date date;
    private double alpha; // Ratio of work completed (0.0 to 1.0)
    private int quantity; // Quantity of products completed on the given date

    // Constructors
    public Attendance() {
    }

    public Attendance(ScheduleEmployee scheduleEmployee, Date date, double alpha, int quantity) {
        this.scheduleEmployee = scheduleEmployee;
        this.date = date;
        this.alpha = alpha;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ScheduleEmployee getScheduleEmployee() {
        return scheduleEmployee;
    }

    public void setScheduleEmployee(ScheduleEmployee scheduleEmployee) {
        this.scheduleEmployee = scheduleEmployee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
