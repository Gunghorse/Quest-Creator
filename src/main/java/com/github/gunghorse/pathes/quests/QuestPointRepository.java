package com.github.gunghorse.pathes.quests;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestPointRepository extends MongoRepository<QuestPoint, String> {
    List<QuestPoint> getByQuestID(@Param("questID") String questID);
    List<QuestPoint> getByPointStatus(@Param("pointStatus") String pointStatus);
    List<QuestPoint> findByTitle(@Param("title") String title);
    List<QuestPoint> findByLocation(@Param("location") GeoJsonPoint location);
    List<QuestPoint> findByQuestIDAndTitleAndLocation(@Param("questID") String questID,
                                                @Param("title") String title,
                                                @Param("location") GeoJsonPoint location);

    @Query("{'location' : {$geoNear:?0 , $maxDistance:?1}}")
    List<QuestPoint> findByLocationNear(@Param("location") GeoJsonPoint location, @Param("distance") int distance);
    //TODO add new features from this documentation
    // https://docs.spring.io/spring-data/mongodb/docs/1.2.0.RELEASE/reference/html/mongo.repositories.html
}
