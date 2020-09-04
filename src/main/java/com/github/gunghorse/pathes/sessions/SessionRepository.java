package com.github.gunghorse.pathes.sessions;

import com.github.gunghorse.pathes.quests.Quest;
import com.github.gunghorse.pathes.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends MongoRepository<Session, String> {
    List<Session> findByUser(@Param("user") User user);
    List<Session> findByQuest(@Param("quest") Quest quest);
    List<Session> findByEndedTrue();
    List<Session> findByEndedFalse();
}
