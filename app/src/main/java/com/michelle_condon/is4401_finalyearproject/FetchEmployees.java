package com.michelle_condon.is4401_finalyearproject;

//Code below is based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0

public class FetchEmployees {
    //Declare variables
    String employeeName;
    String monday;
    String tuesday;
    String wednesday;
    String thursday;
    String friday;

    public FetchEmployees() {
    }

    public FetchEmployees(String employeeName, String monday, String tuesday, String wednesday, String thursday, String friday) {
        this.employeeName = employeeName;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeId) {
        this.employeeName = employeeName;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }
}
//End