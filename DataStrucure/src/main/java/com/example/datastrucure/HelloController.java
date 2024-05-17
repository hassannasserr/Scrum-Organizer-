package com.example.datastrucure;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    Label Nextbtn,exitBtn;

    public void ExitBtnClick1(MouseEvent event){
        Platform.exit();
    }

    public void exitBtn(MouseEvent event) throws IOException {
        login next=new login();
        next.start(new Stage());
        Stage currentStage = (Stage) exitBtn.getScene().getWindow();
        currentStage.close();
    }
}