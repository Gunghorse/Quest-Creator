package com.github.gunghorse.questCreator.quests.points;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.questCreator.quests.Quest;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.geo.Point;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

public class QuestStartPoint extends QuestPoint {

    @JsonIgnoreProperties({"points","players","startPoint", "creator"})
    @Relationship(type = "STARTING_FROM", direction = INCOMING)
    private Quest questStartingFrom;

    public QuestStartPoint(QuestPointStatus pointStatus, String title, String description, Point location) {
        super(pointStatus, title, description, location);
    }
}
