package com.github.gunghorse.questCreator.relationships;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.questCreator.Keys;
import com.github.gunghorse.questCreator.quests.Quest;
import com.github.gunghorse.questCreator.user.User;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = Keys.CREATED_BY)
public class CreatedByRel {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    @JsonIgnoreProperties({"quest","playing","creatures"})
    private Quest quest;

    @EndNode
    @JsonIgnoreProperties({"user","players","creator"})
    private User creator;
}
