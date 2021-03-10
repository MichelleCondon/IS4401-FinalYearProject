package com.michelle_condon.is4401_finalyearproject.Models;


public class Timesheets {

    //Code below is based on a YouTube Video, by Learn with Deeksha, https://www.youtube.com/watch?v=lJaPdBMdPy0

    private String employee;
    private String In;
    private String Break;
    private String EndBreak;
    private String Out;


    public Timesheets(String employee, String In, String Break, String EndBreak, String Out) {
        this.employee = employee;
        this.In = In;
        this.Break = Break;
        this.EndBreak = EndBreak;
        this.Out = Out;

    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getIn() {
        return In;
    }

    public void setIn(String in) {
        In = in;
    }

    public String getBreak() {
        return Break;
    }

    public void setBreak(String aBreak) {
        Break = aBreak;
    }

    public String getEndBreak() {
        return EndBreak;
    }

    public void setEndBreak(String endBreak) {
        EndBreak = endBreak;
    }

    public String getOut() {
        return Out;
    }

    public void setOut(String out) {
        Out = out;
    }

    public Timesheets(){

    }
}
//End