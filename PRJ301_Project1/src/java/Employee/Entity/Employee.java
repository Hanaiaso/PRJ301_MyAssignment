package Employee.Entity;
import Login.Entity.User;
import java.sql.*;
public class Employee {
    private int id;
    private String name;
    private boolean gender;
    private String address;
    private Date dob;
    private double salary;
    private boolean iswork;
    private User createdby;
    private User updatedby;
    private java.util.Date updatedtime; 
    private Department dept;
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public Department getDept() {
        return dept;
    }
    public void setDept(Department dept) {
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
    public boolean isGender() {
        return gender;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public boolean isIswork() {
        return iswork;
    }
    public void setIswork(boolean iswork) {
        this.iswork = iswork;
    }
    public User getCreatedby() {
        return createdby;
    }
    public void setCreatedby(User createdby) {
        this.createdby = createdby;
    }
    public User getUpdatedby() {
        return updatedby;
    }
    public void setUpdatedby(User updatedby) {
        this.updatedby = updatedby;
    }
    public java.util.Date getUpdatedtime() {
        return updatedtime;
    }
    public void setUpdatedtime(java.util.Date updatedtime) {
        this.updatedtime = updatedtime;
    }     
}
