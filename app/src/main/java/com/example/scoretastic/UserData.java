package com.example.scoretastic;

import android.widget.ImageView;

public class UserData {
    String name;
    String email;
    String password;
    int Radius;
    long id;
    String userId;
    int hosted;
    int subscribed;
    String fSports;
    String pos;



    public UserData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRadius() {
        return Radius;
    }

    public void setRadius(int radius) {
        Radius = radius;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getHosted() {
        return hosted;
    }

    public void setHosted(int hosted) {
        this.hosted = hosted;
    }

    public int getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(int subscribed) {
        this.subscribed = subscribed;
    }

    public String getfSports() {
        return fSports;
    }

    public void setfSports(String fSports) {
        this.fSports = fSports;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }
}