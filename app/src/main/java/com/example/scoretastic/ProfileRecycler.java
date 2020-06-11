package com.example.scoretastic;

import android.widget.ImageView;

public class ProfileRecycler {
    String name;
    String totalMatch;
    String pos;
    String favSports;
    String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalMatch() {
        return totalMatch;
    }

    public void setTotalMatch(String totalMatch) {
        this.totalMatch = totalMatch;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getFavSports() {
        return favSports;
    }

    public void setFavSports(String favSports) {
        this.favSports = favSports;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
