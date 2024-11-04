package Employee.Entity;

import Login.Entity.User;
import Schedule.Entity.Attendance;

import java.sql.Date;
import java.util.ArrayList;

public class Employee {

    private int id;
    private String name;
    private int additionalBonus;
    private boolean gender;
    private String address;
    private Date dob;
    private double salary;
    private boolean iswork;
    private User createdby;
    private User updatedby;
    private java.util.Date updatedtime;
    private Department dept;
    private ArrayList<Attendance> attendances = new ArrayList<>();

    public int getAdditionalBonus() {
        return additionalBonus;
    }

    public void setAdditionalBonus(int additionalBonus) {
        this.additionalBonus = additionalBonus;
    }

    // Getter and Setter for Attendances
    public ArrayList<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(ArrayList<Attendance> attendances) {
        this.attendances = attendances;
    }

    // Getter and Setter for Salary
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Getter and Setter for Department
    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    // Getter and Setter for ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for Gender
    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    // Getter and Setter for Address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getter and Setter for Date of Birth
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    // Getter and Setter for Work Status
    public boolean isIswork() {
        return iswork;
    }

    public void setIswork(boolean iswork) {
        this.iswork = iswork;
    }

    // Getter and Setter for Created By User
    public User getCreatedby() {
        return createdby;
    }

    public void setCreatedby(User createdby) {
        this.createdby = createdby;
    }

    // Getter and Setter for Updated By User
    public User getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(User updatedby) {
        this.updatedby = updatedby;
    }

    // Getter and Setter for Updated Time
    public java.util.Date getUpdatedtime() {
        return updatedtime;
    }

    public void setUpdatedtime(java.util.Date updatedtime) {
        this.updatedtime = updatedtime;
    }
}
