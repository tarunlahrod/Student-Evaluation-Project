package com.example.studentevaluationproject;

public class StudentAttendanceRow {
    private String name;
    private String roll;
    private String attendance;
    private String percentage;

    public StudentAttendanceRow(String name, String roll, String attendance, String percentage) {
        this.name = name;
        this.roll = roll;
        this.attendance = attendance;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
