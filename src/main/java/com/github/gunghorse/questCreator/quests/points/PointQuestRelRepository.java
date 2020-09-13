package com.github.gunghorse.questCreator.quests.points;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PointQuestRelRepository extends Neo4jRepository<QuestPoint, Long> {
}
