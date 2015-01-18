
package desktopclient.gui.changeclient;


import desktopclient.entities.ISearchable;
import desktopclient.entities.LoanInfo;
import desktopclient.entities.Person;
import desktopclient.gui.IMyController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.imageio.metadata.IIOMetadataController;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * @author sting
 */
public class ChangeInfoWindowController implements  Initializable, IMyController {
    @FXML
    public Button applyButton;
    @FXML
    TextField fioTextField;
    @FXML
    TextField passportTextField;
    @FXML
    TextField innTextField;
    @FXML
    TextField initialAmountTextField;
    @FXML
    TextField balanceTextField;
    @FXML
    ComboBox<String> currencyComboBox;
    @FXML
    DatePicker initdateDatePicker;
    @FXML
    DatePicker finishdateDatePicker;
    @FXML
    DatePicker birthdayDatePicker;
    @FXML
    ComboBox<String> bankComboBox;
    @FXML
    CheckBox arrearsCheckBox;
    private Person person;
    private LoanInfo searchInfo;

    public ChangeInfoWindowController(){
    }

    @FXML
    public void changeInfoOnClick(ActionEvent event) {
        String [] fio = fioTextField.getText().split("\\s+");
        String [] passport = passportTextField.getText().split("\\s+");
        String inn = innTextField.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        LocalDate birthday = birthdayDatePicker.getValue();
        Double initAmount, balance;
        try {
            initAmount = Double.parseDouble(initialAmountTextField.getText());
            balance = Double.parseDouble(balanceTextField.getText());
        }catch (NumberFormatException e){
            initAmount=null;
            balance=null;
        }
        String currency = currencyComboBox.getValue();
        LocalDate initDate = initdateDatePicker.getValue();
        LocalDate finishDate = finishdateDatePicker.getValue();
        String bank = bankComboBox.getValue();
        Boolean arrears = arrearsCheckBox.isSelected();
        if (fio[0].equals("") || passport[0].equals("") || inn.equals("") || birthday==null ||
                initAmount==null || balance==null || currency==null ||
                initDate==null || finishDate==null || bank==null){
            alert.setContentText("Заполните все поля");
            alert.showAndWait();
        }
        else{
            person = new Person();
            searchInfo = new LoanInfo();
            person.setSurname(fio[0]);
            person.setName(fio[1]);
            if (fio.length == 3)
                person.setPatronymic(fio[2]);
            person.setBirthday(birthday);
            person.setPassNumber(passport[1]);
            person.setPassSerial(passport[0]);
            person.setInn(inn);
            searchInfo.setPerson(person);
            searchInfo.setCurrency(currency);
            searchInfo.setBank(bank);
            searchInfo.setArrears(arrears);
            searchInfo.setBalance(balance);
            searchInfo.setFinishDate(finishDate);
            searchInfo.setInitDate(initDate);
            searchInfo.setInitAmount(initAmount);
            Stage curStage = (Stage) (applyButton.getScene().getWindow());
            curStage.close();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //будут тянуться из базы
        ObservableList<String> curitms = FXCollections.observableArrayList();
        curitms.add("UAH");
        curitms.add("USD");
        curitms.add("RUB");
        currencyComboBox.setItems(curitms);
        ObservableList<String> bitms = FXCollections.observableArrayList();
        bitms.add("privat");
        bitms.add("aval");
        bitms.add("uksib");
        bankComboBox.setItems(bitms);
        setDatePickerFormat(birthdayDatePicker);
        setDatePickerFormat(initdateDatePicker);
        setDatePickerFormat(finishdateDatePicker);
    }

    private void setDatePickerFormat(DatePicker datePicker) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            public String toString(LocalDate object) {
                if (object == null)
                    return "";
                return dateTimeFormatter.format(object);
            }

            @Override
            public LocalDate fromString(String string) {
                if (string == null)
                    return null;
                return LocalDate.parse(string);
            }
        });
    }


    private void setScreenForms(LoanInfo loanInfo) {
        if (loanInfo != null) {
            setScreenForms(loanInfo.getPerson());
            initialAmountTextField.setText(loanInfo.getInitAmount().toString());
            balanceTextField.setText(loanInfo.getBalance().toString());
            ObservableList<String> currencyList = currencyComboBox.getItems();
            ObservableList<String> bankList = bankComboBox.getItems();
            Iterator it = currencyList.iterator();
            int i = 0;
            while (it.hasNext()) {
                if (it.next().toString().equals(loanInfo.getCurrency()))
                    currencyComboBox.getSelectionModel().select(i);
                i++;
            }
            i = 0;
            it = bankList.iterator();
            while (it.hasNext()) {
                if (it.next().toString().equals(loanInfo.getBank()))
                    bankComboBox.getSelectionModel().select(i);
                i++;
            }
            initdateDatePicker.setValue(loanInfo.getInitDate());
            finishdateDatePicker.setValue(loanInfo.getFinishDate());
            arrearsCheckBox.setSelected(loanInfo.getArrears());
        }
    }
    private void setScreenForms(Person curPerson) {
        if (curPerson != null) {
            fioTextField.setText(curPerson.getSurname() + " " + curPerson.getName() + " " + curPerson.getPatronymic());
            passportTextField.setText(curPerson.getPassSerial() + " " + curPerson.getPassNumber());
            birthdayDatePicker.setValue(curPerson.getBirthday());
            innTextField.setText(curPerson.getInn());
        }
    }

    @Override
    public void setSomeObject(ISearchable curObject) {
        if (curObject instanceof Person) {
            this.person = (Person) curObject;
            setScreenForms(person);
        }
        else if (curObject instanceof LoanInfo) {
            this.searchInfo = (LoanInfo) curObject;
            setScreenForms(searchInfo);
        }
        else
            System.out.println("fsyo ploho\n CIWC setSomeObject");
    }
    @Override
    public Person getPerson() {
        return person;
    }
    public LoanInfo getSearchInfo(){
        return searchInfo;
    }
}

