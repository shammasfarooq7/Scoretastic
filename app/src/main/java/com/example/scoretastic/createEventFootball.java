package com.example.scoretastic;

import java.sql.Time;
import java.util.Date;

public class createEventFootball extends CreateEventData{
    int keeper;
    int defender;
    int attacker;
    int midfielder;

    public createEventFootball() {
    }

    public createEventFootball(String resultLocation, double resultLng, double resultLat, String description, Date date, Time time, String sports, int totalPlayers, int timeHour, int timeMinute, int keeper, int defender, int attacker, int midfielder) {
        super(resultLocation, resultLng, resultLat, description, date, time, sports, totalPlayers, timeHour, timeMinute);
        this.keeper = keeper;
        this.defender = defender;
        this.attacker = attacker;
        this.midfielder = midfielder;
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
}