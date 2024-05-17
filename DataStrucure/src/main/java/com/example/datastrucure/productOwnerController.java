package com.example.datastrucure;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class productOwnerController {
    @FXML
    private Label Nextbtn, exitBtn, note;
    @FXML
    private TextField id, txt, lvl, ctr;
    @FXML
    private Button add;

    public void ExitBtnClick1(MouseEvent event) {
        Platform.exit();
    }

    public void Nextbtn(MouseEvent event) throws IOException {
        login next = new login();
        next.start(new Stage());
        Stage currentStage = (Stage) Nextbtn.getScene().getWindow();
        currentStage.close();
    }

    public void add(ActionEvent event) {
        if (id.getText().isEmpty() || txt.getText().isEmpty() || lvl.getText().isEmpty() || ctr.getText().isEmpty()) {
            Platform.runLater(() -> {
                note.setStyle("-fx-alignment: center;");
                note.setText("You can't leave empty fields");
            });
        }
        else if(!lvl.getText().equals("easy")&&!lvl.getText().equals("medium")&&!lvl.getText().equals("hard"))
        {
            Platform.runLater(() -> {
                note.setStyle("-fx-alignment: center;");
                note.setText("Task Level must be 'easy' or 'hard' or 'medium'");
            });
        }
        else {
            Connection c = null;
            Statement st = null;
            try {
                c = DriverManager.getConnection("jdbc:mysql://localhost:3306/datastructure", "root", "632004");
                String sql = "INSERT INTO userstories (id, level, story, acceptance_criteria) VALUES ('" +
                        id.getText() + "', '" + lvl.getText() + "', '" + txt.getText() + "', '" + ctr.getText() + "')";
                st = c.createStatement();
                st.executeUpdate(sql);
                Platform.runLater(() -> {
                    note.setStyle("-fx-alignment: center; -fx-text-fill: green;");
                    note.setText("Record added successfully");
                });
            } catch (SQLException e) {
                Platform.runLater(() -> {
                    note.setStyle("-fx-alignment: center; -fx-text-fill: red;");
                    note.setText("Error: " + e.getMessage());
                });
                e.printStackTrace();
            } finally {
                try {
                    if (st != null) {
                        st.close();
                    }
                    if (c != null) {
                        c.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
