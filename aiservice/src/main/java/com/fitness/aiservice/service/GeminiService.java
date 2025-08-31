package com.fitness.aiservice.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Service

public class GeminiService {

    @Value("${gemini.api.key}")
    private String geminiKEY;
    private final Client client;

    public GeminiService(@Value("${gemini.api.key}") String geminiKEY) {
        this.client = Client.builder().apiKey(geminiKEY).build();
    }


    public String getAnswer(String question){
        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                question,
                null
        );
        return response.text();


    }

}
