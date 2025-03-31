package com.example.management_service.controller;


import com.example.management_service.dto.WebsiteRequest;
import com.example.management_service.entity.Website;

import com.example.management_service.service.WebsiteService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/website")
@OpenAPIDefinition(info = @Info(title = "Monitoring Service API",
        version = "1.0.0", description = "Service for Monitoring the Website Status"))
public class WebsiteController {
    private final WebsiteService websiteRepository;

    @PostMapping("/save")
    @Operation(summary = "Save website", description = "Save website details", tags = {"Website"})
    public Website saveWebsite(WebsiteRequest website) {
        return websiteRepository.save(website);
    }
}