/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Plan.Entity;

import Schedule.Entity.ScheduleCampain;
import java.util.ArrayList;

/**
 *
 * @author LEGION
 */
public class PlanCampain {
    private int id;
    private Plan plan;
    private Product product;
    private int quantity;
    private float cost;
    private ArrayList<ScheduleCampain> schedulecampain = new ArrayList<>();

    public ArrayList<ScheduleCampain> getSchedulecampain() {
        return schedulecampain;
    }

    public void setSchedulecampain(ArrayList<ScheduleCampain> schedulecampain) {
        this.schedulecampain = schedulecampain;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
    
}
