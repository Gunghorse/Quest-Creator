package com.github.gunghorse.questCreator.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.gunghorse.questCreator.Keys;
import com.github.gunghorse.questCreator.quests.Quest;
import org.neo4j.driver.internal.shaded.reactor.util.annotation.NonNull;
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

    @NonNull
    @NotEmpty
    @Index(unique = true)
    private String login;

    @NonNull
    @NotEmpty
    private String password;

    @NonNull
    @NotEmpty
    @Index(unique = true)
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
