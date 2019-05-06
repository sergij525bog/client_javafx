package com.javafx.habr_spring.gui;

import com.javafx.habr_spring.model.CommitData;
import com.javafx.habr_spring.model.WriterFile;
import com.javafx.habr_spring.service.CommitService;
import com.javafx.habr_spring.service.PullService;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class MenuController {
    @Autowired private CommitService commitService;
    @Autowired private PullService pullService;

    @FXML private TextArea area;
    @FXML private MenuBar menuBar;
    private Stage window;

    private final FileChooser fileChooser = new FileChooser();
    private static File file;
    private String filename;

    @FXML
    public void initialize() {

    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        menuBar = new MenuBar();
        /*fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"),
                new FileChooser.ExtensionFilter("DOCX", "*.docx")
        );*/
    }

    @FXML
    private void openFile(){
        try {
            file = fileChooser.showOpenDialog(window);
            if(file != null) {
                this.filename = file.getAbsolutePath();
                FileReader reader = new FileReader(file);
                Scanner scanner = new Scanner(reader);
                area.setText("");
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
        if(file == null || file.toString().equals(area.getText())){
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
        CommitData commitData = new CommitData();
        file.setFilename(filename);
        Date date = new Date();
        file.setFilesize(MenuController.file.length());
        commitData.setFiledata(area.getText());
        commitData.setCommit_date(date);
        commitData.setDescription("This is an attempt of create and save file");
        commitService.commitFile(file);
    }

    @FXML
    private void pushFile(){

    }

    @FXML
    private void pullFile(){
        Optional<WriterFile> optionalFileModel = pullService.pullFile(1L);
        WriterFile writerFile = optionalFileModel.get();
        System.out.println(writerFile.getFilename());
    }

    private void printLog(javafx.scene.control.TextArea area, File file){

    }

    @FXML
    public void aboutProgram() {
    }

    @FXML
    public void createFile() {

    }
}
