package com.alperovich.fishbook.management.controllers;

import com.alperovich.fishbook.management.DB.DBConnector;
import com.alperovich.fishbook.management.utils.LoginCounter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SignUpController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMassageLabel;


    public void loginButtonOnAction(ActionEvent e) {
        if (!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank()) {
            String username = usernameTextField.getText();
            String sql = "SELECT COUNT(1) FROM fishbooking_db.users WHERE userName = '" + usernameTextField.getText() + "' AND password = '" + passwordPasswordField.getText() + "'";
            try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    if (rs.getInt(1) == 1) {
                        Stage stage;
                        Scene scene;
                        if (username.equals("Admin")) {
                            WelcomeScreen();
                            FXMLLoader adLoader = new FXMLLoader(getClass().getResource("/com/alperovich/fishbook/management/admin-view.fxml"));
                            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                            scene = new Scene(adLoader.load());
                        } else {
                            WelcomeScreen();
                            FXMLLoader manLoader = new FXMLLoader(getClass().getResource("/com/alperovich/fishbook/management/manager-view.fxml"));
                            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                            scene = new Scene(manLoader.load());
                        }
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        loginMassageLabel.setText("Invalid Login or Password. Try Again!");
                    }
                }
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
        } else {
            loginMassageLabel.setText("Fields are empty! Please input data!");
        }
    }

    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void WelcomeScreen() {
        Thread welcomeScreen = new LoginCounter();
        welcomeScreen.setName("Welcome screen");
        welcomeScreen.start();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Welcome");
        alert.setHeaderText("Hello, " + usernameTextField.getText());
        alert.setContentText("Fishbooking By Zakhar Alperovich");
        alert.showAndWait();

    }
}