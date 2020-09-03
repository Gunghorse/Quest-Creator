package com.github.gunghorse.pathes.sessions;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="sessions")
@TypeAlias("session")
public class Session {

    @Id
    private String id;
    private String userID;
    private String questID;
    private boolean isEnded;

    public Session(String userID, String questID) {
        this.userID = userID;
        this.questID = questID;
    }

    public String getId() {
        return id;
    }

    public String getUserID() {
        return userID;
    }

    public String getQuestID() {
        return questID;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }
}
