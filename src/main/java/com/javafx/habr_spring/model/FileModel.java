package com.javafx.habr_spring.model;

import com.javafx.habr_spring.domain.WriterFile;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class FileModel {
    private final FileChooser fileChooser;
    private static File file;
    private static File directory;
    private String filename;

    /*public void open(Path file) {
        try {
            List<String> filedata = Files.readAllLines(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(WriterFile file) {
        try {
            Files.write(file.getFilename(), file.getFiledata(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public FileModel(FileChooser fileChooser) {
        this.fileChooser = fileChooser;
        this.fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Всі файли", "*.*"),
                new FileChooser.ExtensionFilter(".TXT", "*.txt"),
                new FileChooser.ExtensionFilter(".DOCX", "*.docx*")
        );
    }

    public File open(OpenFileType type) {
        switch(type) {
            case PROJECT: {
                fileChooser.setInitialDirectory(getProjectsDirectory());
            }
            case FILE:
                fileChooser.setInitialDirectory(getCurrentDirectory());
        }
        return fileChooser.showOpenDialog(null);
    }

    private File getCurrentDirectory() {
        try {
            File file = new File((System.getProperty("user.home") + "/all_projects.txt"));
            FileReader reader = new FileReader(file);
            Scanner scanner = new Scanner(reader);
            String homeProject = "";
            while (scanner.hasNext()) {
                homeProject += scanner.nextLine() + "\n";
            }
            System.out.println(homeProject);
            reader.close();
            scanner.close();
            } catch (IOException e) {
            e.printStackTrace();
        }
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Документи"));
        file = fileChooser.getInitialDirectory();
        System.out.println("File is " + file.getAbsolutePath());
        return file;
    }

    private File getProjectsDirectory() {
        return new File(System.getProperty("user.home"));
    }
}
