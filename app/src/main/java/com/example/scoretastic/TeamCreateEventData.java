package com.example.scoretastic;

import java.sql.Time;
import java.util.Date;

public class TeamCreateEventData {
    String sports;
    String variation;
    String venue;
    String description;
    String resultLocation;
    double resultLng;
    double resultLat;
    long id;
    int timeHour;
    int timeMinute;
    String uid;
    int day;
    int month;
    int year;

    public TeamCreateEventData() {
    }

    public TeamCreateEventData(String sports, String variation, String venue, String description, String resultLocation, double resultLng, double resultLat, long id, int timeHour, int timeMinute, String uid, int day, int month, int year) {
        this.sports = sports;
        this.variation = variation;
        this.venue = venue;
        this.description = description;
        this.resultLocation = resultLocation;
        this.resultLng = resultLng;
        this.resultLat = resultLat;
        this.id = id;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.uid = uid;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getSports() {
        return sports;
    }

    public void setSports(String sports) {
        this.sports = sports;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(int timeHour) {
        this.timeHour = timeHour;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(int timeMinute) {
        this.timeMinute = timeMinute;
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
}
