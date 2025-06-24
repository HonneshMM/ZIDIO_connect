package com.zidioRecruiterService.controller;

import com.zidioRecruiterService.dto.JobRequest;
import com.zidioRecruiterService.dto.RecruiterRequest;
import com.zidioRecruiterService.entities.Job;
import com.zidioRecruiterService.service.RecruiterService;
import com.zidioRecruiterService.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiters")
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    @Autowired
    private JwtUtil jwtUtil;

    private String extractEmail(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        return jwtUtil.extractEmail(authHeader.substring(7));
    }

    @PostMapping("/register")
    public ResponseEntity<RecruiterRequest> register(@RequestBody RecruiterRequest request) {
        return ResponseEntity.ok(recruiterService.saveRecruiter(request));
    }

    @PostMapping("/post-job")
    public ResponseEntity<Job> postJob(@RequestBody JobRequest request, HttpServletRequest httpRequest) {
        // Optional: extract recruiter email from JWT instead of body
        String email = extractEmail(httpRequest);
        request.recruiterEmail = email;
        return ResponseEntity.ok(recruiterService.postJob(request));
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(recruiterService.getAllJobs());
    }

    @GetMapping("/jobs/me")
    public ResponseEntity<List<Job>> getMyJobs(HttpServletRequest request) {
        String email = extractEmail(request);
        return ResponseEntity.ok(recruiterService.getPostedJobs(email));
    }

    @GetMapping("/{email}")
    public ResponseEntity<RecruiterRequest> getRecruiterByEmail(@PathVariable String email) {
        return ResponseEntity.ok(recruiterService.getByRecruiterEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<RecruiterRequest>> getAllRecruiters() {
        return ResponseEntity.ok(recruiterService.getAllRecruiters());
    }
}
