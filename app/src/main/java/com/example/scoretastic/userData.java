package com.example.scoretastic;

import android.widget.ImageView;

public class userData {
    String name;
    String email;
    String password;
    ImageView profilePic;
    int Radius;
    int userId;

    public userData(String name, String email, String password, ImageView profilePic, int radius, int userId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePic = profilePic;
        Radius = radius;
        this.userId = userId;
    }

    public userData() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public ImageView getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(ImageView profilePic) {
        this.profilePic = profilePic;
    }

    public int getRadius() {
        return Radius;
    }

    public void setRadius(int radius) {
        Radius = radius;
    }
}
