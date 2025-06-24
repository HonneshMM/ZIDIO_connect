package com.zidioRecruiter.RecruiterService.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.zidioRecruiter.RecruiterService.DTO.ApplicationStatusUpdateRequest;
import com.zidioRecruiter.RecruiterService.DTO.JobRequest;
import com.zidioRecruiter.RecruiterService.DTO.RecruiterRequest;
import com.zidioRecruiter.RecruiterService.Entity.Job;
import com.zidioRecruiter.RecruiterService.Entity.Recruiter;
import com.zidioRecruiter.RecruiterService.Repository.JobRepository;
import com.zidioRecruiter.RecruiterService.Repository.RecruiterRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecruiterService {

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private JobRepository jobRepository;

    public RecruiterRequest saveRecruiter(RecruiterRequest request) {
        Recruiter recruiter = new Recruiter();
        BeanUtils.copyProperties(request, recruiter);
        recruiterRepository.save(recruiter);

        RecruiterRequest response = new RecruiterRequest();
        BeanUtils.copyProperties(recruiter, response);
        return response;
    }

    public Job postJob(JobRequest request) {
        Job job = new Job();
        BeanUtils.copyProperties(request, job);
        job.setPostedBy(request.recruiterEmail);
        jobRepository.save(job);
        return job;
    }

    public List<Job> getPostedJobs(String recruiterEmail) {
        return jobRepository.findByPostedBy(recruiterEmail);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public RecruiterRequest getByRecruiterEmail(String email) {
        Recruiter r = recruiterRepository.findByRecruiterEmail(email);
        if (r == null) throw new RuntimeException("Recruiter not found");

        RecruiterRequest dto = new RecruiterRequest();
        BeanUtils.copyProperties(r, dto);
        return dto;
    }

    public List<RecruiterRequest> getAllRecruiters() {
        return recruiterRepository.findAll().stream().map(r -> {
            RecruiterRequest dto = new RecruiterRequest();
            BeanUtils.copyProperties(r, dto);
            return dto;
        }).collect(Collectors.toList());
    }
    
    @Autowired
    private RestTemplate restTemplate;

    public String updateStudentApplicationStatus(ApplicationStatusUpdateRequest request) {
        String studentServiceUrl = "http://localhost:8081/student/update-status"; // Replace with service discovery or gateway if needed

        HttpEntity<ApplicationStatusUpdateRequest> entity = new HttpEntity<>(request);
        ResponseEntity<String> response = restTemplate.exchange(studentServiceUrl, HttpMethod.PUT, entity, String.class);
        return response.getBody();
    }

}
