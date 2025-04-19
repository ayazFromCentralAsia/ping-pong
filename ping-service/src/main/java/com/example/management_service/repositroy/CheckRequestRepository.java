package com.example.management_service.repositroy;

import com.example.management_service.entity.CheckRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckRequestRepository extends JpaRepository<CheckRequest, Integer> {

}
