package com.example.ping_service.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class WebsiteKafkaResponse {
    private Integer id;

    private String title;
    private String url;
    private String description;
    private Integer intervalTime;
    private WebsiteStatus status;

    public enum WebsiteStatus {
        ACTIVE,
        INACTIVE;
    }
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
