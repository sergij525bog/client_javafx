package com.javafx.habr_spring.service;

import com.javafx.habr_spring.domain.CommitData;
import com.javafx.habr_spring.domain.WriterFile;
import com.javafx.habr_spring.repository.CommitRepository;
import com.javafx.habr_spring.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Service
public class CommitService {
    private final FileRepository fileRepository;
    private final CommitRepository commitRepository;

    @Autowired
    public CommitService(FileRepository fileRepository, CommitRepository commitRepository) {
        this.fileRepository = fileRepository;
        this.commitRepository = commitRepository;
    }

    public void commitFile(WriterFile file, CommitData commit) {
        if(!fileRepository.findByFilename(file.getFilename()).equals(file)) {
            fileRepository.save(file);
            commitRepository.save(commit);
        }
    }

    public void commitFiles(ArrayList<WriterFile> files, String commitDescription) {
        fileRepository.saveAll(files);
        ArrayList<CommitData> commits = new ArrayList<>();
        CommitData commit = new CommitData();
        for (int i = 0; i < files.size(); i++) {
            commit.setCommit(fileRepository.findByFilename(files.get(i).getFilename()));
            commit.setFiledata(files.get(i).getFiledata());
            commit.setFilesize(files.get(i).getFilesize());
            commit.setCommitDate(new Date());
            commit.setDescription(commitDescription + i);
            commits.add(commit);
        }
        commitRepository.saveAll(commits);
    }

    public ArrayList<WriterFile> checkFilesOnChanges(WriterFile... files) {
        ArrayList<WriterFile> unsavedFiles = new ArrayList<>();
        for (WriterFile file : files) {
            WriterFile f = fileRepository.findByFilename(file.getFilename());
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

    public ArrayList<WriterFile> loadFiles(Date commitDate, WriterFile... files) {
        ArrayList<WriterFile> fileList = new ArrayList<>();
        CommitData commit = new CommitData();
        WriterFile file = new WriterFile();
        for (WriterFile fileFromDb : files) {
           commit = commitRepository.findByCommitAndCommitDate(fileFromDb.getId(), commitDate);
           file = fileRepository.findById(commit.getCommit().getId()).get();
           file.setFiledata(commit.getFiledata());
           file.setFilesize(commit.getFilesize());
           fileRepository.save(file);
           fileList.add(file);
        }
        return fileList;
    }
}
