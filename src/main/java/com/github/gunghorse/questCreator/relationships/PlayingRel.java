package com.github.gunghorse.questCreator.relationships;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.questCreator.Keys;
import com.github.gunghorse.questCreator.quests.Quest;
import com.github.gunghorse.questCreator.user.User;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = Keys.PLAYING)
public class PlayingRel {

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
