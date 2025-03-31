package com.example.management_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Website Request DTO")
public class WebsiteRequest {
    @NotBlank(message = "Title cannot be blank")
    @Schema(description = "Title of the website", example = "Github")
    private String title;
    @NotBlank(message = "Url cannot be blank")
    @Schema(description = "Url of the website", example = "https://github.com")
    private String url;

    @Schema(description = "Description of the website", example = "This is a website for version control")
    private String description;

    @Schema(description = "Interval time of the website in minutes", example = "10")
    @NotNull(message = "Interval time cannot be null")
    private Integer intervalTime;

    @Schema(description = "Status of the website, can be either ACTIVE or INACTIVE", example = "ACTIVE")
    @Enumerated(EnumType.STRING)
    private WebsiteStatus status;

    enum WebsiteStatus {
        ACTIVE,
        INACTIVE;
    }
}
