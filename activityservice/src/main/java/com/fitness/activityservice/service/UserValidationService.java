package com.fitness.activityservice.service;

import com.fitness.activityservice.config.WebClientConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationService {
    private final WebClient userServiceWebClient;

    public boolean validate(String userID){
        try{
            return userServiceWebClient.get()
                    .uri("/api/users/{userID}/validate", userID)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

        } catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new RuntimeException("User not found: " + userID);
            else if(e.getStatusCode() == HttpStatus.BAD_REQUEST)
                throw new RuntimeException("Invalid request: " + userID);

        }
        return false;


    }

}
