package com.privat.bki.desktopapp.gui.directories
import com.privat.bki.business.entities.Bank
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
class BankWindowController implements Initializable {
    @FXML
    Button applyButton
    TextField bankCodeTextField
    TextField bankNameTextField


    private Bank bank

    private MainModel mainModel

    BankWindowController() {
    }

    void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel
    }

    @Override
    void initialize(URL location, ResourceBundle resources) {
        bank = new Bank()
    }

    void setScreenForms(String code) {
        if (!code.equals("")) {
            bankCodeTextField.setText(code)
        }
    }

    void applyOnClick(ActionEvent actionEvent) {
        String code, name
        code = bankCodeTextField.getText()
        name = bankNameTextField.getText()
        if (!code.equals("") &&
                !name.equals("")){
            bank.setCode(code)
            bank.setName(name)
        }
        else
            bank = null
        Stage stage = (Stage) bankNameTextField.getScene().getWindow()
        stage.close()
    }

    Bank getBank() {
        return bank
    }
}

