package com.alperovich.fishbook.management.utils;

import com.alperovich.fishbook.management.DAO.impl.UserDaoImpl;
import com.alperovich.fishbook.management.models.User;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserUtils {

    static UserDaoImpl udao = new UserDaoImpl();

    public static User insertUser(TextField firstName, TextField lastName, TextField userName, TextField password) {
        User user = new User();
        user.setFirstName(firstName.getText());
        user.setLastName(lastName.getText());
        user.setUserName(userName.getText());
        user.setPassword(password.getText());
        udao.createUser(user);
        return user;
    }

    public static User updateUser(TextField firstName, TextField lastName, TextField userName, TextField password, TextField id) {
        User user = new User();
        user.setFirstName(firstName.getText());
        user.setLastName(lastName.getText());
        user.setUserName(userName.getText());
        user.setPassword(password.getText());
        user.setId(Integer.parseInt(id.getText()));
        udao.updateUser(user);
        return user;
    }

    public void deleteUser(TextField id) {
        ;
        udao.deleteUserById(Integer.parseInt(id.getText()));
    }

    public void searchUser(TextField searching, TableView<User> tv) {
        FilteredList<User> filteredList = new FilteredList<>(udao.getAllUsers(), b -> true);
        searching.textProperty().addListener((observable, oldValue, newValue) -> {
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
        sortedList.comparatorProperty().bind(tv.comparatorProperty());
        tv.setItems(sortedList);
    }

    public void showUsers(TableColumn<User, Integer> columnId,
                          TableColumn<User, String> columnFirstName,
                          TableColumn<User, String> columnLastName,
                          TableColumn<User, String> columnUsername,
                          TableColumn<User, String> columnPassword,
                          TableView<User> tv) {
        ObservableList<User> userList = udao.getAllUsers();
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));
        columnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        tv.setItems(userList);
    }

    public void fillForm(TableView<User> tv,
                         TextField textFieldId,
                         TextField textFieldFirstName,
                         TextField textFieldLastName,
                         TextField textFieldUsername,
                         TextField textFieldPassword) {
        User user = tv.getSelectionModel().getSelectedItem();
        if (user != null) {
            textFieldId.setText(Integer.toString(user.getId()));
            textFieldFirstName.setText(user.getFirstName());
            textFieldLastName.setText(user.getLastName());
            textFieldUsername.setText(user.getUserName());
            textFieldPassword.setText(user.getPassword());
        }
    }

    public void clearForm(TextField textFieldId,
                          TextField textFieldFirstName,
                          TextField textFieldLastName,
                          TextField textFieldUsername,
                          TextField textFieldPassword) {
        textFieldId.setText("");
        textFieldFirstName.setText("");
        textFieldLastName.setText("");
        textFieldUsername.setText("");
        textFieldPassword.setText("");
        textFieldId.requestFocus();
    }

}
