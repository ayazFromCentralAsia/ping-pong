package com.example.management_service.service;


import com.example.management_service.entity.CheckRequest;
import com.example.management_service.entity.MonitoringLogs;
import com.example.management_service.repositroy.CheckRequestRepository;
import com.example.management_service.repositroy.MonitoringRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@EnableKafka
public class PingCheckerService {

    private final CheckRequestRepository repository;
    private final Map<Integer, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
    private final RestTemplate restTemplate = new RestTemplate();
    private final MonitoringRepository monitoringRepository;

    @PostConstruct
    public void init() {
        repository.findAll().forEach(this::scheduleTaskForWebsite);
    }

    public void scheduleTaskForWebsite(CheckRequest request) {
        ScheduledFuture<?> future = executorService.scheduleAtFixedRate(
                () -> pingWebsite(request),
                0,
                request.getIntervalTime(),
                TimeUnit.MINUTES
        );
        tasks.put(request.getWebsiteId(), future);
    }

    private void pingWebsite(CheckRequest request) {
        System.out.println("Пингую сайт: " + request.getUrl());

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> response = isWebsiteUp(request.getUrl());
        long endTime = System.currentTimeMillis();

        int responseTime = (int) (endTime - startTime);
        int statusCode = response.getStatusCodeValue();

        MonitoringLogs log = new MonitoringLogs();
        log.setWebsiteId(request.getWebsiteId());
        log.setStatusCode(statusCode);
        log.setResponseTime(responseTime);
        if (statusCode == 200){
            log.setStatus(MonitoringLogs.LogStatus.valueOf("SUCCESS"));
        }else {
            log.setStatus(MonitoringLogs.LogStatus.valueOf("FAILED"));
        }
        log.setCreatedAt(LocalDateTime.now());

        monitoringRepository.save(log);
    }

    public ResponseEntity<?> isWebsiteUp(String url) {
        try {
            return restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.out.println("HTTP ошибка: " + ex.getStatusCode());
            return ResponseEntity.status(ex.getStatusCode()).build();
        } catch (ResourceAccessException ex) {
            System.out.println("Сетевая ошибка: " + ex.getMessage());
            return ResponseEntity.status(500).build();
        } catch (Exception ex) {
            System.out.println("Другая ошибка: " + ex.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

//    public void updateSchedule(CheckRequest updatedRequest) {
//        cancelTask(updatedRequest.getWebsiteId());
//        scheduleTaskForWebsite(updatedRequest);
//    }

    public void cancelTask(Integer websiteId) {
        ScheduledFuture<?> future = tasks.get(websiteId);
        if (future != null) {
            future.cancel(false);
            tasks.remove(websiteId);
        }
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }
}
