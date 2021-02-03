package com.michelle_condon.is4401_finalyearproject;

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
}
