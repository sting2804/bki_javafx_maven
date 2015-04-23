package com.privat.bki.desktopapp.gui.login

import com.privat.bki.desktopapp.gui.main.MainModel
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.stage.Stage

import java.net.URL
import java.util.ResourceBundle

/**
 * Created by sting on 4/19/15.
 */
class LoginController implements Initializable {
    @FXML
    Button applyButton
    TextField usernameTextField
    TextField passwordTextField

    private boolean isAuthenticated

    private MainModel mainModel

    LoginController() {
    }

    void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel
    }

    @Override
    void initialize(URL location, ResourceBundle resources) {
        isAuthenticated = false
    }

    void setScreenForms(String code) {
        if (!code.equals("")) {
            usernameTextField.setText(code)
        }
    }

    void applyOnClick(ActionEvent actionEvent) {
        String username, password
        username = usernameTextField.getText()
        password = passwordTextField.getText()
        if (username != null && !username.equals("") &&
                password != null && !password.equals("")) {
            //isAuthenticated = mainModel.authenticateUser(username, password)
            isAuthenticated = true
            if (isAuthenticated) {
                Stage stage = (Stage) usernameTextField.getScene().getWindow()
                stage.close()
            }
        } else {
            showErrorAuthenticationMessage()
        }
    }

    private void showErrorAuthenticationMessage() {
        new Alert(Alert.AlertType.WARNING, "Неверно задан логин или пароль").showAndWait()
    }

}
