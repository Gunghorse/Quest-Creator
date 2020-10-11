package com.github.gunghorse.questCreator.relationships;

import com.github.gunghorse.questCreator.quests.Quest;
import com.github.gunghorse.questCreator.quests.points.QuestStartPoint;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "STARTING_FROM")
public class StartPointRel {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    //@JsonIgnoreProperties({"quest","playing","creatures"})
    private Quest quest;

    @EndNode
    //@JsonIgnoreProperties({"user","players","creator"})
    private QuestStartPoint startPoint;
}
