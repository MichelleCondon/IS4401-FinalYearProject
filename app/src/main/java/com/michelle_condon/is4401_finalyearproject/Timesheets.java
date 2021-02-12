package com.michelle_condon.is4401_finalyearproject;


public class Timesheets {

    private String employeeName;
    private String In;
    private String Break;
    private String EndBreak;
    private String Out;


    public Timesheets(String employeeName, String In, String Break, String EndBreak, String Out) {
        this.employeeName = employeeName;
        this.In = In;
        this.Break = Break;
        this.EndBreak = EndBreak;
        this.Out = Out;

    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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
