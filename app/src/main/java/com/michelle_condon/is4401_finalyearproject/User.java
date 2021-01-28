package com.michelle_condon.is4401_finalyearproject;

public class User {
    //Declaration of global variables
    public String fullname, employeeId, email;

    //Empty Constructor
    public User() {
    }

    public User(String email, String employeeId, String fullname) {
        this.email = email;
        this.fullname = fullname;
        this.employeeId = employeeId;
    }
}
