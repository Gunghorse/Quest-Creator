package com.github.gunghorse.questCreator.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.questCreator.Keys;
import com.github.gunghorse.questCreator.quests.Quest;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    @Relationship(type = Keys.PLAYING)
    private List<Quest> playing = new ArrayList<>();

    @JsonIgnoreProperties({"user","players","creator"})
    @Relationship(type = Keys.CREATED_BY, direction = OUTGOING)
    private List<Quest> createdQuests = new ArrayList<>();


    public void addCreatedQuest(Quest quest){
        createdQuests.add(quest);
    }

    public void startQuestSession(Quest quest){
        playing.add(quest);
        quest.addPlayer(this);
    }
}
