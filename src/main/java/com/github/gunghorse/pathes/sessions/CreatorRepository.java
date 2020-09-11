package com.github.gunghorse.pathes.sessions;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CreatorRepository extends Neo4jRepository<SessionRel, Long>{}
