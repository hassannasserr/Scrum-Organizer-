module com.example.datastrucure {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.datastrucure to javafx.fxml;
    exports com.example.datastrucure;
}