package com.zidioRecruiter.RecruiterService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zidioRecruiter.RecruiterService.Entity.Recruiter;

public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {
    Recruiter findByRecruiterEmail(String recruiterEmail);
}
