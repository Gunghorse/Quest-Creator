package com.github.gunghorse.questCreator.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.questCreator.Keys;
import com.github.gunghorse.questCreator.quests.Quest;
import org.neo4j.ogm.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;


@NodeEntity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Index(unique = true)
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    @Index(unique = true)
    private String email;

    @JsonIgnoreProperties({"user","players","creator"})
    @Relationship(type = Keys.PLAYING)
    private List<Quest> playing = new ArrayList<>();

    @JsonIgnoreProperties({"user","players","creator"})
    @Relationship(type = Keys.CREATED_BY, direction = INCOMING)
    private List<Quest> createdQuests = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void addCreatedQuest(Quest quest){
        createdQuests.add(quest);
    }

    public void startQuestSession(Quest quest){
        playing.add(quest);
        quest.addPlayer(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Quest> getCreatedQuests() {
        return createdQuests;
    }

    public void setCreatedQuests(List<Quest> createdQuests) {
        this.createdQuests = createdQuests;
    }
}
