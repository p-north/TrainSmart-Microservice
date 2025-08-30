package com.fitness.aiservice.model;


import lombok.Data;

import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;
import java.util.Map;


@Data
public class Activity {
    @Id
    private String id;
    private String userID;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Map<String, Object> additionalMetrics;
}
