package com.github.gunghorse.pathes.sessions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.pathes.quests.Quest;
import com.github.gunghorse.pathes.user.User;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "PLAYING")
public class SessionRel {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    @JsonIgnoreProperties({"user","players","creator"})
    private User player;

    @EndNode
    @JsonIgnoreProperties({"quest","playing","creatures"})
    private Quest quest;

}
