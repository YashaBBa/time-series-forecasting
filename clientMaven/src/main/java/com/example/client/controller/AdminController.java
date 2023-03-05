package com.example.client.controller;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.example.client.HelloApplication;
import com.example.client.hendler.SessionHandler;
import com.example.client.model.Roles;
import com.example.client.model.User;
import com.example.client.model.enums.ControllerEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminController {


    public Button exit;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button DeleteButton;

    @FXML
    private ComboBox<String> RoleCheckBox;

    @FXML
    private Button UpdateButton;

    @FXML
    private ComboBox<String> faceComboBox;

    @FXML
    private RadioButton id;

    @FXML
    private RadioButton log;

    @FXML
    private TextField login;

    @FXML
    private TextField login1;

    @FXML
    private TextField name;

    @FXML
    private Button regButton;

    @FXML
    private TextArea resultTextArea;

    @FXML
    private Button selectButton;

    @FXML
    private TextField surname;
    @FXML
    private Label errorField;

    @FXML
    private Button selectAll;

    @FXML
    private TableColumn<User, String> surnameTable;

    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> roleTable;

    @FXML
    private TableColumn<User, String> nameTable;


    @FXML
    private TableColumn<User, Integer> idTable;
    @FXML
    private TableColumn<User, String> faceTable;
    @FXML
    private TableColumn<User, String> loginTable;

    @FXML
    private ComboBox<String> sortByBox;

    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;


    @FXML
    void delete(ActionEvent event) {
        SessionHandler sessionHandler = SessionHandler.getInstance();
        if (sessionHandler.getSocket().isConnected()) {
            try {
                inputStream = sessionHandler.getInputStream();
                outputStream = sessionHandler.getOutputStream();
                String request = "";
                User user = new User();
                if (id.isSelected()) {
                    try {
                        if (Integer.parseInt(login.getText()) < 0) {
                            errorField.setText("Невернвый id");
                        }
                    } catch (NumberFormatException e) {
                        errorField.setText("Неверный формат");
                        return;
                    }


                    //  request = "deleteById " + login.getText();
                    outputStream.writeObject(ControllerEnum.DELETEBYID);
                    user.setId(Integer.parseInt(login.getText()));
                } else {
                    outputStream.writeObject(ControllerEnum.DELETEBYLOGIN);
                    user.setLogin(login.getText());
                    //request = "deleteByLogin " + login.getText();
                }


                outputStream.writeObject(user);
                // byte[] bytesRequest = request.getBytes(StandardCharsets.UTF_8);


                Boolean result = (Boolean) inputStream.readObject();

                if (result.equals(false)) {
                    errorField.setText("Такого пользователя не существует");
                    return;
                }else {
                    errorField.setText("");
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
    void find(ActionEvent event) {
        SessionHandler sessionHandler = SessionHandler.getInstance();
        if (sessionHandler.getSocket().isConnected()) {
            try {
                inputStream = sessionHandler.getInputStream();
                outputStream = sessionHandler.getOutputStream();
                String request = "";
                User user = new User();
                if (id.isSelected()) {
                    try {
                        if (Integer.parseInt(login.getText()) < 0) {
                            errorField.setText("Невернвый id");
                        }
                    } catch (NumberFormatException e) {
                        errorField.setText("Неверный формат");
                        return;
                    }


                    outputStream.writeObject(ControllerEnum.FINDBYID);
                    user.setId(Integer.parseInt(login.getText()));
                } else {

                    outputStream.writeObject(ControllerEnum.FINDBYLOGIN);
                    user.setLogin(login.getText());
                }
                outputStream.writeObject(user);


                user = (User) inputStream.readObject();


                try {
                    login1.setText(user.getLogin());
                    name.setText(user.getName());
                    surname.setText(user.getSurname());
                    faceComboBox.setPromptText(user.getFace());
                    RoleCheckBox.setPromptText(user.getRole());
                } catch (NullPointerException e) {
                    errorField.setText("Такого пользователя не существует");
                    return;
                }


            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    ObservableList<User> observableList = FXCollections.observableArrayList();
    ObservableList<User> observableSortedList = FXCollections.observableArrayList();


    @FXML
    void findAll(ActionEvent event) {
        SessionHandler sessionHandler = SessionHandler.getInstance();
        if (sessionHandler.getSocket().isConnected()) {
            try {
                inputStream = sessionHandler.getInputStream();
                outputStream = sessionHandler.getOutputStream();


                outputStream.writeObject(ControllerEnum.FINDALL);

                List<User> list = (List<User>) inputStream.readObject();


                observableSortedList.clear();
                observableList.clear();

                for (User user1 : list) {
                    User user = new User();
                    user.setId(user1.getId());
                    user.setLogin(user1.getLogin());
                    user.setName(user1.getName());
                    user.setSurname(user1.getSurname());

                    user.setRole(user1.getRole());
                    user.setFace(user1.getFace());

                    observableList.add(user);
                    observableSortedList.add(user);

                }

                table.setItems(observableList);


            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    String sortParam;


    @FXML
    void getCRUDParam(ActionEvent event) {
        if (id.isSelected()) {
            sortParam = "id";
        } else {
            sortParam = "login";
        }
    }

    @FXML
    void selectFace(ActionEvent event) {

    }

    @FXML
    void selectRole(ActionEvent event) {

    }

    @FXML
    void sortBy(ActionEvent event) {


    }

    @FXML
    void sortByRole(ActionEvent event) {
        observableSortedList = FXCollections.observableArrayList(observableList);
        FilteredList<User> filteredList = new FilteredList<>(observableList, x -> x.getRole().equals(sortByBox.getValue().toString()));
        table.setItems(filteredList);
    }

    @FXML
    void update(ActionEvent event) throws IOException {
        SessionHandler sessionHandler = SessionHandler.getInstance();
        if (sessionHandler.getSocket().isConnected()) {
            try {
                inputStream = sessionHandler.getInputStream();
                outputStream = sessionHandler.getOutputStream();

                String role = RoleCheckBox.getSelectionModel().getSelectedItem().toString();

                outputStream.writeObject(ControllerEnum.UPDATEUSERROLE);
                User user = new User();
                user.setLogin(login1.getText());
                user.setId(Roles.map.get(role));
                outputStream.writeObject(user);
                Boolean result = (Boolean) inputStream.readObject();


                if (result.equals(true)) {
                    errorField.setText("");
                } else {
                    errorField.setText("Что-то пошло не по плану");
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
    void initialize() {
        ObservableList<String> faceList = FXCollections.observableArrayList("Phis", "Ur");
        faceComboBox.setItems(faceList);
        ObservableList<String> roleList = FXCollections.observableArrayList("USER", "ADMIN");
        RoleCheckBox.setItems(roleList);
        ObservableList<String> sortByList = FXCollections.observableArrayList("USER", "ADMIN");
        sortByBox.setItems(sortByList);


        nameTable.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        surnameTable.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        faceTable.setCellValueFactory(new PropertyValueFactory<User, String>("face"));
        roleTable.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
        idTable.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        loginTable.setCellValueFactory(new PropertyValueFactory<User, String>("login"));


        //  tableObject.setItems(list);


    }

    public void exitButton(ActionEvent actionEvent) {
        SessionHandler.roleID = 0;
        exit.getScene().getWindow().hide();
        SceneChanger sceneChanger = SceneChanger.getInstance();
        sceneChanger.FXML_page("hello-view.fxml");
    }
}
