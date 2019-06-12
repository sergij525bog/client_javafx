package com.javafx.habr_spring.service;


import com.javafx.habr_spring.model.client.ClientFile;
import com.javafx.habr_spring.model.client.Commit;
import com.javafx.habr_spring.repository.client.ClientFileRepository;
import com.javafx.habr_spring.repository.client.CommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Service
public class CommitService {
    private final ClientFileRepository fileRepository;
    private final CommitRepository commitRepository;

    @Autowired
    public CommitService(ClientFileRepository fileRepository, CommitRepository commitRepository) {
        this.fileRepository = fileRepository;
        this.commitRepository = commitRepository;
    }

    public void commitFile(ClientFile file, Commit commit) {
        if(!fileRepository.findByFilename(file.getFilename()).equals(file)) {
            fileRepository.save(file);
            commitRepository.save(commit);
        }
    }

    public void commitFiles(ArrayList<ClientFile> files, String commitDescription) {
        fileRepository.saveAll(files);
        ArrayList<Commit> commits = new ArrayList<>();
        Commit commit = new Commit();
        for (int i = 0; i < files.size(); i++) {
            commit.setCurrentFile(fileRepository.findByFilename(files.get(i).getFilename()));
            commit.setFiledata(files.get(i).getFiledata());
            commit.setFilesize(files.get(i).getFilesize());
            commit.setCommitDate(new Date());
            commit.setDescription(commitDescription + i);
            commits.add(commit);
        }
        commitRepository.saveAll(commits);
    }

    public ArrayList<ClientFile> checkFilesOnChanges(ClientFile... files) {
        ArrayList<ClientFile> unsavedFiles = new ArrayList<>();
        for (ClientFile file : files) {
            ClientFile f = fileRepository.findByFilename(file.getFilename());
            if (f != null) {
                byte[] filedata = fileRepository.findByFilename(file.getFilename()).getFiledata();
                if(!Arrays.equals(file.getFiledata(), filedata)) {
                    unsavedFiles.add(file);
                }
            } else {
                unsavedFiles.add(file);
            }
        }
        return unsavedFiles;
    }

    public ArrayList<ClientFile> loadFiles(Date commitDate, ClientFile... files) {
        ArrayList<ClientFile> fileList = new ArrayList<>();
        Commit commit = new Commit();
        ClientFile file = new ClientFile();
        for (ClientFile fileFromDb : files) {
           commit = commitRepository.findByCurrentFileAndCommitDate(fileFromDb.getId(), commitDate);
           file = fileRepository.findById(commit.getCurrentFile().getId()).get();
           file.setFiledata(commit.getFiledata());
           file.setFilesize(commit.getFilesize());
           fileRepository.save(file);
           fileList.add(file);
        }
        return fileList;
    }
}
