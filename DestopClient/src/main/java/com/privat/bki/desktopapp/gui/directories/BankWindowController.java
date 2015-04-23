package com.privat.bki.desktopapp.gui.directories;


import com.privat.bki.desktopapp.gui.main.MainModel;
import com.privat.bki.entities.Bank;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author sting
 */
public class BankWindowController implements Initializable {
    @FXML
    public Button applyButton;
    public TextField bankCodeTextField;
    public TextField bankNameTextField;


    private Bank bank;

    private MainModel mainModel;

    public BankWindowController() {
    }

    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bank = new Bank();
    }

    public void setScreenForms(String code) {
        if (!code.equals("")) {
            bankCodeTextField.setText(code);
        }
    }

    public void applyOnClick(ActionEvent actionEvent) {
        String code, name;
        code = bankCodeTextField.getText();
        name = bankNameTextField.getText();
        if (!code.equals("") &&
                !name.equals("")){
            bank.setCode(code);
            bank.setName(name);
        }
        else
            bank = null;
        Stage stage = (Stage) bankNameTextField.getScene().getWindow();
        stage.close();
    }

    public Bank getBank() {
        return bank;
    }
}
