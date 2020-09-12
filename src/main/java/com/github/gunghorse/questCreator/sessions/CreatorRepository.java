package com.github.gunghorse.questCreator.sessions;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CreatorRepository extends Neo4jRepository<SessionRel, Long>{}
