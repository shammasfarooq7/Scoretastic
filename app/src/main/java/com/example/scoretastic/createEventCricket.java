package com.example.scoretastic;

import java.sql.Time;
import java.util.Date;

public class createEventCricket extends CreateEventData {
    int batsman;
    int bowlers;
    int allRounder;
    int wicketKeeper;

    public createEventCricket() {
    }

    public createEventCricket(int batsman, int bowlers, int allRounder, int wicketKeeper) {
        this.batsman = batsman;
        this.bowlers = bowlers;
        this.allRounder = allRounder;
        this.wicketKeeper = wicketKeeper;
    }

    public createEventCricket(String resultLocation, double resultLng, double resultLat, String description, int day, int month, int year, String sports, int totalPlayers, int timeHour, int timeMinute, long id, int status, String uid, int batsman, int bowlers, int allRounder, int wicketKeeper) {
        super(resultLocation, resultLng, resultLat, description, day, month, year, sports, totalPlayers, timeHour, timeMinute, id, status, uid);
        this.batsman = batsman;
        this.bowlers = bowlers;
        this.allRounder = allRounder;
        this.wicketKeeper = wicketKeeper;
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
}