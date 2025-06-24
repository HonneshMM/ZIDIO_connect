package com.zidioRecruiter.RecruiterService.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zidioRecruiter.RecruiterService.DTO.ApplicationStatusUpdateRequest;
import com.zidioRecruiter.RecruiterService.DTO.JobRequest;
import com.zidioRecruiter.RecruiterService.DTO.RecruiterRequest;
import com.zidioRecruiter.RecruiterService.Entity.Job;
import com.zidioRecruiter.RecruiterService.Service.RecruiterService;
import com.zidioRecruiter.RecruiterService.Util.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/api/recruiters")
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    @Autowired
    private JwtUtil jwtUtil;

    
    //fetching the data or email from the token which is based on the login
    private String extractEmail(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        return jwtUtil.extractEmail(authHeader.substring(7));
    }

    //new recruiter registration
    @PostMapping("/register")
    public ResponseEntity<RecruiterRequest> register(@RequestBody RecruiterRequest request) {
        return ResponseEntity.ok(recruiterService.saveRecruiter(request));
    }

    // recruiter posting the job
    @PostMapping("/post-job")
    public ResponseEntity<Job> postJob(@RequestBody JobRequest request, HttpServletRequest httpRequest) {
        
        String email = extractEmail(httpRequest);
        request.recruiterEmail = email;
        return ResponseEntity.ok(recruiterService.postJob(request));
    }

    //getting all the jobs which are available
    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(recruiterService.getAllJobs());
    }

    //recruiter can get or see the their posted jobs
    @GetMapping("/jobs/me")
    public ResponseEntity<List<Job>> getMyJobs(HttpServletRequest request) {
        String email = extractEmail(request);
        return ResponseEntity.ok(recruiterService.getPostedJobs(email));
    }
    
    //get recruiter through their email id
    @GetMapping("/{email}")
    public ResponseEntity<RecruiterRequest> getRecruiterByEmail(@PathVariable String email) {
        return ResponseEntity.ok(recruiterService.getByRecruiterEmail(email));
    }

    //get all registered recruiters
    @GetMapping
    public ResponseEntity<List<RecruiterRequest>> getAllRecruiters() {
        return ResponseEntity.ok(recruiterService.getAllRecruiters());
    }
    
    //update the application status
    @PutMapping("/update-application-status")
    public ResponseEntity<String> updateApplicationStatus(@RequestBody ApplicationStatusUpdateRequest request) {
        return ResponseEntity.ok(recruiterService.updateStudentApplicationStatus(request));
    }
}
