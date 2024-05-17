package com.example.datastrucure;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class linkedListcall {

    @FXML
    private Label Nextbtn, exitBtn;

    @FXML
    private TextField id, del;

    @FXML
    private TextArea list;

    private LinkedList listData;

    public void ExitBtnClick1(MouseEvent event) {
        Platform.exit();
    }

    public void Nextbtn(MouseEvent event) throws IOException {
        login next = new login();
        next.start(new Stage());
    }

    public void del(ActionEvent event) {
        String idValue = id.getText();
        if (idValue.isEmpty()) {
            // Handle empty id field
            list.setText("ID field cannot be empty.");
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/datastructure", "root", "632004");
             Statement statement = connection.createStatement()) {

            String query = "SELECT id, level, userStory, acceptance_criteria FROM sprint WHERE Id = '" + idValue + "'";
            ResultSet resultSet = statement.executeQuery(query);

            listData = new LinkedList();
            int count = 1;
            while (resultSet.next()) {
                // Get the sprint data from the result set
                int dbId = resultSet.getInt("id");
                String level = resultSet.getString("level");
                String userStory = resultSet.getString("userStory");
                String acceptanceCriteria = resultSet.getString("acceptance_criteria");

                // Format and add the data to the linked list
                String rowData = String.format("%d. Level: %s, User Story: %s, Acceptance Criteria: %s%n", count, level, userStory, acceptanceCriteria);
                listData.add(rowData, count, dbId);
                count++;
            }

            // Display the linked list data in the TextArea
            list.setText(listData.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromList(ActionEvent event) {
        String delValue = del.getText();
        if (delValue.isEmpty()) {
            // Handle empty del field
            list.setText("Delete field cannot be empty.");
            return;
        }

        try {
            int indexToDelete = Integer.parseInt(delValue);
            int dbId = listData.delete(indexToDelete);

            if (dbId != -1) {
                deleteFromDatabase(dbId);
            }

            // Refresh the TextArea with updated list data
            list.setText(listData.toString());
        } catch (NumberFormatException e) {
            list.setText("Please enter a valid number.");
        }
    }

    private void deleteFromDatabase(int dbId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/datastructure", "root", "632004");
             Statement statement = connection.createStatement()) {

            String query = "DELETE FROM sprint WHERE id = " + dbId;
            statement.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllRows(ActionEvent event) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/datastructure", "root", "632004");
             Statement statement = connection.createStatement()) {

            String query = "DELETE FROM sprint";
            statement.executeUpdate(query);

            // Clear the linked list and the TextArea
            if (listData != null) {
                listData.clear();
            }
            list.setText("");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Linked list implementation
    private static class Node {
        String data;
        int originalNumber;
        int dbId;
        Node next;

        Node(String data, int originalNumber, int dbId) {
            this.data = data;
            this.originalNumber = originalNumber;
            this.dbId = dbId;
            this.next = null;
        }
    }

    private static class LinkedList {
        private Node head;

        LinkedList() {
            this.head = null;
        }

        void add(String data, int originalNumber, int dbId) {
            Node newNode = new Node(data, originalNumber, dbId);
            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
        }

        int delete(int originalNumber) {
            if (head == null) return -1;

            if (head.originalNumber == originalNumber) {
                int dbId = head.dbId;
                head = head.next;
                return dbId;
            }

            Node current = head;
            while (current.next != null && current.next.originalNumber != originalNumber) {
                current = current.next;
            }

            if (current.next != null) {
                int dbId = current.next.dbId;
                current.next = current.next.next;
                return dbId;
            }

            return -1;
        }

        void clear() {
            head = null;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node current = head;
            while (current != null) {
                sb.append(current.data);
                current = current.next;
            }
            return sb.toString();
        }
    }
}
