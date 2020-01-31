package com.example.scoretastic;
public class Recycler {
    String sports;
    String location;
    String time;
    String date;

    public Recycler(String sports, String location, String time, String date) {
        this.sports = sports;
        this.location = location;
        this.time = time;
        this.date = date;
    }

    public Recycler() {
    }

    public String getSports() {
        return sports;
    }

    public void setSports(String sports) {
        this.sports = sports;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
