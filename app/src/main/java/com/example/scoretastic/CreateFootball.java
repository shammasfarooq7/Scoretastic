package com.example.scoretastic;

public class CreateFootball {


    int Attacker;
    int Defender;
    int Mid;
    int Keeper;

    public void setKeeper(int keeper) {
        Keeper = keeper;
    }

    public void setMid(int mid) {
        Mid = mid;
    }

    public void setDefender(int defender) {
        Defender = defender;
    }

    public void setAttacker(int attacker) {
        Attacker = attacker;
    }

    public int getKeeper() {
        return Keeper;
    }

    public int getMid() {
        return Mid;
    }

    public int getDefender() {
        return Defender;
    }

    public int getAttacker() {
        return Attacker;
    }
}
