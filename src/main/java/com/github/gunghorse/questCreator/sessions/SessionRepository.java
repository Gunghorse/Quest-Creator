package com.github.gunghorse.questCreator.sessions;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SessionRepository extends Neo4jRepository<SessionRel, Long> {

}
