package com.example.scoretastic;

import java.util.Date;
import java.sql.Time;

public class CreateEventData {
    String resultLocation;
    double resultLng;
    double resultLat;
    String description;
    Date date;
    Time time;
    String Sports;
    int totalPlayers;
    int timeHour;
    int timeMinute;

    public CreateEventData() {
    }

    public CreateEventData(String resultLocation, double resultLng, double resultLat, String description, Date date, Time time, String sports, int totalPlayers, int timeHour, int timeMinute) {
        this.resultLocation = resultLocation;
        this.resultLng = resultLng;
        this.resultLat = resultLat;
        this.description = description;
        this.date = date;
        this.time = time;
        Sports = sports;
        this.totalPlayers = totalPlayers;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
    }

    public String getResultLocation() {
        return resultLocation;
    }

    public void setResultLocation(String resultLocation) {
        this.resultLocation = resultLocation;
    }

    public double getResultLng() {
        return resultLng;
    }

    public void setResultLng(double resultLng) {
        this.resultLng = resultLng;
    }

    public double getResultLat() {
        return resultLat;
    }

    public void setResultLat(double resultLat) {
        this.resultLat = resultLat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getSports() {
        return Sports;
    }

    public void setSports(String sports) {
        Sports = sports;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public int getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(int timeHour) {
        this.timeHour = timeHour;
    }

    public int getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(int timeMinute) {
        this.timeMinute = timeMinute;
    }
}
