package com.zidioStudentService.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zidioStudentService.Entities.Application;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudentEmail(String studentEmail);
    List<Application> findByJobId(Long jobId);
}
