package com.example.management_service.service;

import com.example.management_service.dto.WebsiteRequest;
import com.example.management_service.entity.Website;
import com.example.management_service.exceptions.AlreadyExistsException;
import com.example.management_service.repository.WebsiteRepository;
import org.springframework.kafka.support.SendResult;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.valueOf;

@Service
@RequiredArgsConstructor
public class WebsiteService {

    private final WebsiteRepository websiteRepository;
    private final KafkaTemplate<String, Website> kafkaTemplate;


    public Website save(WebsiteRequest website) {
        Website websiteEntity = new Website();

        if (websiteRepository.existsByUrl(website.getUrl())) {
            throw new AlreadyExistsException("Website with this URL already exists");
        }

        if (website.getIntervalTime() > 1440){
            throw new IllegalArgumentException("Interval time cannot be greater than 1440 minutes");
        }

        websiteEntity.setTitle(website.getTitle());
        websiteEntity.setUrl(website.getUrl());
        websiteEntity.setDescription(website.getDescription());
        websiteEntity.setIntervalTime(website.getIntervalTime());
        websiteEntity.setStatus(Website.WebsiteStatus.valueOf(String.valueOf(valueOf(website.getStatus()))));
        websiteEntity.setCreatedAt(LocalDateTime.now());
        websiteEntity.setUpdatedAt(LocalDateTime.now());

        websiteRepository.save(websiteEntity);

        kafkaTemplate.send("website-info", websiteEntity);

        return websiteEntity;
    }
}