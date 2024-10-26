package Schedule.Entity;

import Employee.Entity.Employee;
import java.util.ArrayList;

public class ScheduleEmployee {

    private int id;
    private ScheduleCampain schedulecampain;
    private int quantity;
    private ArrayList<Employee> employee = new ArrayList<>();
    


    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for schedulecampain
    public ScheduleCampain getSchedulecampain() {
        return schedulecampain;
    }

    public void setSchedulecampain(ScheduleCampain schedulecampain) {
        this.schedulecampain = schedulecampain;
    }

    // Getter and Setter for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter and Setter for employee list
    public ArrayList<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(ArrayList<Employee> employee) {
        this.employee = employee;
    }

    // New getters for properties derived from nested objects
    public String getPlanName() {
        if (schedulecampain != null && schedulecampain.getPlancampain() != null
                && schedulecampain.getPlancampain().getPlan() != null) {
            return schedulecampain.getPlancampain().getPlan().getName();
        }
        return null;
    }

    public int getPlanCampainId() {
        if (schedulecampain != null && schedulecampain.getPlancampain() != null) {
            return schedulecampain.getPlancampain().getId();
        }
        return -1;
    }

    public String getDepartmentName() {
        if (schedulecampain != null && schedulecampain.getPlancampain() != null
                && schedulecampain.getPlancampain().getPlan() != null
                && schedulecampain.getPlancampain().getPlan().getDept() != null) {
            return schedulecampain.getPlancampain().getPlan().getDept().getName();
        }
        return null;
    }

    public java.sql.Date getDate() {
        if (schedulecampain != null) {
            return schedulecampain.getDate();
        }
        return null;
    }

    public int getShift() {
        if (schedulecampain != null) {
            return schedulecampain.getShift();
        }
        return -1;
    }

}
