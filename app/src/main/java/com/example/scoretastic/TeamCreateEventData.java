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
    Date date;
    Time time;
    long id;
    int timeHour;
    int timeMinute;

    public TeamCreateEventData() {
    }

    public TeamCreateEventData(String sports, String variation, String venue, String description, String resultLocation, double resultLng, double resultLat, Date date, Time time, long id, int timeHour, int timeMinute) {
        this.sports = sports;
        this.variation = variation;
        this.venue = venue;
        this.description = description;
        this.resultLocation = resultLocation;
        this.resultLng = resultLng;
        this.resultLat = resultLat;
        this.date = date;
        this.time = time;
        this.id = id;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
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

    public int getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(int timeMinute) {
        this.timeMinute = timeMinute;
    }
}
