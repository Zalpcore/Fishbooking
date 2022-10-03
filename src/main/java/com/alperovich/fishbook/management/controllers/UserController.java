package com.alperovich.fishbook.management.controllers;

import com.alperovich.fishbook.management.DAO.impl.UserDaoImpl;
import com.alperovich.fishbook.management.models.User;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    public TextField textFieldSearching;
    @FXML
    private TextField textFieldId;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private TableView<User> tableViewUsers;
    @FXML
    private TableColumn<User, Integer> columnId;
    @FXML
    private TableColumn<User, String> columnFirstName;
    @FXML
    private TableColumn<User, String> columnLastName;
    @FXML
    private TableColumn<User, String> columnUsername;
    @FXML
    private TableColumn<User, String> columnPassword;
    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;

    private Stage stage;
    private Scene scene;
    private Parent root;
    UserDaoImpl udao = new UserDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showUsers();
    }

    public void insertButtonOnAction() {
        User user = new User();
        user.setFirstName(textFieldFirstName.getText());
        user.setLastName(textFieldLastName.getText());
        user.setUserName(textFieldUsername.getText());
        user.setPassword(textFieldPassword.getText());
        udao.createUser(user);
        clearUserForm();
        showUsers();
    }

    public void updateButtonOnAction() {
        User user = new User();
        user.setFirstName(textFieldFirstName.getText());
        user.setLastName(textFieldLastName.getText());
        user.setUserName(textFieldUsername.getText());
        user.setPassword(textFieldPassword.getText());
        user.setId(Integer.parseInt(textFieldId.getText()));
        udao.updateUser(user);
        clearUserForm();
        showUsers();
    }

    public void deleteButtonOnAction() {
        int id = Integer.parseInt(textFieldId.getText());
        udao.deleteUserById(id);
        clearUserForm();
        showUsers();
    }

    public void searchUserOnAction() {
        FilteredList<User> filteredList = new FilteredList<>(udao.getAllUsers(), b -> true);
        textFieldSearching.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(User -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                if (User.getFirstName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (User.getLastName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (User.getUserName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (User.getPassword().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<User> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableViewUsers.comparatorProperty());
        tableViewUsers.setItems(sortedList);
    }

    public void showUsers() {
        ObservableList<User> userList = udao.getAllUsers();
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));
        columnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        tableViewUsers.setItems(userList);
    }

    public void cancelButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/alperovich/fishbook/management/admin-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleMouseClick() {
        User user = tableViewUsers.getSelectionModel().getSelectedItem();
        if (user != null) {
            textFieldId.setText(Integer.toString(user.getId()));
            textFieldFirstName.setText(user.getFirstName());
            textFieldLastName.setText(user.getLastName());
            textFieldUsername.setText(user.getUserName());
            textFieldPassword.setText(user.getPassword());
        }
    }

    public void clearUserForm() {
        textFieldId.setText("");
        textFieldFirstName.setText("");
        textFieldLastName.setText("");
        textFieldUsername.setText("");
        textFieldPassword.setText("");
        textFieldId.requestFocus();
    }
}
