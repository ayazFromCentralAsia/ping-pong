package com.example.management_service.service;

import com.example.management_service.PingServiceApplication;
import com.example.management_service.entity.CheckRequest;
import com.example.management_service.entity.Website;
import com.example.management_service.repositroy.CheckRequestRepository;
import com.example.management_service.repositroy.MonitoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final MonitoringRepository monitoringRepository;
    private final CheckRequestRepository checkRequestRepository;
    private final PingCheckerService pingCheckerService;


    @KafkaListener(topics = "website-info", groupId = "ping-checker-group")
    public void consumeJson(Website website) {
        CheckRequest checkRequest = new CheckRequest();

        checkRequest.setUrl(website.getUrl());
        checkRequest.setTitle(website.getTitle());
        checkRequest.setIntervalTime(website.getIntervalTime());
        checkRequest.setWebsiteId(website.getId());

        checkRequestRepository.save(checkRequest);
        pingCheckerService.scheduleTaskForWebsite(checkRequest);
    }
}
