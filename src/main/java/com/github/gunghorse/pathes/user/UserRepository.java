package com.github.gunghorse.pathes.user;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends Neo4jRepository<User, Long> {
    User findByLogin(@Param("login") String login);
    User findByLoginAndPassword(@Param("login") String login, @Param("password") String password);
}

