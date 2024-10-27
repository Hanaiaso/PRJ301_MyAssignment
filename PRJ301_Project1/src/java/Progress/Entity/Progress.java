/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Progress.Entity;

import Plan.Entity.Plan;
import java.util.Date;

/**
 *
 * @author LEGION
 */
public class Progress {
    private Plan plan;
    private Date startDate;
    private Date endDate;
    private Date progressDate; // Ngày theo dõi tiến độ
    private int totalProducts;
    private int completedProducts;
    private int remainingProducts;
    private String status;

    // Getters và setters cho thuộc tính mới
    public Date getProgressDate() {
        return progressDate;
    }

    public void setProgressDate(Date progressDate) {
        this.progressDate = progressDate;
    }

    // Các getter và setter khác
    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
    
    
}
