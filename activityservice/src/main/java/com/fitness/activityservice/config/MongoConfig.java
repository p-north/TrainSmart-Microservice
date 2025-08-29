package com.fitness.activityservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig {
//    Wires up an auditing infrastructure that listens to save events
//    automatically sets fields to the correct time and date
}
