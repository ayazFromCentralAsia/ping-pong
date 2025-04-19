package com.example.management_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "monitoring_logs")
@Data
@Setter
@Getter
public class MonitoringLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer websiteId;
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
