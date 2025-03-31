package com.example.management_service.entity;


import com.example.management_service.dto.WebsiteRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "website")
@Data
@Setter
@Getter
public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String url;
    private String description;
    private Integer intervalTime;

    @Enumerated(EnumType.STRING)
    private WebsiteStatus status;

    public enum WebsiteStatus {
        ACTIVE,
        INACTIVE;
    }

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
