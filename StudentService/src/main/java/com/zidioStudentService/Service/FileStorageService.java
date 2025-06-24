package com.zidioStudentService.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.jvnet.hk2.annotations.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileStorageService {

    private final String uploadDir = "uploads/resumes/";

    public String saveFile(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + filename);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }

    public Resource loadFile(String filename) throws IOException {
        Path path = Paths.get(uploadDir + filename);
        return new UrlResource(path.toUri());
    }
}
