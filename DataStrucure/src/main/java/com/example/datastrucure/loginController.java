package com.example.datastrucure;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {
    Label Nextbtn,exitBtn;
    Button owner,manager,developer;

    public void ExitBtnClick1(MouseEvent event){
        Platform.exit();
    }

    public void Nextbtn(MouseEvent event) throws IOException {
        HelloApplication next= new HelloApplication();
        next.start(new Stage());

    }

    public void owner(ActionEvent event) throws IOException {
        productOwner next=new productOwner();
        next.start(new Stage());

    }

    public void manager(ActionEvent event) throws IOException {
        productManager next=new productManager();
        next.start(new Stage());

    }

    public void developer(ActionEvent event) throws IOException {
        developer next=new developer();
        next.start(new Stage());

    }
}
