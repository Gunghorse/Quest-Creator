package com.github.gunghorse.pathes.quests;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestPointRepository extends MongoRepository<QuestPoint, String> {
    List<QuestPoint> getByQuestID(@Param("questID") String questID);
    List<QuestPoint> getByPointStatus(@Param("pointStatus") String pointStatus);
    List<QuestPoint> findByTitle(@Param("title") String title);
    List<QuestPoint> findByLocation(@Param("location") Point location);

    //TODO add new features from this documentation
    // https://docs.spring.io/spring-data/mongodb/docs/1.2.0.RELEASE/reference/html/mongo.repositories.html
}
