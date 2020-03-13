package com.example.scoretastic;

public class JoinEventData {
    int eventKey;
    int userId;
    String position;

    public JoinEventData() {
    }

    public JoinEventData(int eventKey, int userId, String position) {
        this.eventKey = eventKey;
        this.userId = userId;
        this.position = position;
    }

    public int getEventKey() {
        return eventKey;
    }

    public void setEventKey(int eventKey) {
        this.eventKey = eventKey;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


}
