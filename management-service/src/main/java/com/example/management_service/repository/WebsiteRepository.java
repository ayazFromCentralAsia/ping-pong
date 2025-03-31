package com.example.management_service.repository;

import com.example.management_service.entity.Website;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteRepository extends JpaRepository<Website, Integer> {
    boolean existsByUrl(@NotBlank(message = "Url cannot be blank") String url);
}
