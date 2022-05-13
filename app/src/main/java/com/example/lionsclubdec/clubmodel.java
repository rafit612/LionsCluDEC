package com.example.lionsclubdec;

public class clubmodel {
    String Club,Code;

    public clubmodel() {
    }

    public clubmodel(String club, String code) {
        Club = club;
        Code = code;
    }

    public String getClub() {
        return Club;
    }

    public void setClub(String club) {
        Club = club;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
