package com.example.datastrucure;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class developer extends Application {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("developer.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1000, 560);

    public developer() throws IOException {
    }

    @Override
    public void start(Stage stage) throws IOException {

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}