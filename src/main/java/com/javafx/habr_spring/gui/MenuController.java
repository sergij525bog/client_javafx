package com.javafx.habr_spring.gui;


import com.javafx.habr_spring.model.FileModel;
import com.javafx.habr_spring.model.OpenFileType;
import com.javafx.habr_spring.model.client.ClientFile;
import com.javafx.habr_spring.model.client.Commit;
import com.javafx.habr_spring.service.CommitService;
import com.javafx.habr_spring.service.PullService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class MenuController {
    @Autowired
    private CommitService commitService;
    @Autowired
    private PullService pullService;

    @FXML
    private TextArea area;

    @FXML
    private MenuBar menuBar;

    @FXML
    private TextField descriptionField;

    @FXML
    private TreeView filesTree;

    private ArrayList<String> files = new ArrayList<>();
    private ArrayList<String> directories = new ArrayList<>();

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
        setFileTree("/home/sbogdan/IdeaProjects/aws");
    }

    private void setFileTree(String path) {
        filesTree.setRoot(createTree(new File(path)));
        filesTree.setCellFactory(event -> new TreeCell<File>(){
            @Override
            protected void updateItem(File item, boolean empty) {
                super.updateItem(item, empty);
                if(item != null) {
                    setText(item.getName());
                } else {
                    setText("");
                }
            }
        });
    }

    @FXML
    public void openProject() {
        directory = fileModel.open(OpenFileType.PROJECT, window);
        setFileTree(directory.getAbsolutePath());
    }

    private TreeItem<File> createTree(File file) {
        TreeItem<File> item = new TreeItem<>(file);
        File[] childs = file.listFiles();
        if (childs != null) {
            for (File child : childs) {
                item.getChildren().add(createTree(child));
            }
        }
        return item;
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
        ClientFile file = new ClientFile();
        if(MenuController.file != null) {
            file.setFilename(MenuController.file.getName());
            file.setFilesize(MenuController.file.length());
            file.setFiledata(area.getText().getBytes());
            ArrayList<ClientFile> filesToCommit = commitService.checkFilesOnChanges(file);
            if (filesToCommit != null && !filesToCommit.isEmpty()) {
                commitService.commitFiles(filesToCommit, /*descriptionField.getText()*/ "Some description");
            }
        } else {
            errorWindow("Жодного файлу не вибрано!");
        }
    }

    private void errorWindow(String error) {
        Stage stage = new Stage();
        stage.setTitle("Помилка!!!");

        Label errorLabel = new Label(error);
        Button ok = new Button("Зрозуміло");
        ok.setOnAction(event -> {
            stage.close();
        });

        BorderPane pane = new BorderPane();
        pane.setCenter(errorLabel);
        pane.setBottom(ok);
        //pane.getChildren().addAll(errorLabel, ok);
        Scene scene = new Scene(pane, 300, 200);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(window);
        stage.show();
    }

    @FXML
    private void pushFile() {

    }

    @FXML
    private void pullFile() {
        /*Optional<WriterFile> optionalFileModel = pullService.pullFile(1L);
        WriterFile writerFile = optionalFileModel.get();
        System.out.println(writerFile.getFilename());*/
    }

    @FXML
    private Commit loadCommits() {
        Stage newWindow = new Stage();
        Button click = new Button("OK");
        ArrayList<Commit> commits = commitService.loadCommits(commitService.findById(1L).getId());
        String[] filenames = new String[commits.size()];
        String[] descriptions = new String[commits.size()];
        Date[] dates = new Date[commits.size()];
        String[] variants = new String[commits.size()];
        for(int i = 0; i < commits.size(); i++) {
            filenames[i] = commits.get(i).getCurrentFile().getFilename();
            descriptions[i] = commits.get(i).getDescription();
            dates[i] = commits.get(i).getCommitDate();
            variants[i] = filenames[i] + " | " + descriptions[i] + " | " + dates[i];
            System.out.println(variants[i]);
        }
        ObservableList<String> versions = FXCollections.observableArrayList(variants);
        ChoiceBox<String> langsChoiceBox = new ChoiceBox<>(versions);
        if(variants.length > 0) {
            langsChoiceBox.setValue(variants[variants.length - 1]);
        }

        Label lbl = new Label();
        langsChoiceBox.setOnAction(event -> lbl.setText(langsChoiceBox.getValue()));

        click.setOnAction(e -> {
            System.out.println("Field return " + langsChoiceBox.getValue());
            newWindow.close();
        });
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().addAll(langsChoiceBox, lbl, click);

        Scene secondScene = new Scene(secondaryLayout, 230, 100);

        newWindow.setTitle("Список версій файлу");
        newWindow.setScene(secondScene);

        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(window);

        // Set position of second window, related to primary window.
        newWindow.setHeight(500);
        newWindow.setWidth(400);
        newWindow.centerOnScreen();
        newWindow.show();

        //commitService.loadFiles();
        return null;
    }

    private void printLog(TextArea area, File file){

    }

    private void modalCreate(String title, boolean isFile) {
        Stage createWindow = new Stage();
        createWindow.setTitle(title);

        Button okButton = new Button("Ок");
        Button cancelButton = new Button("Відмінити");

        Label name = new Label("Назва");
        Label type = new Label("Тип");

        GridPane pane = new GridPane();

        ObservableList<String> types = FXCollections.observableArrayList("Файл", "Папка");

        TextField filename = new TextField("");
        ComboBox fileType = new ComboBox(types);
        if (isFile) {
            fileType.setValue(types.get(0));
        } else {
            fileType.setValue(types.get(1));
        }

        okButton.setOnAction(event -> {
            fileModel.createFile(fileType.getValue() == types.get(0));
            createWindow.close();
        });

        cancelButton.setOnAction(event -> {
            createWindow.close();
        });

        pane.add(name, 1, 1);
        pane.add(filename, 2, 1);
        pane.add(type, 1, 2);
        pane.add(fileType, 2, 2);
        pane.add(okButton, 2, 3);
        pane.add(cancelButton, 3, 3);

        Scene scene = new Scene(pane, 600, 400);
        createWindow.setScene(scene);
        createWindow.initModality(Modality.WINDOW_MODAL);
        createWindow.initOwner(window);
        createWindow.show();
    }

    @FXML
    public void aboutProgram() {

    }

    @FXML
    public void createProject() {
        Stage newWindow = new Stage();
        Button okButton = new Button("Ок");
        Button cancelButton = new Button("Відмінити");

        Label nameProject = new Label("Назва");
        Label pathProject = new Label("Шлях");
        TextField name = new TextField("Новий проект");
        TextField path = new TextField("");

        CheckBox mainBranch = new CheckBox("Головна частина");
        CheckBox writerBranch = new CheckBox("Частина автора");
        CheckBox editorBranch = new CheckBox("Частина редактора");

        okButton.setOnAction(event -> {
            fileModel.createProject(name.getText(), path.getText(), mainBranch.isSelected(), writerBranch.isSelected(), editorBranch.isSelected());
        });

        GridPane pane = new GridPane();

        Scene secondScene = new Scene(pane, 230, 100);

        newWindow.setTitle("Новий проект");
        newWindow.setScene(secondScene);

        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(window);

        // Set position of second window, related to primary window.
        newWindow.setHeight(500);
        newWindow.setWidth(400);
        newWindow.centerOnScreen();
        newWindow.show();
    }

    @FXML
    public void createDirectory() {
        modalCreate("Нова папка", false);
    }

    @FXML
    public void createFile() {
        modalCreate("Новий файл", true);
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

    private void modalRenameDelete (String title, boolean task) {
        Stage stage = new Stage();
        stage.setTitle(title);

        Label errorLabel = new Label("Введіть нову назву");
        TextField newName = new TextField();
        Button okButton = new Button("Ок");
        Button cancelButton = new Button("Відмінити");
        okButton.setOnAction(event -> {
            /*if (task) {
                fileModel.renameFile(newName.getText());
            } else {
                fileModel.deleteFile();
            }*/
            stage.close();
        });

        cancelButton.setOnAction(event -> {
            stage.close();
        });

        GridPane pane = new GridPane();

        Scene scene = new Scene(pane, 300, 200);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(window);
        stage.show();
    }
}
