package com.example.client.controller;

import com.example.client.HelloApplication;
import com.example.client.hendler.SessionHandler;
import com.example.client.model.User;
import com.example.client.model.enums.ControllerEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginTextPlace;

    @FXML
    private PasswordField passwordTextPlace;

    @FXML
    private Button registgrationButton;

    @FXML
    private Button singInButton;

    @FXML
    private Label erField;

    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;


    private Stage stage;

    Scene scene;

    @FXML
    void doEntryButton(ActionEvent event) {
        SessionHandler sessionHandler = SessionHandler.getInstance();
        if (sessionHandler.getSocket().isConnected()) {
            try {

                inputStream = sessionHandler.getInputStream();
                outputStream = sessionHandler.getOutputStream();


                User user = new User();
                user.setLogin(loginTextPlace.getText());
                user.setPassword(passwordTextPlace.getText());
                outputStream.writeObject(ControllerEnum.LOGINATION);
                outputStream.writeObject(user);


                User result = (User) inputStream.readObject();


                try {
                    SessionHandler.roleID = Integer.parseInt(result.getRole());
                    SessionHandler.userId = result.getId();
                } catch (NumberFormatException e) {
                    erField.setText("Данные введены неверно");
                    return;
                }

                if (SessionHandler.roleID == 1 || SessionHandler.roleID == 3) {


                    singInButton.getScene().getWindow().hide();
                    SceneChanger sceneChanger = SceneChanger.getInstance();
                    sceneChanger.FXML_page("main-page.fxml");

                } else {
                    singInButton.getScene().getWindow().hide();
                    SceneChanger sceneChanger = SceneChanger.getInstance();

                    sceneChanger.FXML_page("prognos-page.fxml");
                }


            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @FXML
    void onRegButton(ActionEvent event) throws IOException {
        registgrationButton.getScene().getWindow().hide();
        SceneChanger sceneChanger = SceneChanger.getInstance();
        sceneChanger.FXML_page("registration.fxml");


    }

    @FXML
    void initialize() {

    }

}
