/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Schedule.Entity;

import Employee.Entity.Employee;
import java.util.ArrayList;

/**
 *
 * @author LEGION
 */
public class ScheduleEmployee {
    private int id;
    private ScheduleCampain schedulecampain;
    private int quantity;
    private ArrayList<Employee> employee = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ScheduleCampain getSchedulecampain() {
        return schedulecampain;
    }

    public void setSchedulecampain(ScheduleCampain schedulecampain) {
        this.schedulecampain = schedulecampain;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ArrayList<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(ArrayList<Employee> employee) {
        this.employee = employee;
    }
    
}
