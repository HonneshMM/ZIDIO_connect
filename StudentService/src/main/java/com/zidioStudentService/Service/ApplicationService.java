package com.zidioStudentService.Service;


import com.zidioStudentService.Entities.Application;
import com.zidioStudentService.Repository.ApplicationRepository;
import com.zidioStudentService.dto.ApplicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepo;

    public String applyToJob(String studentEmail, ApplicationRequest request) {
        Application app = new Application();
        app.setStudentEmail(studentEmail);
        app.setJobId(request.jobId);
        app.setResumeUrl(request.resumeUrl);
        app.setAppliedDate(LocalDate.now());
        app.setStatus("Applied");
        applicationRepo.save(app);
        return "Application submitted successfully";
    }

    public List<Application> getMyApplications(String studentEmail) {
        return applicationRepo.findByStudentEmail(studentEmail);
    }

    public List<Application> getApplicationsForJob(Long jobId) {
        return applicationRepo.findByJobId(jobId);
    }
    
    public String updateApplicationStatus(ApplicationRequest request) {
        Application app = applicationRepo.findById(request.getJobId())
            .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(request.getStatus());
        applicationRepo.save(app);
        return "Application status updated to " + request.getStatus();
    }
}
