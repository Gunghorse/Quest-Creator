package com.github.gunghorse.pathes.quests;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestRepository extends MongoRepository<Quest, String> {
    List<Quest> findByCreatorID(@Param("creatorID") String creatorID);
    List<Quest> findByTitle(@Param("title") String title);
}
