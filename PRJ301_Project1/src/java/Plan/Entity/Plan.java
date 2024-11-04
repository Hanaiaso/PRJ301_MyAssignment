package Plan.Entity;

import Employee.Entity.Department;
import java.sql.Date;
import java.util.ArrayList;

public class Plan {

    private int id;
    private String name;
    private Date start;
    private Date end;
    private Department dept;
    private boolean isDone;
    private ArrayList<PlanCampain> campains = new ArrayList<>();
    private int totalProducts; // Tổng số sản phẩm
    private int completedProducts; // Số sản phẩm đã hoàn thành

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getCompletedProducts() {
        return completedProducts;
    }

    public void setCompletedProducts(int completedProducts) {
        this.completedProducts = completedProducts;
    }

    public Plan() {
    }

    public Plan(String name, Date start, Date end, Department dept) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.dept = dept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public boolean isIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public ArrayList<PlanCampain> getCampains() {
        return campains;
    }

    public void setCampains(ArrayList<PlanCampain> campains) {
        this.campains = campains;
    }
}
