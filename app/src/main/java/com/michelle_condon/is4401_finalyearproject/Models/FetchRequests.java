package com.michelle_condon.is4401_finalyearproject.Models;

public class FetchRequests {

    String dates;
    String employeeEmail;
    String empHolidayName;
    String status;

    public FetchRequests() {
    }

    public FetchRequests(String dates, String employeeEmail, String empName, String empHolidayName) {
        this.dates = dates;
        this.employeeEmail = employeeEmail;
        this.empHolidayName = empHolidayName;
        this.status = status;
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

    public String getEmpHolidayName() {
        return empHolidayName;
    }

    public void setEmpHolidayName(String empHolidayName) {
        this.empHolidayName = empHolidayName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
