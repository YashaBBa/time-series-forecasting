

package com.example.client.controller;

import com.example.client.hendler.SessionHandler;
import com.example.client.model.Result;
import com.example.client.model.User;
import com.example.client.model.enums.ControllerEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.List;

public class HistoryController {


    public Button backButton;
    public TextField addressPlace;
    public TableColumn<Result, Date> date1;
    @FXML
    private TableColumn<Result, String> date;

    @FXML
    private Button exit;

    @FXML
    private TableView<Result> tableObject;

    @FXML
    private Button testFileButton;

    @FXML
    private TableColumn<Result, Integer> value;

    @FXML
    void exitButton(ActionEvent event) {
        SessionHandler.roleID = 0;
        exit.getScene().getWindow().hide();
        SceneChanger sceneChanger = SceneChanger.getInstance();
        sceneChanger.FXML_page("hello-view.fxml");
    }

    @FXML
    void opentTestFile(ActionEvent event) {
        Desktop desktop = null;
        if (addressPlace.getText().equals(""))
            return;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }

        try {
            desktop.open(new File(addressPlace.getText()));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    ObservableList<Result> observableList = FXCollections.observableArrayList();
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    @FXML
    void initialize() {
        date.setCellValueFactory(new PropertyValueFactory<Result, String>("result"));
        value.setCellValueFactory(new PropertyValueFactory<Result, Integer>("timestap"));
        date1.setCellValueFactory(new PropertyValueFactory<Result,Date>("date"));

        SessionHandler sessionHandler = SessionHandler.getInstance();
        if (sessionHandler.getSocket().isConnected()) {

            try {
                inputStream = sessionHandler.getInputStream();
                outputStream = sessionHandler.getOutputStream();

                outputStream.writeObject(ControllerEnum.GETRESULTS);
                User user = new User();
                user.setId(SessionHandler.userId);
                outputStream.writeObject(user);
                List<Result> resultList = (List<Result>) inputStream.readObject();
                observableList = FXCollections.observableArrayList(resultList);


            } catch (IOException e) {

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        tableObject.setItems(observableList);


    }

    public void onBackButton(ActionEvent actionEvent) {
        exit.getScene().getWindow().hide();
        SceneChanger sceneChanger = SceneChanger.getInstance();
        sceneChanger.FXML_page("prognos-page.fxml");
    }
}

