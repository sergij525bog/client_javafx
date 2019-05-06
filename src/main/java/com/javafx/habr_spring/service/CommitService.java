package com.javafx.habr_spring.service;

import com.javafx.habr_spring.model.WriterFile;
import com.javafx.habr_spring.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommitService {
    private final FileRepository fileRepository;

    @Autowired
    public CommitService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void commitFile(WriterFile file) {
        fileRepository.save(file);
    }
}
