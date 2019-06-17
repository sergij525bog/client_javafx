package com.javafx.habr_spring;

import com.javafx.habr_spring.configuration.ControllersConfiguration;
import com.javafx.habr_spring.gui.MenuController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ClientApplication extends ApplicationSupport{
    @Qualifier("mainView")
    @Autowired
    private ControllersConfiguration.ViewHolder view;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Must work");
        primaryStage.setScene(new Scene(view.getView()));
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> {
            ((MenuController) view.getController()).closeProgram();
        });

        primaryStage.show();
    }

    /*public void files(Stage primaryStage) throws Exception {


        StackPane myPane = new StackPane();
        myPane.getChildren().add(tree);
        Scene myScene = new Scene(myPane);

        primaryStage.setScene(myScene);
        primaryStage.setTitle("File Tree");
        primaryStage.show();

    }*/

    public static void main(String[] args) {
        launchApp(ClientApplication.class, args);
    }
}
