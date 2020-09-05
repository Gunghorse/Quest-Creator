package com.github.gunghorse.pathes.sessions;


import com.github.gunghorse.pathes.quests.Quest;
import com.github.gunghorse.pathes.user.User;
import com.mongodb.lang.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="sessions")
@TypeAlias("session")
public class Session {

    @Id
    private String id;
    private User user;
    @DBRef
    @Nullable
    private Quest quest;
    private boolean isEnded;

    public Session(User user, Quest quest) {
        this.user = user;
        this.quest = quest;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getUserID(){
        return user.getLogin();
    }

    public Quest getQuest() {
        return quest;
    }

    public String getQuestID(){
        return quest.getId();
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }
}
