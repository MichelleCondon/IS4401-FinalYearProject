package com.michelle_condon.is4401_finalyearproject.Models;

public class Hours {

    private String date;
    private String day;
    private String hours;
    private String name;
    private String month;
    private String email;

    public Hours(String date, String day, String hours, String name, String month, String email) {
        this.date = date;
        this.day = day;
        this.hours = hours;
        this.name = name;
        this.month = month;
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Hours() {

    }
}
