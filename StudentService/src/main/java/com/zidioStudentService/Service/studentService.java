package com.zidioStudentService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zidioStudentService.Entities.Student;
import com.zidioStudentService.Repository.StudentRepository;
import com.zidioStudentService.dto.StudentDTO;

@Service
public class studentService {

    @Autowired
    private StudentRepository studentRepository;

    public String createOrUpdate(String email, StudentDTO dto) {
        Student student = studentRepository.findByEmail(email).orElse(new Student());
        student.setName(dto.name);
        student.setEmail(email); // override to ensure integrity
        student.setCourse(dto.course);
        student.setUniversity(dto.university);
        student.setResumeUrl(dto.resumeUrl);
        studentRepository.save(student);
        return "Student Profile saved successfully.";
    }

    public StudentDTO getProfile(String email) {
        Student s = studentRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Student not found"));
        StudentDTO dto = new StudentDTO();
        dto.name = s.getName();
        dto.email = s.getEmail();
        dto.course = s.getCourse();
        dto.university = s.getUniversity();
        dto.resumeUrl = s.getResumeUrl();
        return dto;
    }
}
