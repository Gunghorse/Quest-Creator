package com.github.gunghorse.pathes;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class PathesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PathesApplication.class, args);
    }

    public @Bean
    MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    public @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "questCreator");
    }

    public @Bean
    void createIndex(){
        MongoCollection<Document> dbCollection = mongoTemplate().getCollection("questPoint");
        dbCollection.createIndex(Indexes.geo2dsphere("location"));
    }
}
