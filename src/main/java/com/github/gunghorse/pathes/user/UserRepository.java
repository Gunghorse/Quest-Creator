package com.github.gunghorse.pathes.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findByLogin(@Param("login") String login);
    List<User> findByPassword(@Param("password") String password);
    User findByLoginAndPassword(@Param("login") String login, @Param("password") String password);
}

