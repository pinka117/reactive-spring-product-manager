package com.reactive.spring.product.manager.controller;

import com.mongodb.MongoClient;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/bdd/resources/login.feature", glue = "com.reactive.spring.product.manager.controller.steps.login")
public class LoginWebCucumberBDD {
    @TestConfiguration
    public class MongoConfig {
        private static final String MONGO_DB_URL = "localhost";
        private static final String MONGO_DB_NAME = "embeded_db";

        @Bean
        public MongoTemplate mongoTemplate() throws IOException {
            EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
            mongo.setBindIp(MONGO_DB_URL);
            MongoClient mongoClient = mongo.getObject();
            MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);
            return mongoTemplate;
        }
    }
}