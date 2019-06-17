package com.javafx.habr_spring.model;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FileModel {
    private final FileChooser fileChooser;
    private final DirectoryChooser directoryChooser;
    private static File file;
    private static File directory;
    private String filename;

    public FileModel(FileChooser fileChooser, DirectoryChooser directoryChooser) {
        this.fileChooser = fileChooser;
        this.fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Всі файли", "*.*"),
                new FileChooser.ExtensionFilter(".TXT", "*.txt"),
                new FileChooser.ExtensionFilter(".DOCX", "*.docx*")
        );
        this.directoryChooser = directoryChooser;
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
            System.out.println("Home project is " + homeProject);
            reader.close();
            scanner.close();
            } catch (IOException e) {
            e.printStackTrace();
        }

        fileChooser.setInitialDirectory(new File(homeProject.toString()));
        file = fileChooser.getInitialDirectory();
        System.out.println("File is " + file.getAbsolutePath());
        return file;
    }

    private File getProjectsDirectory() {
        return new File(System.getProperty("user.home"));
    }


    public void createProject(String nameProject, String pathProject, boolean main, boolean writer, boolean editor) {

    }

    public void createFile(boolean isFile) {

    }
}
