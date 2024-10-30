package Schedule.Entity;

import Plan.Entity.PlanCampain;
import Plan.Entity.Product;
import java.sql.*;
import java.util.ArrayList;

public class ScheduleCampain {

    private int id;
    private PlanCampain plancampain;
    private Date date;
    private int shift;
    private int quantity;
    private Product product;
    private ArrayList<ScheduleEmployee> scheduleemployee = new ArrayList<>();
    private int assignedQuantity;

    public int getAssignedQuantity() {
        return assignedQuantity;
    }

    public void setAssignedQuantity(int assignedQuantity) {
        this.assignedQuantity = assignedQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlanCampain getPlancampain() {
        return plancampain;
    }

    public void setPlancampain(PlanCampain plancampain) {
        this.plancampain = plancampain;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ArrayList<ScheduleEmployee> getScheduleemployee() {
        return scheduleemployee;
    }

    public void setScheduleemployee(ArrayList<ScheduleEmployee> scheduleemployee) {
        this.scheduleemployee = scheduleemployee;
    }

}
