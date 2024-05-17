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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class developerController {

    @FXML
    private Label Nextbtn, exitBtn;

    @FXML
    private TextField id, del;

    @FXML
    private TextArea list;

    String s[]=new String[100];
    int c=1;

    public void ExitBtnClick1(MouseEvent event) {
        Platform.exit();
    }

    public void Nextbtn(MouseEvent event) throws IOException, SQLException {

        login next = new login();
        next.start(new Stage());
    }
    public void done(ActionEvent event) throws SQLException {
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/datastructure","root","632004");
        Statement st=con.createStatement();
        st.executeUpdate("delete from sprint where Id='"+id.getText()+"'");
    }

    public void del(ActionEvent event) throws SQLException {
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/datastructure","root","632004");
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select * from sprint where Id='"+id.getText()+"'");
        while(rs.next())
        {
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                int sprintId = rs.getInt("numberSprint");
                String sprintName = rs.getString("userStory");
                String criteria = rs.getString("acceptance_criteria");
                sb.append(sprintId).append(" User Story: ").append(sprintName).append(", Acceptance Criteria: ").append(criteria).append("\n");
            }
            list.setText(sb.toString());
        }
    }

    public void remove(ActionEvent event) throws SQLException {
        // Establish the database connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/datastructure", "root", "632004");
             Statement st = con.createStatement()) {

            // Retrieve the user story associated with the given sprint number
            ResultSet rs = st.executeQuery("SELECT userStory FROM sprint WHERE numberSprint='" + Integer.parseInt(del.getText()) + "'");

            String userStory = null;
            if (rs.next()) {  // Move cursor to the first row
                userStory = rs.getString("userStory");
            }

            // Close the ResultSet
            rs.close();

            if (userStory != null) {
                // Delete the sprint with the given numberSprint
                st.executeUpdate("DELETE FROM sprint WHERE numberSprint='" + Integer.parseInt(del.getText()) + "'");

                // Delete the user story associated with the sprint
                st.executeUpdate("DELETE FROM userstories WHERE story='" + userStory + "'");

                // Output the user story for confirmation
                System.out.println(userStory);
            } else {
                // Output a message if no user story was found
                System.out.println("No user story found for the given sprint number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, handle the exception in a user-friendly manner, e.g., showing an error message in the UI
        }
    }



}