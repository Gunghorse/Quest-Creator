package com.github.gunghorse.pathes.sessions;

import com.github.gunghorse.pathes.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends MongoRepository<Session, String> {
    List<Session> findByUserID(@Param("login") String userID);
}
