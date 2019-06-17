package com.javafx.habr_spring.service;

import com.javafx.habr_spring.model.client.Commit;
import com.javafx.habr_spring.model.server.Push;
import com.javafx.habr_spring.model.server.ServerFile;
import com.javafx.habr_spring.repository.client.CommitRepository;
import com.javafx.habr_spring.repository.server.PushRepository;
import com.javafx.habr_spring.repository.server.ServerFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PushService {
    private final CommitRepository commitRepository;

    private final PushRepository pushRepository;

    @Autowired
    private ServerFileRepository fileRepository;

    public PushService(CommitRepository commitRepository, PushRepository pushRepository) {
        this.commitRepository = commitRepository;
        this.pushRepository = pushRepository;
    }

    public void pushFiles() {
        List<Commit> allCommits = commitRepository.findAll();
        ArrayList<Push> allPushes = new ArrayList<>();
        ArrayList<ServerFile> serverFiles= new ArrayList<>();
        Push push = new Push();
        ServerFile serverFile = new ServerFile();
        for(Commit commit : allCommits) {
            serverFile.setFilename(commit.getCurrentFile().getFilename());
            serverFile.setFiledata(commit.getFiledata());
            serverFile.setFilesize(commit.getFilesize());
            serverFiles.add(serverFile);
        }
        fileRepository.saveAll(serverFiles);
        for(ServerFile file : serverFiles) {
            push.setCurrentFile(file);
            push.setDescription("New description");
            push.setFiledata(file.getFiledata());
            push.setFilesize(file.getFilesize());
            push.setPushDate(new Date());
            allPushes.add(push);
        }
        pushRepository.saveAll(allPushes);
    }
}
