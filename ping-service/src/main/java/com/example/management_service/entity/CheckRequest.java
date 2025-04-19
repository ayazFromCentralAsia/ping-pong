package com.example.management_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "check_request")
@Data
@Setter
@Getter
public class CheckRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer websiteId;
    private String title;
    private String url;
    private Integer intervalTime;
}
