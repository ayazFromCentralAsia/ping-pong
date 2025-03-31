package com.example.management_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "monitoring_logs")
@Data
public class MonitoringLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "website_id", nullable = false)
    private Website website;
    private Integer statusCode;
    private Integer responseTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogStatus status;

    public enum LogStatus {
        SUCCESS,
        FAILED
    }

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
