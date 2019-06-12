package com.javafx.habr_spring.gui;


import com.javafx.habr_spring.model.FileModel;
import com.javafx.habr_spring.model.OpenFileType;
import com.javafx.habr_spring.model.client.ClientFile;
import com.javafx.habr_spring.model.client.Commit;
import com.javafx.habr_spring.service.CommitService;
import com.javafx.habr_spring.service.PullService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
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
        directory = fileModel.open(OpenFileType.PROJECT, window);
        filesTree = new TreeView();
        TreeItem<String> projectDir = new TreeItem<>(directory.getName());
        projectDir.setExpanded(true);
        getTree(directory.getAbsolutePath());

    }

    public void getTree(String path) {
        TreeItem<Object> tree = new TreeItem<>(path.substring(path.lastIndexOf(File.separator)));
        List<TreeItem<Object>> dirs = new ArrayList<>();
        List<TreeItem<Object>> files = new ArrayList<>();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path))) {
            for(Path dPath: directoryStream) {
                if (Files.isDirectory(dPath)) {
                    TreeItem<Object> subDirectory = new TreeItem<>(path);
                    getSubLeafs(dPath, subDirectory);
                    dirs.add(subDirectory);
                } else {
                    files.add(getLeafs(dPath));
                }
            }

            tree.getChildren().addAll(dirs);
            tree.getChildren().addAll(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TreeItem<Object> getLeafs(Path subPath) {
        String strPath = subPath.toString();
        System.out.println("leaf " + strPath);
        TreeItem<Object> leafs = new TreeItem<>(strPath.substring(1 + strPath.lastIndexOf(File.separator)));
        return leafs;
    }

    private void getSubLeafs(Path subPath, TreeItem<Object> parent) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(subPath.toString()))) {
            for(Path subDir: directoryStream) {
                // explicit search for files because we dont want to get sub-sub-directories
                String subTree = subDir.toString();
                if (!Files.isDirectory(subDir)) {

                    System.out.println("subLeaf" + subTree);
                    TreeItem<Object> subLeafs = new TreeItem<>(subTree.substring(1 + subTree.lastIndexOf(File.separator)));
                    parent.getChildren().add(subLeafs);
                } else {
                    System.out.println("directory " + subTree);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        file.setFilename(MenuController.file.getName());
        file.setFilesize(MenuController.file.length());
        file.setFiledata(area.getText().getBytes());
        ArrayList<ClientFile> filesToCommit = commitService.checkFilesOnChanges(file);
        if(filesToCommit != null && !filesToCommit.isEmpty()) {
            commitService.commitFiles(filesToCommit, /*descriptionField.getText()*/ "Some description");
        }
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
    private Commit loadFile() {
        Label secondLabel = new Label("I'm a Label on new Window");
        TextField field = new TextField();
        Button click = new Button("OK");
        click.setOnAction(e -> {
            System.out.println("Field return " + field.getText());
        });
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().addAll(secondLabel, field, click);

        Scene secondScene = new Scene(secondaryLayout, 230, 100);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);

        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(window);

        // Set position of second window, related to primary window.
        newWindow.setX(300);
        newWindow.setY(200);

        newWindow.show();

        //commitService.loadFiles();
        return null;
    }

    private void printLog(TextArea area, File file){

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
