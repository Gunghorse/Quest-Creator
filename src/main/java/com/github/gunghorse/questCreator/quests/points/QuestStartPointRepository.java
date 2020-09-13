package com.github.gunghorse.questCreator.quests.points;

import com.github.gunghorse.questCreator.quests.Quest;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface QuestStartPointRepository  extends Neo4jRepository<QuestStartPoint, Long> {
}
