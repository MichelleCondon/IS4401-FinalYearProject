package com.michelle_condon.is4401_finalyearproject;

//Code below is based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0

public class User {
    //Declaration of global variables
    public String fullname, employeeId, email, password, position;
    public Integer phoneNumber;

    //Empty Constructor
    public User() {
    }

    public User(String email, String employeeId, String fullname, String password, String position, Integer phoneNumber) {
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
}
//End