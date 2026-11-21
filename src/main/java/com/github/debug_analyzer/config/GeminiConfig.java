package com.github.debug_analyzer.config;

import com.google.genai.Client;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Bean
    public Client geminiClient(@Value("${debug-analyzer.gemini.api-key}") String apiKey) {
        return new Client.Builder().apiKey(apiKey).build();
    }

    @Bean
    public ChatModel chatModel(Client geminiClient,
                               @Value("${debug-analyzer.gemini.models.debug-agent}") String defaultModel) {
        return GoogleGenAiChatModel.builder()
                .genAiClient(geminiClient)
                .options(GoogleGenAiChatOptions.builder().model(defaultModel).build())
                .build();
    }
}
