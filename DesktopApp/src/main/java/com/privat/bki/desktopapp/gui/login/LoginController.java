package com.privat.bki.desktopapp.gui.login;

import com.privat.bki.desktopapp.gui.main.MainModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public Button applyButton;
    public TextField usernameTextField;
    public TextField passwordTextField;

    private boolean isAuthenticated;

    private MainModel mainModel;

    public LoginController() {
    }

    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isAuthenticated = false;
    }

    public void setScreenForms(String code) {
        if (!code.equals("")) {
            usernameTextField.setText(code);
        }
    }

    public void applyOnClick(ActionEvent actionEvent) {
        String username, password;
        username = usernameTextField.getText();
        password = passwordTextField.getText();
        if (username != null && !username.equals("") &&
                password != null && !password.equals("")) {
            isAuthenticated = mainModel.authenticateUser(username, password);
            //isAuthenticated = true;
            if (isAuthenticated) {
                Stage stage = (Stage) usernameTextField.getScene().getWindow();
                stage.close();
            }
        } else {
            showErrorAuthenticationMessage();
        }
    }

    private void showErrorAuthenticationMessage() {
        new Alert(Alert.AlertType.WARNING, "Неверно задан логин или пароль").showAndWait();
    }

}
