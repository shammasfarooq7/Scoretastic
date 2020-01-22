package com.example.scoretastic;

import java.sql.Date;
import java.sql.Time;

public class recycler {
    String sports;
    String location;
    Time time;
    Date date;

    public recycler(String sports, String location, Time time, Date date) {
        this.sports = sports;
        this.location = location;
        this.time = time;
        this.date = date;
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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
