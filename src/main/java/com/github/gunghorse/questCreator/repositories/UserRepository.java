package com.github.gunghorse.questCreator.repositories;

import com.github.gunghorse.questCreator.user.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends Neo4jRepository<User, Long> {
    User findByUsername(@Param("username") String login);
    User findByUsernameAndPassword(@Param("username") String login, @Param("password") String password);
    User findByEmailIgnoreCase(@Param("email") String email);
}

