package com.michelle_condon.is4401_finalyearproject.Models;

//Code below is based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0

public class User {
    //Declaration of global variables
    public String fullname, employeeId, email, password, position;
    public String phoneNumber;

    //Empty Constructor
    public User() {
    }

    public User(String email, String employeeId, String fullname, String password, String position, String phoneNumber) {
        this.email = email;
        this.fullname = fullname;
        this.employeeId = employeeId;
        this.password = password;
        this.position = position;
        this.phoneNumber = phoneNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
//End