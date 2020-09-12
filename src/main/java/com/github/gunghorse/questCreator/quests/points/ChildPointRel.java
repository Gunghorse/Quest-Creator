package com.github.gunghorse.questCreator.quests.points;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "LEAD_TO")
public class ChildPointRel {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    @JsonIgnoreProperties({"quest","children","parents"})
    private QuestPoint startPoint;

    @EndNode
    @JsonIgnoreProperties({"quest","children","parents"})
    private QuestPoint childPoint;
}
