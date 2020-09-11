package com.github.gunghorse.pathes.sessions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.pathes.quests.Quest;
import com.github.gunghorse.pathes.user.User;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "CREATED_BY")
public class CreatedRel {

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
