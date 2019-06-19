package com.javafx.habr_spring.service;

import com.javafx.habr_spring.model.OpenFileType;
import com.javafx.habr_spring.model.client.ClientFile;
import com.javafx.habr_spring.model.client.ClientProject;
import com.javafx.habr_spring.repository.client.ClientProjectRepository;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Service
public class FileService {
    @Autowired
    private ClientProjectRepository projectRepository;

    private FileChooser fileChooser = new FileChooser();
    private DirectoryChooser directoryChooser = new DirectoryChooser();
    private static File file;
    private static File directory;
    private String filename;

    public FileService() {
        this.fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Всі файли", "*.*"),
                new FileChooser.ExtensionFilter(".TXT", "*.txt"),
                new FileChooser.ExtensionFilter(".DOCX", "*.docx*")
        );
        this.directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    public File open(OpenFileType type, Stage window) {
        switch(type) {
            case PROJECT: {
                directoryChooser.setInitialDirectory(getProjectsDirectory());
                return directoryChooser.showDialog(window);
            }
            case FILE:
                fileChooser.setInitialDirectory(getCurrentDirectory());
                return fileChooser.showOpenDialog(window);
        }
        return null;
    }

    private File getCurrentDirectory() {
        StringBuilder homeProject = new StringBuilder();
        try {
            File file = new File((System.getProperty("user.home") + "/all_projects.txt"));
            String[] list = file.list();
            for (String s : list != null ? list : new String[0]) {
                System.out.println(s);
            }
            FileReader reader = new FileReader(file);
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNext()) {
                homeProject.append(scanner.nextLine());
            }
            //System.out.println("Home project is " + homeProject);
            reader.close();
            scanner.close();
            } catch (IOException e) {
            e.printStackTrace();
        }

        fileChooser.setInitialDirectory(new File(homeProject.toString()));
        file = fileChooser.getInitialDirectory();
        //System.out.println("File is " + file.getAbsolutePath());
        return file;
    }

    private File getProjectsDirectory() {
        return new File(System.getProperty("user.home"));
    }


    public void createProject(String nameProject, String pathProject, boolean main, boolean writer, boolean editor) {
        File newProject = new File(pathProject + "/" + nameProject);
        if (newProject.mkdir()) {
            ClientProject clientProject = new ClientProject(newProject.getName());
            projectRepository.save(clientProject);
        }
        Set<ClientFile> files = new HashSet<>();
        if(main) {
            File mainBranch = new File(newProject.getAbsolutePath() + "/" + "Головна частина");
            mainBranch.mkdir();
        }
        if(writer) {
            File writerBranch = new File(newProject.getAbsolutePath() + "/" + "Частина автора");
            writerBranch.mkdir();
        }
        if(main) {
            File editorBranch = new File(newProject.getAbsolutePath() + "/" + "Частина редактора");
            editorBranch.mkdir();
        }
    }

    public boolean createFile(String filename, boolean isFile) {
        File newFile = new File(filename);
        if (isFile) {
            if (!newFile.exists()) {
                try {
                    return newFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if(!newFile.exists()) {
                return newFile.mkdir();
            }
        }
        return false;
    }
}
