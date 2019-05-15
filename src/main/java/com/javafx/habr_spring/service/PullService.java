package com.javafx.habr_spring.service;

import com.javafx.habr_spring.domain.WriterFile;
import com.javafx.habr_spring.repository.PullRepository;
import com.javafx.habr_spring.repository.ServerFileRepository;
import com.javafx.habr_spring.server.PullData;
import com.javafx.habr_spring.server.ServerFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class PullService {
    private final ServerFileRepository fileRepository;
    private final PullRepository pullRepository;
    @Autowired
    public PullService(ServerFileRepository fileRepository, PullRepository pullRepository) {
        this.fileRepository = fileRepository;
        this.pullRepository = pullRepository;
    }


}
