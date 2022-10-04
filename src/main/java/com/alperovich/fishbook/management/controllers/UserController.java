package com.alperovich.fishbook.management.controllers;

import com.alperovich.fishbook.management.DAO.impl.UserDaoImpl;
import com.alperovich.fishbook.management.models.User;
import com.alperovich.fishbook.management.utils.UserUtils;
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

    UserUtils utils = new UserUtils();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        utils.showUsers(columnId, columnFirstName, columnLastName, columnUsername, columnPassword, tableViewUsers);
    }

    public void insertButtonOnAction() {
        utils.insertUser(textFieldFirstName, textFieldLastName, textFieldUsername, textFieldPassword);
        utils.clearForm(textFieldId, textFieldFirstName, textFieldLastName, textFieldUsername, textFieldPassword);
        utils.showUsers(columnId, columnFirstName, columnLastName, columnUsername, columnPassword, tableViewUsers);
    }

    public void updateButtonOnAction() {
        utils.updateUser(textFieldFirstName, textFieldLastName, textFieldUsername, textFieldPassword, textFieldId);
        utils.clearForm(textFieldId, textFieldFirstName, textFieldLastName, textFieldUsername, textFieldPassword);
        utils.showUsers(columnId, columnFirstName, columnLastName, columnUsername, columnPassword, tableViewUsers);
    }

    public void deleteButtonOnAction() {
        utils.deleteUser(textFieldId);
        utils.clearForm(textFieldId, textFieldFirstName, textFieldLastName, textFieldUsername, textFieldPassword);
        utils.showUsers(columnId, columnFirstName, columnLastName, columnUsername, columnPassword, tableViewUsers);
    }

    public void searchUserOnAction() {
        utils.searchUser(textFieldSearching, tableViewUsers);
    }

    public void cancelButtonOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(
                "/com/alperovich/fishbook/management/admin-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleMouseClick() {
        utils.fillForm(tableViewUsers, textFieldId, textFieldFirstName,
                textFieldLastName, textFieldUsername, textFieldPassword);
    }
}
