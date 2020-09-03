package com.github.gunghorse.pathes.quests;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="questPoint")
@TypeAlias("questPoint")
public class QuestPoint {
    @Id
    private String pointID;
    private String pointStatus;
    private String title;
    private String description;
    private Point location;
    private String questID;
    private String parentPointID;

    public QuestPoint(String pointStatus,
                      String title,
                      String description,
                      double longitude,
                      double latitude,
                      String questID,
                      String parentPointID) {
        this.pointStatus = pointStatus;
        this.title = title;
        this.description = description;
        this.location = new Point(latitude, longitude);
        this.questID = questID;
        this.parentPointID = parentPointID;
    }

    public void setQuestID(String questID) {
        this.questID = questID;
    }

    public String getPointID() {
        return pointID;
    }

    public String getPointStatus() {
        return pointStatus;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Point getLocation() {
        return location;
    }

    public String getQuestID() {
        return questID;
    }

    public String getParentPointID() {
        return parentPointID;
    }
}
