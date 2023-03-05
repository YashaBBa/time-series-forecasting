module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.client to javafx.fxml;
    opens com.example.client.model to javafx.fxml, javafx.base;


    opens com.example.client.controller to javafx.fxml;
    exports com.example.client;
    
}