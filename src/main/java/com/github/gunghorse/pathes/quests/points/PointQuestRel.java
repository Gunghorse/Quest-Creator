package com.github.gunghorse.pathes.quests.points;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.pathes.quests.Quest;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "BELONGS_TO")
public class PointQuestRel {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    @JsonIgnoreProperties({"quest","children","parents"})
    private QuestPoint point;

    @EndNode
    @JsonIgnoreProperties({"points","players","startPoint", "creator"})
    private Quest quest;
}
