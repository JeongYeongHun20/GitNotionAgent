package com.gitnotionagent.config;

import notion.api.v1.NotionClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotionConfig {
    @Bean
    public NotionClient notionClient(@Value("${notion.token}") String token) {
        return new NotionClient(token);
    }
}

