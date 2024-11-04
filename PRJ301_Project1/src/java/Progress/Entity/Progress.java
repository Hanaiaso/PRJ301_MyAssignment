/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Progress.Entity;

import Employee.Entity.Department;
import Plan.Entity.Plan;
import java.util.Date;
import java.util.List;

/**
 *
 * @author LEGION
 */
public class Progress {

    private Plan plan;
    private int totalProducts; // Tổng số sản phẩm đã giao
    private int completedProducts; // Tổng số sản phẩm đã hoàn thành
    private int remainingProducts; // Số sản phẩm còn lại
    private String status; // Trạng thái kế hoạch: Hoàn thành, Đang tiến hành, Muộn
    private Department department; // Bộ phận liên quan đến kế hoạch

    // Getters và setters cho tất cả các thuộc tính
    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

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

    public int getRemainingProducts() {
        return remainingProducts;
    }

    public void setRemainingProducts(int remainingProducts) {
        this.remainingProducts = remainingProducts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
