package com.privat.bki.desktopapp.gui.directories;
import com.privat.bki.business.entities.Currency;
import com.privat.bki.desktopapp.gui.main.MainModel;
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
public class CurrencyWindowController implements Initializable {
    @FXML
    public Button applyButton;
    public TextField currencyCodeTextField;
    public TextField currencyNameTextField;


    public Currency getCurrency() {
        return currency;
    }

    private Currency currency;

    private MainModel mainModel;

    public CurrencyWindowController() {
    }

    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currency = new Currency();
    }

    public void setScreenForms(String code) {
        if (!code.equals("")) {
            currencyCodeTextField.setText(code);
        }
    }

    public void applyOnClick(ActionEvent actionEvent) {
        String code, name;
        code = currencyCodeTextField.getText();
        name = currencyNameTextField.getText();
        if (!code.equals("") &&
                !name.equals("")){
            currency.setCode(code);
            currency.setName(name);
        }
        else
            currency = null;
        Stage stage = (Stage) currencyCodeTextField.getScene().getWindow();
        stage.close();
    }
}

