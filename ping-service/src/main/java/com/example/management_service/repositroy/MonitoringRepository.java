package com.example.management_service.repositroy;


import com.example.management_service.entity.MonitoringLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringRepository extends JpaRepository<MonitoringLogs, Integer> {

}