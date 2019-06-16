package com.javafx.habr_spring.service;

import com.javafx.habr_spring.model.client.ClientFile;
import com.javafx.habr_spring.model.client.Commit;
import com.javafx.habr_spring.model.server.Push;
import com.javafx.habr_spring.repository.client.ClientFileRepository;
import com.javafx.habr_spring.repository.server.PushRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PushService {
    @Autowired
    private PushRepository pushRepository;

    @Autowired
    private CommitService commitService;

    @Autowired
    private ClientFileRepository fileRepository;

    public void pushFile(ClientFile file, Commit commit, Push push) {
        commitService.commitFile(file, commit);
        if(!fileRepository.findByFilename(file.getFilename()).equals(file)) {
            fileRepository.save(file);
            pushRepository.save(push);
        }
    }
}
