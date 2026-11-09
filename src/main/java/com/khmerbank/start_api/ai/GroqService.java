
package com.khmerbank.start_api.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class GroqService {

    private final WebClient webClient;
    private final String model;

    public GroqService(
            WebClient.Builder builder,
            @Value("${groq.api-key}") String apiKey,
            @Value("${groq.base-url}") String baseUrl,
            @Value("${groq.model}") String model
    ) {
        this.webClient = builder
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();

        this.model = model;
    }

    public String chat(String message) {

        Map<String, Object> request = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", "You are a helpful Java programming tutor."
                        ),
                        Map.of(
                                "role", "user",
                                "content", message
                        )
                )
        );

        Map response = webClient.post()
                .uri("/chat/completions")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        List<Map<String, Object>> choices =
                (List<Map<String, Object>>) response.get("choices");

        Map<String, Object> firstChoice = choices.get(0);

        Map<String, Object> responseMessage =
                (Map<String, Object>) firstChoice.get("message");

        return (String) responseMessage.get("content");
    }
}
