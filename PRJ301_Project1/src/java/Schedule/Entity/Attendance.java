/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Schedule.Entity;

import Employee.Entity.Employee;
import java.sql.*;

/**
 *
 * @author LEGION
 */
public class Attendance {
    private int id;
    private Employee employee;
    private ScheduleCampain scheduleCampain;
    private Date date;  
    private double alpha; // Ratio of work completed (0.0 to 1.0)
    private int quantity; // Quantity of products completed on the given date


    // Constructors
    public Attendance() {}

    public Attendance(Employee employee, ScheduleCampain scheduleCampain, Date date, double alpha, int quantity) {
        this.employee = employee;
        this.scheduleCampain = scheduleCampain;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ScheduleCampain getScheduleCampain() {
        return scheduleCampain;
    }

    public void setScheduleCampain(ScheduleCampain scheduleCampain) {
        this.scheduleCampain = scheduleCampain;
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
