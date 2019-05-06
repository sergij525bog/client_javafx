package com.javafx.habr_spring.service;

import com.javafx.habr_spring.model.WriterFile;
import com.javafx.habr_spring.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PullService {
    private final FileRepository fileRepository;

    @Autowired
    public PullService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public Optional<WriterFile> pullFile(Long id) {
        return fileRepository.findById(id);
    }
}
