package com.github.gunghorse.pathes.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.pathes.Keys;
import com.github.gunghorse.pathes.quests.Quest;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;


@NodeEntity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String password;

    @JsonIgnoreProperties({"user","players","creator"})
    @Relationship(type = Keys.PLAYING)
    private List<Quest> playing = new ArrayList<>();

    @JsonIgnoreProperties({"user","players","creator"})
    @Relationship(type = Keys.CREATED_BY, direction = INCOMING)
    private List<Quest> creatures = new ArrayList<>();

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void addCreature(Quest quest){
        creatures.add(quest);
    }

    public void startQuestSession(Quest quest){
        playing.add(quest);
        quest.addPlayer(this);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
