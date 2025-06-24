package com.zidioStudentService.Controller;

import java.io.IOException;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zidioStudentService.Entities.Application;
import com.zidioStudentService.Repository.ApplicationRepository;
import com.zidioStudentService.Service.FileStorageService;
import com.zidioStudentService.Util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;

@RestController
@RequestMapping("/student")
public class ResumeController {

    @Autowired
    private FileStorageService fileService;

    @Autowired
    private ApplicationRepository applicationRepo;
    
    @PostMapping("/apply-with-resume/{jobId}")
    public ResponseEntity<String> applyWithResume(
            @PathVariable Long jobId,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) throws IOException {

        String email = JwtUtil.extractEmail(request.getHeader("Authorization").substring(7));
        String filename = fileService.saveFile(file);
        String fileUrl = "http://localhost:8003/student/resume/" + filename;

        Application app = new Application();
        app.setStudentEmail(email);
        app.setJobId(jobId);
        app.setStatus("Pending");
//        app.setAppliedAt();
        app.setResumeUrl(fileUrl);

        applicationRepo.save(app);
        return ResponseEntity.ok("Applied to job with resume!");
    }

}
