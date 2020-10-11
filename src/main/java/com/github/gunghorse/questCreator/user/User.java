package com.github.gunghorse.questCreator.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.questCreator.Keys;
import com.github.gunghorse.questCreator.quests.Quest;
import com.github.gunghorse.questCreator.quests.points.QuestPoint;
import lombok.Getter;
import lombok.Setter;

import org.neo4j.ogm.annotation.*;

import java.util.*;

import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

@Getter @Setter
@NodeEntity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Index(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @Index(unique = true)
    private String email;

    @JsonIgnoreProperties({"user","players","creator"})
    @Relationship(type = Keys.CREATED_BY, direction = OUTGOING)
    private List<Quest> createdQuests = new ArrayList<>();

    @Transient
    private HashSet<Quest> completedQuestsId = new HashSet<>();
    @Transient
    private HashMap<Quest, HashSet<QuestPoint>> activeQuestsVisitedPoints = new HashMap<>();
    @Transient
    private HashMap<Quest, HashSet<QuestPoint>> activeQuestsVisiblePoints = new HashMap<>();


    public void addCreatedQuest(Quest quest){
        createdQuests.add(quest);
    }

    public void startQuestSession(Quest quest){
        quest.addPlayer(this);

        HashSet<QuestPoint> points = new HashSet<>();
        points.add(quest.getStartPoint());
        activeQuestsVisiblePoints.put(quest, points);
        activeQuestsVisitedPoints.put(quest, new HashSet<>());
    }

    public void visitPoint(Quest quest, QuestPoint point){
        if (quest == null || point == null) return;

        HashSet<QuestPoint> visible = activeQuestsVisiblePoints.get(quest);
        visible.remove(point);
        visible.addAll(point.getChildren());

        HashSet<QuestPoint> visited = activeQuestsVisitedPoints.get(quest);
        visited.add(point);

        if (visited.size() == quest.getSize()){    // if quest completed
            completedQuestsId.add(quest);
            activeQuestsVisitedPoints.remove(quest);
            activeQuestsVisiblePoints.remove(quest);
        }
    }
}