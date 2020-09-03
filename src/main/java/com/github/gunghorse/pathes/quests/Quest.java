package com.github.gunghorse.pathes.quests;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="quests")
@TypeAlias("quest")
public class Quest {
    @Id
    private String id;
    private List<String> questPointsID;
    private String title;
    private String description;
    private String creatorID;

    public Quest(String title, String description, String creatorID) {
        this.title = title;
        this.description = description;
        this.creatorID = creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public void addQuestPointID(String questPointsID) {
        this.questPointsID.add(questPointsID);
    }

    public String getId() {
        return id;
    }

    public List<String> getQuestPointsID() {
        return questPointsID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatorID() {
        return creatorID;
    }
}
