package com.github.gunghorse.questCreator.repositories;

import com.github.gunghorse.questCreator.quests.Quest;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestRepository extends Neo4jRepository<Quest, Long> {
    List<Quest> findByTitle(@Param("title") String title);
    List<Quest> findByCreatorUsername(@Param("username") String username);
}
