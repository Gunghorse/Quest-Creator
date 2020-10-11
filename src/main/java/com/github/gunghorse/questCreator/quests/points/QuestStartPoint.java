package com.github.gunghorse.questCreator.quests.points;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.questCreator.quests.Quest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.geo.Point;

import java.util.List;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@SuperBuilder
@Getter
public class QuestStartPoint extends QuestPoint {

    @JsonIgnoreProperties({"points","players","startPoint", "creator"})
    @Relationship(type = "STARTING_FROM", direction = INCOMING)
    private Quest quest;

    @JsonIgnore
    protected List<QuestPoint> parents;

    public QuestStartPoint(){super();}

    public void setQuestStartingFrom(Quest questStartingFrom){
        this.quest = questStartingFrom;
        super.setQuest(questStartingFrom);
    }
}
