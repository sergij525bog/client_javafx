package com.javafx.habr_spring.service;


import com.javafx.habr_spring.model.client.ClientFile;
import com.javafx.habr_spring.model.client.ClientProject;
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
        ArrayList<Commit> commits = new ArrayList<>();
        Commit commit = new Commit();
        for (int i = 0; i < files.size(); i++) {
            ClientFile byFilename = fileRepository.findByFilename(files.get(i).getFilename());
            commit.setCurrentFile(byFilename);
            commit.setFiledata(files.get(i).getFiledata());
            commit.setFilesize((long) files.get(i).getFiledata().length);
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
                byte[] filedata = f.getFiledata();
                if(!Arrays.equals(file.getFiledata(), filedata)) {
                    f.setFiledata(file.getFiledata());
                    fileRepository.save(f);
                    unsavedFiles.add(file);
                }
            } else {
                fileRepository.save(file);
                unsavedFiles.add(file);
            }
        }
        return unsavedFiles;
    }

    /*public ArrayList<ClientFile> loadFiles(Date commitDate, Long projectId, ClientFile... files) {
        ArrayList<ClientFile> fileList = fileRepository.findByProject(projectId);
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
    }*/

    public ArrayList<Commit> loadCommits(Long projectId) {
        ArrayList<ClientFile> clientFiles = fileRepository.findByProject(projectId);
        ArrayList<Commit> commits = new ArrayList<>();
        for(int i = 0; i < clientFiles.size(); i++) {
            commits.addAll(commitRepository.findByCurrentFile(clientFiles.get(i).getId()));
            System.out.println(clientFiles.get(i).getFilename());
        }
        return commits;
    }
}
