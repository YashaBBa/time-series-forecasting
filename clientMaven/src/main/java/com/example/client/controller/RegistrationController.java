package com.example.client.controller;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import com.example.client.HelloApplication;
import com.example.client.hendler.SessionHandler;
import com.example.client.model.User;
import com.example.client.model.enums.ControllerEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField face;

    @FXML
    private TextField login;

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private Button regButton;

    @FXML
    private TextField repPassword;

    @FXML
    private TextField surname;

    @FXML
    private Label erField;



    @FXML
    void reg(ActionEvent event) {
        SessionHandler sessionHandler = SessionHandler.getInstance();
        if (sessionHandler.getSocket().isConnected()) {
            if (password.getText().equals(repPassword.getText())) {
                try {
                    inputStream = sessionHandler.getInputStream();
                    outputStream = sessionHandler.getOutputStream();

                    outputStream.writeObject(ControllerEnum.REGISTRATION);
                    User user = new User();
                    user.setLogin(login.getText());
                    user.setPassword(password.getText());
                    user.setName(name.getText());
                    user.setSurname(surname.getText());
                    user.setFace(face.getText());
                    outputStream.writeObject(user);
                    Boolean result = (Boolean) inputStream.readObject();

                    if (result.equals(true)) {
                        regButton.getScene().getWindow().hide();
                        SceneChanger sceneChanger = SceneChanger.getInstance();
                        sceneChanger.FXML_page("hello-view.fxml");
                    } else {
                        erField.setText("Пользователь с таким логином уже существует");
                    }


                } catch (IOException e) {

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                erField.setText("Пароли не совпадают");

            }
        }
    }

    @FXML
    void onRegButton(ActionEvent event) {

    }

    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    @FXML
    void initialize() {


    }

}

