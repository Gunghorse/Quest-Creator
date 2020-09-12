package com.github.gunghorse.pathes.quests.points;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.pathes.quests.Quest;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.geo.Point;

import java.util.LinkedList;
import java.util.List;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;


@NodeEntity
public class QuestPoint {
    @Id
    @GeneratedValue
    protected Long id;
    protected QuestPointStatus pointStatus;
    protected String title;
    protected String description;
    protected Point location;

    @JsonIgnoreProperties({"points","players","startPoint", "creator"})
    @Relationship(type = "BELONGS_TO")
    private Quest quest;

    @JsonIgnoreProperties({"quest","children","parents"})
    @Relationship(type = "LEAD_TO")
    private List<QuestPoint> children = new LinkedList<>();

    @JsonIgnoreProperties({"quest","children","parents"})
    @Relationship(type = "LEAD_TO", direction = INCOMING)
    private List<QuestPoint> parents = new LinkedList<>();

    public QuestPoint(QuestPointStatus pointStatus,
                      String title,
                      String description,
                      Point location) {
        this.pointStatus = pointStatus;
        this.title = title;
        this.description = description;
        this.location = location;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public List<QuestPoint> getChildren() {
        return children;
    }

    public void setChildren(List<QuestPoint> children) {
        this.children = children;
    }

    public List<QuestPoint> getParents() {
        return parents;
    }

    public void setParents(List<QuestPoint> parents) {
        this.parents = parents;
    }

    public Long getId() {
        return id;
    }

    public QuestPointStatus getPointStatus() {
        return pointStatus;
    }

    public void setPointStatus(QuestPointStatus pointStatus) {
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

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}