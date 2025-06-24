package com.zidioStudentService.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zidioStudentService.Entities.Student;


public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByEmail(String email);
}