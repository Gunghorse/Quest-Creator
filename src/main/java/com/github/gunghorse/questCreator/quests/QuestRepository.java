package com.github.gunghorse.questCreator.quests;


import com.github.gunghorse.questCreator.user.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestRepository extends Neo4jRepository<Quest, Long> {
    List<Quest> findByTitle(@Param("title") String title);
    List<Quest> findByCreator(@Param("creator") User creator);
}
