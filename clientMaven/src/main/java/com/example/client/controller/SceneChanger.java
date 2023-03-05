package com.example.client.controller;

import com.example.client.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneChanger {
    private static final SceneChanger INSTANCE = new SceneChanger();

    public static SceneChanger getInstance() {
        return INSTANCE;
    }

    private SceneChanger() {
    }


   private Stage stage;

    public void FXML_page(String url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource(url));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent parent = loader.getRoot();
        Scene scene = new Scene(parent);
        stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        //  Parent root = FXMLLoader.load(getClass().getResource(url));

    }


}

