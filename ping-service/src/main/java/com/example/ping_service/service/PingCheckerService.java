package com.example.ping_service.service;


import com.example.ping_service.dto.WebsiteKafkaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PingCheckerService {

    @KafkaListener(topics = "website-info", groupId = "ping-checker-group")
    public void consumeLog(WebsiteKafkaResponse log) {
        System.out.println("getting: " + log);
    }
    @Bean
    public JsonDeserializer<WebsiteKafkaResponse> errorHandlingJsonDeserializer() {
        JsonDeserializer<WebsiteKafkaResponse> deserializer = new JsonDeserializer<>(WebsiteKafkaResponse.class);
        deserializer.addTrustedPackages("com.example.ping_service.dto.WebsiteKafkaResponse");
        return deserializer;
    }
}
