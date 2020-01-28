package com.example.scoretastic;

import java.sql.Date;
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
    int batsman;
    int bowlers;
    int allRounder;
    int wicketKeeper;
    int keeper;
    int defender;
    int attacker;
    int midfielder;
    int timeHour;
    int timeMinute;

    public CreateEventData() {
    }

    public CreateEventData(String resultLocation, double resultLng, double resultLat, String description, Date date, Time time, String sports, int totalPlayers, int batsman, int bowlers, int allRounder, int wicketKeeper, int keeper, int defender, int attacker, int midfielder, int timeHour, int timeMinute) {
        this.resultLocation = resultLocation;
        this.resultLng = resultLng;
        this.resultLat = resultLat;
        this.description = description;
        this.date = date;
        this.time = time;
        Sports = sports;
        this.totalPlayers = totalPlayers;
        this.batsman = batsman;
        this.bowlers = bowlers;
        this.allRounder = allRounder;
        this.wicketKeeper = wicketKeeper;
        this.keeper = keeper;
        this.defender = defender;
        this.attacker = attacker;
        this.midfielder = midfielder;
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

    public int getBatsman() {
        return batsman;
    }

    public void setBatsman(int batsman) {
        this.batsman = batsman;
    }

    public int getBowlers() {
        return bowlers;
    }

    public void setBowlers(int bowlers) {
        this.bowlers = bowlers;
    }

    public int getAllRounder() {
        return allRounder;
    }

    public void setAllRounder(int allRounder) {
        this.allRounder = allRounder;
    }

    public int getWicketKeeper() {
        return wicketKeeper;
    }

    public void setWicketKeeper(int wicketKeeper) {
        this.wicketKeeper = wicketKeeper;
    }

    public int getKeeper() {
        return keeper;
    }

    public void setKeeper(int keeper) {
        this.keeper = keeper;
    }

    public int getDefender() {
        return defender;
    }

    public void setDefender(int defender) {
        this.defender = defender;
    }

    public int getAttacker() {
        return attacker;
    }

    public void setAttacker(int attacker) {
        this.attacker = attacker;
    }

    public int getMidfielder() {
        return midfielder;
    }

    public void setMidfielder(int midfielder) {
        this.midfielder = midfielder;
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
