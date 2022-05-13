package com.example.lionsclubdec;

public class monthlymodel {
    String Time,ID,Date,Total_D;

    public monthlymodel() {
    }

    public monthlymodel(String time, String ID, String date, String total_D) {
        Time = time;
        this.ID = ID;
        Date = date;
        Total_D = total_D;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTotal_D() {
        return Total_D;
    }

    public void setTotal_D(String total_D) {
        Total_D = total_D;
    }
}
