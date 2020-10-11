package com.github.gunghorse.questCreator.quests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.github.gunghorse.questCreator.quests.points.QuestPoint;
import com.github.gunghorse.questCreator.quests.points.QuestStartPoint;

import com.github.gunghorse.questCreator.Keys;

import com.github.gunghorse.questCreator.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.LinkedList;
import java.util.List;

import static org.neo4j.ogm.annotation.Relationship.*;


@NodeEntity
@SuperBuilder
@Getter @Setter
@NoArgsConstructor
public class Quest {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;

    @JsonIgnoreProperties({"quest","playing","createdQuests"})
    @Relationship(type = "PLAYING", direction = OUTGOING)
    private final List<User> players = new LinkedList<>();

    @JsonIgnoreProperties({"quest","playing","createdQuests"})
    @Relationship(type = Keys.CREATED_BY, direction = INCOMING)
    private User creator;

    @JsonIgnore
    @JsonIgnoreProperties({"quest","children","parents"})
    @Relationship(type = "BELONGS_TO", direction = OUTGOING)
    private final List<QuestPoint> points = new LinkedList<>();

    @JsonIgnoreProperties({"quest"})
    @Relationship(type = "STARTING_FROM", direction = INCOMING)
    private QuestStartPoint startPoint;

    /*public Quest(String title, String description) {
        this.title = title;
        this.description = description;
    }*/

    public void setCreator(User creator){
        this.creator = creator;
        creator.addCreatedQuest(this);
    }

    public void setStartPoint(QuestStartPoint startPoint){
        this.startPoint = startPoint;
        startPoint.setQuestStartingFrom(this);
    }
    
    public void addPlayer(User player){
        players.add(player);
    }
    
    public void addPoint(QuestPoint point) {
        this.points.add(point);
    }
}
