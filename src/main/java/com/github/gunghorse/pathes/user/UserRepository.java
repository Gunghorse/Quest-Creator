package com.github.gunghorse.pathes.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findByLogin(@Param("login") String login);
    User findByLoginAndPassword(@Param("login") String login, @Param("password") String password);
}

