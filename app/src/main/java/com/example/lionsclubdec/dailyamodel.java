package com.example.lionsclubdec;

public class dailyamodel {
    String ID ,Time, Total_D;

    public dailyamodel() {
    }

    public dailyamodel(String ID, String time, String total_D) {
        this.ID = ID;
        Time = time;
        Total_D = total_D;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTotal_D() {
        return Total_D;
    }

    public void setTotal_D(String total_D) {
        Total_D = total_D;
    }
}
