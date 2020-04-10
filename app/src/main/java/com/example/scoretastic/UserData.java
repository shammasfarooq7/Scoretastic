package com.example.scoretastic;

import android.widget.ImageView;

public class UserData {
    String name;
    String email;
    String password;
    ImageView profilePic;
    int Radius;
    long id;
    String userId;

    public UserData(String name, String email, String password, String userId,long id,ImageView profilePic, int radius ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.id=id;
        this.profilePic = profilePic;
        Radius = radius;
    }

    public UserData() {
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