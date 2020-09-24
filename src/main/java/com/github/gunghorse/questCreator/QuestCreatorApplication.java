package com.github.gunghorse.questCreator;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.ConfigurationSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class QuestCreatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestCreatorApplication.class, args);
    }
}
