package com.javafx.habr_spring;

import com.javafx.habr_spring.configuration.ControllersConfiguration;
import com.javafx.habr_spring.gui.MenuController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

    public static void main(String[] args) {
        launchApp(ClientApplication.class, args);
    }
}
