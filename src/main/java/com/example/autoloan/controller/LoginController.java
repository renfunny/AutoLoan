package com.example.autoloan.controller;

import com.example.autoloan.model.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordPasswordField;
    @FXML private Button loginBtn;

    private Login loginModel;

    @FXML
    public void initialize(){
        loginModel = new Login();
        loginBtn.setOnAction(event -> handleLogin());
    }

    @FXML
    public void handleLogin(){
        usernameTextField.setBorder(null);

        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();

        loginModel.setUsername(username);
        loginModel.setPassword(password);

        if(loginModel.validate()){
            navigateToMenu();
        } else {
            highlightUsernameTextField();
        }
    }

    private void navigateToMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/autoloan/carloan-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) loginBtn.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void highlightUsernameTextField(){
        BorderStroke borderStroke = new BorderStroke(
                Color.RED,
                BorderStrokeStyle.SOLID,
                new CornerRadii(3),
                new javafx.scene.layout.BorderWidths(2)
        );
        usernameTextField.setBorder(new Border(borderStroke));
    }
}