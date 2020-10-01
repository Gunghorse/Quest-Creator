package com.github.gunghorse.questCreator.quests.points;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.questCreator.quests.Quest;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.springframework.data.geo.Point;
import org.springframework.data.neo4j.conversion.PointConverter;

import java.util.LinkedList;
import java.util.List;

import static org.neo4j.ogm.annotation.Relationship.*;


@NodeEntity
public class QuestPoint {
    @Id
    @GeneratedValue
    protected Long id;
    protected QuestPointStatus pointStatus;
    protected String title;
    protected String description;
    @Convert(PointConverter.class) private Point location;


    @JsonIgnoreProperties({"points","players","startPoint", "creator"})
    @Relationship(type = "BELONGS_TO", direction = INCOMING)
    private Quest quest;

    @JsonIgnoreProperties({"quest","parents"})
    @Relationship(type = "LEAD_TO", direction = OUTGOING)
    protected List<QuestPoint> children = new LinkedList<>();

    @JsonIgnoreProperties({"quest","children"})
    @Relationship(type = "LEAD_TO", direction = INCOMING)
    protected List<QuestPoint> parents = new LinkedList<>();

    public QuestPoint(){}

    public QuestPoint(String title,
                      String description,
                      Point location) {
        this.title = title;
        this.description = description;
        this.location = location;
    }

    public QuestPoint(QuestPointStatus pointStatus,
                      String title,
                      String description,
                      Point location) {
        this.pointStatus = pointStatus;
        this.title = title;
        this.description = description;
        this.location = location;
    }

    @JsonIgnoreProperties
    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
        quest.setPoint(this);
    }

    @JsonIgnoreProperties
    public List<QuestPoint> getChildren() {
        return children;
    }

    public void addChild(QuestPoint child) {
        this.children.add(child);
    }

    @JsonIgnoreProperties
    public List<QuestPoint> getParents() {
        return parents;
    }

    public void addParent(QuestPoint parent) {
        this.parents.add(parent);
        parent.addChild(this);
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