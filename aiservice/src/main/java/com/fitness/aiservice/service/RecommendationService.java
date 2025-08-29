package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    public List<Recommendation> getUserRecommendation(String userID) {
        return recommendationRepository.findByUserID(userID);
    }

    public Recommendation getActivityRecommendation(String activityID) {
        return recommendationRepository.findByActivityID(activityID)
                .orElseThrow(()-> new RuntimeException(("No recommendation found for this activity: " + activityID)));
    }
}
