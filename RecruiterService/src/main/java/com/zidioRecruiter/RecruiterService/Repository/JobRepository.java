package com.zidioRecruiter.RecruiterService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zidioRecruiter.RecruiterService.Entity.Job;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByPostedBy(String recruiterEmail);
}
