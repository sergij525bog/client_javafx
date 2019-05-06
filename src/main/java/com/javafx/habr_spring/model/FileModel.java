package com.javafx.habr_spring.model;

import com.javafx.habr_spring.domain.WriterFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileModel {
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
}
