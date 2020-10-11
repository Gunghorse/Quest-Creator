package com.github.gunghorse.questCreator.quests.points;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.questCreator.quests.Quest;
import lombok.*;
import lombok.experimental.SuperBuilder;
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
@Getter @Setter @SuperBuilder
@NoArgsConstructor
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
    protected final List<QuestPoint> children = new LinkedList<>();

    @JsonIgnoreProperties({"quest","children"})
    @Relationship(type = "LEAD_TO", direction = INCOMING)
    protected final List<QuestPoint> parents = new LinkedList<>();


    @JsonIgnoreProperties
    public Quest getQuest() {
        return quest;
    }

    @JsonIgnoreProperties
    public List<QuestPoint> getChildren() {
        return children;
    }

    @JsonIgnoreProperties
    public List<QuestPoint> getParents() {
        return parents;
    }


    public void setQuest(Quest quest) {
        this.quest = quest;
        quest.addPoint(this);
    }


    public void addChild(QuestPoint child) {
        this.children.add(child);
    }

    public void addParent(QuestPoint parent) {
        this.parents.add(parent);
        parent.addChild(this);
    }
}