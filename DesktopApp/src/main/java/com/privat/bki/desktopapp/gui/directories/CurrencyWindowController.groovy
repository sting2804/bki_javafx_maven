package com.privat.bki.desktopapp.gui.directories
import com.privat.bki.business.entities.Currency
import com.privat.bki.desktopapp.gui.main.MainModel
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.stage.Stage
/**
 * @author sting
 */
class CurrencyWindowController implements Initializable {
    @FXML
    Button applyButton
    TextField currencyCodeTextField
    TextField currencyNameTextField


    Currency getCurrency() {
        return currency
    }

    private Currency currency

    private MainModel mainModel

    CurrencyWindowController() {
    }

    void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel
    }



    @Override
    void initialize(URL location, ResourceBundle resources) {
        currency = new Currency()
    }

    void setScreenForms(String code) {
        if (!code.equals("")) {
            currencyCodeTextField.setText(code)
        }
    }

    void applyOnClick(ActionEvent actionEvent) {
        String code, name
        code = currencyCodeTextField.getText()
        name = currencyNameTextField.getText()
        if (!code.equals("") &&
                !name.equals("")){
            currency.setCode(code)
            currency.setName(name)
        }
        else
            currency = null
        Stage stage = (Stage) currencyCodeTextField.getScene().getWindow()
        stage.close()
    }
}

