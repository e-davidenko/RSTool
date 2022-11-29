package com.excecc.rservertool;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;


//jpackage --input C:\Users\davidenko.KGP\IdeaProjects\RServerTool\out\artifacts\RServerTool_jar --main-jar RServerTool_jar.jar --icon icon.ico --name RSTool --app-version "0.6.3" --copyright "Created by e-davidenko in KRGP" --vendor "IB KRGP" --win-shortcut --win-per-user-install --win-dir-chooser


public class Application extends javafx.application.Application {





    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("RServerTool");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        launch();
    }
}