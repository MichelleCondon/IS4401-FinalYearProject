package com.michelle_condon.is4401_finalyearproject;

public class FetchRequests {

    String dates;
    String employeeEmail;

    public FetchRequests() {
    }

    public FetchRequests(String dates, String employeeEmail) {
        this.dates = dates;
        this.employeeEmail = employeeEmail;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
}
