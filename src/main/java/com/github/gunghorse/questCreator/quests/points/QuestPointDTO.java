package com.github.gunghorse.questCreator.quests.points;

public class QuestPointDTO {
    private Long questID;
    private String pointStatus;
    private String title;
    private String description;
    private double longitude;
    private double latitude;

    public Long getQuestID() {
        return questID;
    }

    public void setQuestID(Long questID) {
        this.questID = questID;
    }

    public String getPointStatus() {
        return pointStatus;
    }

    public void setPointStatus(String pointStatus) {
        this.pointStatus = pointStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
