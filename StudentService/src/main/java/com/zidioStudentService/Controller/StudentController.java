package com.zidioStudentService.Controller;

import com.zidioStudentService.Entities.Application;
import com.zidioStudentService.Entities.Job;
import com.zidioStudentService.Service.ApplicationService;
import com.zidioStudentService.Service.studentService;
import com.zidioStudentService.Util.JwtUtil;
import com.zidioStudentService.dto.ApplicationRequest;
import com.zidioStudentService.dto.StudentDTO;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private studentService studentService;
    
    @Autowired private ApplicationService applicationService;

    @Autowired
    private JwtUtil jwtUtil;

    private String getEmailFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        return jwtUtil.extractEmail(token);
    }

    @PostMapping("/profile")
    public ResponseEntity<String> save(@RequestBody StudentDTO dto, HttpServletRequest request) {
        String email = getEmailFromToken(request);
        return ResponseEntity.ok(studentService.createOrUpdate(email, dto));
    }

    //get the profile
    @GetMapping("/profile")
    public ResponseEntity<StudentDTO> get(HttpServletRequest request) {
        String email = getEmailFromToken(request);
        return ResponseEntity.ok(studentService.getProfile(email));
    }
    
    //student can apply for the job
    @PostMapping("/apply")
    public ResponseEntity<String> applyJob(@RequestBody ApplicationRequest request, HttpServletRequest http) {
        String email = getEmailFromToken(http);
        return ResponseEntity.ok(applicationService.applyToJob(email, request));
    }

    //student can see the their application
    @GetMapping("/my-applications")
    public ResponseEntity<List<Application>> getMyApps(HttpServletRequest http) {
        String email = getEmailFromToken(http);
        return ResponseEntity.ok(applicationService.getMyApplications(email));
    }
}
