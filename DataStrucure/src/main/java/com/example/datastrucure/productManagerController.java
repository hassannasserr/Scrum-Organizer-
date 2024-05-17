package com.example.datastrucure;

import com.example.datastrucure.BinarySearchTree;
import com.example.datastrucure.UserStory;
import com.example.datastrucure.login;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Set;

public class productManagerController {
    @FXML
    Label nextBtn, exitBtn, note;
    @FXML
    TextField id, numbers, lvl;
    @FXML
    Button add;

    public void exitBtnClick1(MouseEvent event) {
        Platform.exit();
    }

    public void nextBtn(MouseEvent event) throws IOException {
        login next = new login();
        next.start(new Stage());
    }

    private Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/datastructure";;
        String user = "root";
        String password = "632004";
        return DriverManager.getConnection(url, user, password);
    }

    private BinarySearchTree retrieveUserStories(String level) {
        BinarySearchTree bst = new BinarySearchTree();
        String query = "SELECT * FROM userstories WHERE level = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, level);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UserStory story = new UserStory(
                        rs.getString("id"),
                        rs.getString("level"),
                        rs.getString("acceptance_criteria"),
                        rs.getString("story")
                );
                bst.insert(story);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bst;
    }

    private int getNextNumberSprint() {
        String query = "SELECT MAX(numberSprint) AS maxSprint FROM sprint";
        int nextNumberSprint = 1;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next() && rs.getInt("maxSprint") > 0) {
                nextNumberSprint = rs.getInt("maxSprint") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextNumberSprint;
    }

    private void insertSprintRows(String id, String level, int num, Set<UserStory> stories) {
        String query = "INSERT INTO sprint (id, level, userStory, acceptance_criteria, numberSprint) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            int numberSprint = getNextNumberSprint();
            int count = 0;

            for (UserStory story : stories) {
                if (count >= num) break;

                pstmt.setString(1, id);
                pstmt.setString(2, level);
                pstmt.setString(3, story.story);
                pstmt.setString(4, story.acceptanceCriteria);
                pstmt.setInt(5, numberSprint++);
                pstmt.addBatch();

                count++;
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void add(ActionEvent event) {
        String idText = id.getText();
        String levelText = lvl.getText();
        int num = Integer.parseInt(numbers.getText());

        BinarySearchTree bst = retrieveUserStories(levelText);
        Set<UserStory> stories = bst.inOrderTraversal();

        insertSprintRows(idText, levelText, num, stories);
    }
}
