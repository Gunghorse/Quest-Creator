package com.github.gunghorse.pathes.sessions;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends MongoRepository<Session, String> {
    List<Session> findByUserID(@Param("login") String userID);
    List<Session> findByQuestID(@Param("questID") String questID);
    List<Session> findByEndedTrue();
    List<Session> findByEndedFalse();
}
