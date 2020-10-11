package com.github.gunghorse.questCreator.repositories;

import com.github.gunghorse.questCreator.quests.points.QuestPoint;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface QuestPointRepository extends Neo4jRepository<QuestPoint, Long> {
}
