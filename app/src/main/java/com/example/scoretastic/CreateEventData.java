package com.example.scoretastic;

import java.util.Date;
import java.sql.Time;

public class CreateEventData {
    String resultLocation;
    double resultLng;
    double resultLat;
    String description;
    int day;
    int month;
    int year;
    String Sports;
    int totalPlayers;
    int timeHour;
    int timeMinute;
    long id;
    int status;
    String uid;

    public CreateEventData() {
    }

    public CreateEventData(String resultLocation, double resultLng, double resultLat, String description, int day, int month, int year, String sports, int totalPlayers, int timeHour, int timeMinute, long id, int status, String uid) {
        this.resultLocation = resultLocation;
        this.resultLng = resultLng;
        this.resultLat = resultLat;
        this.description = description;
        this.day = day;
        this.month = month;
        this.year = year;
        Sports = sports;
        this.totalPlayers = totalPlayers;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.id = id;
        this.status = status;
        this.uid = uid;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}