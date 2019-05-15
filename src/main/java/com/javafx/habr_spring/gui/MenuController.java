package com.javafx.habr_spring.gui;

import com.javafx.habr_spring.domain.CommitData;
import com.javafx.habr_spring.domain.WriterFile;
import com.javafx.habr_spring.model.FileModel;
import com.javafx.habr_spring.model.OpenFileType;
import com.javafx.habr_spring.service.CommitService;
import com.javafx.habr_spring.service.PullService;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class MenuController {
    @Autowired private CommitService commitService;
    @Autowired private PullService pullService;

    @FXML private TextArea area;
    @FXML private MenuBar menuBar;
    @FXML private TextField descriptionField;
    private Stage window;

    private final FileChooser fileChooser = new FileChooser();
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private static File file;
    private static File directory;
    private String filename;
    private FileModel fileModel = new FileModel(fileChooser, directoryChooser);

    @FXML
    public void initialize() {

    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        menuBar = new MenuBar();
    }

    @FXML
    public void openProject() {
        file = fileModel.open(OpenFileType.PROJECT, window);
    }

    @FXML
    private void openFile(){
        try {
            file = fileModel.open(OpenFileType.FILE, window);
            if(file != null) {
                FileReader reader = new FileReader(file);
                Scanner scanner = new Scanner(reader);
                area.clear();
                while (scanner.hasNext()) {
                    area.appendText(scanner.nextLine() + "\n");
                }
                reader.close();
                scanner.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveFile(){
        if(file == null){
            System.out.println("File not opened");
            return;
        }
        this.filename = file.getAbsolutePath();
        try {
            FileWriter writer = new FileWriter(this.filename);
            if(!area.getText().isEmpty()){
                writer.write(area.getText());
                System.out.println("Save " + area.getScene());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveFileAs() {
        file = fileChooser.showSaveDialog(window);
        saveFile();
    }

    @FXML
    public void closeProgram(){
        if(file == null || file.toString().equals(area.getText()) || file.isDirectory()){
            System.out.println("Not found changes, program closed");
        } else {
            this.filename = file.getAbsolutePath();
            FileWriter writer;
            try {
                writer = new FileWriter(filename);
                writer.write(area.getText());
                System.out.println("File " + filename + " saved and closed");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        window = (Stage) area.getScene().getWindow();
        window.close();
    }

    @FXML
    private void commitFile(){
        WriterFile file = new WriterFile();
        file.setFilename(MenuController.file.getName());
        file.setFilesize(MenuController.file.length());
        file.setFiledata(area.getText().getBytes());
        ArrayList<WriterFile> filesToCommit = commitService.checkFilesOnChanges(file);
        if(!filesToCommit.isEmpty()) {
            commitService.commitFiles(filesToCommit, descriptionField.getText());
        }
    }

    @FXML
    private void pushFile(){

    }

    @FXML
    private void pullFile(){
        /*Optional<WriterFile> optionalFileModel = pullService.pullFile(1L);
        WriterFile writerFile = optionalFileModel.get();
        System.out.println(writerFile.getFilename());*/
    }

    private void printLog(javafx.scene.control.TextArea area, File file){

    }

    @FXML
    public void aboutProgram() {
    }

    @FXML
    public void createProject() {

    }

    @FXML
    public void createDirectory() {

    }

    @FXML
    public void createFile() {

    }

    @FXML
    public void renameDirectory() {

    }

    @FXML
    public void renameFile() {

    }

    @FXML
    public void deleteDirectory() {

    }

    @FXML
    public void deleteFile() {

    }
}
