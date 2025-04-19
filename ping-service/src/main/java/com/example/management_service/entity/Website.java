package com.example.management_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Data
public class Website {

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
