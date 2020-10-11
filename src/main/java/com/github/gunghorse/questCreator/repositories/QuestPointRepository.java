package com.github.gunghorse.questCreator.repositories;

import com.github.gunghorse.questCreator.quests.Quest;
import com.github.gunghorse.questCreator.quests.points.QuestPoint;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestPointRepository extends Neo4jRepository<QuestPoint, Long> {
    List<QuestPoint> findByQuestId(@Param("id")Long id);
}
