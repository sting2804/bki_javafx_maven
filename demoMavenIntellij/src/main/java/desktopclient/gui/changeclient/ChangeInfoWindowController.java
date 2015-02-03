package desktopclient.gui.changeclient;


import desktopclient.entities.Bank;
import desktopclient.entities.Currency;
import desktopclient.entities.LoanInfo;
import desktopclient.entities.Person;
import desktopclient.gui.main.MainModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author sting
 */
public class ChangeInfoWindowController implements Initializable {
    @FXML
    public Button applyButton;
    @FXML
    public ComboBox<String> bankCodeComboBox;
    @FXML
    public ComboBox<String> currencyCodeComboBox;
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
    DatePicker initdateDatePicker;
    @FXML
    DatePicker finishdateDatePicker;
    @FXML
    DatePicker birthdayDatePicker;
    @FXML
    CheckBox arrearsCheckBox;

    private Person person;
    private LoanInfo searchInfo;

    private ObservableList<String> bankCodes;
    private ObservableList<String> currencyCodes;

    private Map<String, String> bankMap;
    private Map<String, String> currencyMap;
    private MainModel mainModel;


    public ChangeInfoWindowController() {
    }

    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
        initCollections();
    }

    private void initCollections() {
        bankMap = mainModel.getBankMap();
        currencyMap = mainModel.getCurrencyMap();
        if (bankMap == null || currencyMap == null) return;
        bankCodes = FXCollections.observableArrayList(bankMap.keySet());
        currencyCodes = FXCollections.observableArrayList(currencyMap.keySet());
        bankCodeComboBox.setItems(bankCodes);
        currencyCodeComboBox.setItems(currencyCodes);
    }

    @FXML
    public void changeInfoOnClick(ActionEvent event) {
        String[] fio = fioTextField.getText().split("\\s+");
        String[] passport = passportTextField.getText().split("\\s+");
        String inn = innTextField.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        LocalDate birthday = birthdayDatePicker.getValue();
        Double initAmount, balance;
        try {
            initAmount = Double.parseDouble(initialAmountTextField.getText());
            balance = Double.parseDouble(balanceTextField.getText());
        } catch (NumberFormatException e) {
            initAmount = null;
            balance = null;
        }
        String currencyCode = currencyCodeComboBox.getValue();
        LocalDate initDate = initdateDatePicker.getValue();
        LocalDate finishDate = finishdateDatePicker.getValue();
        String bankCode = bankCodeComboBox.getValue();
        Boolean arrears = arrearsCheckBox.isSelected();
        Bank bank;
        Currency currency;
        if (fio[0].equals("") || passport[0].equals("") || inn.equals("") || birthday == null ||
                initAmount == null || balance == null || currencyCode.equals("") ||
                initDate == null || finishDate == null || bankCode.equals("")) {
            alert.setContentText("Заполните все поля");
            alert.showAndWait();
        }
        else {
            if(bankMap.get(bankCode) == null) {
                //вызов окна добавления нового банка
                bank = mainModel.callNewBankWindow(bankCode);
                //обновить карту банков
                bankMap = mainModel.getBankMap();
            } else {
                bank = new Bank(bankCode, bankMap.get(bankCode));
            }
            if(currencyMap.get(currencyCode)==null){
                //вызов окна добавления новой валюты
                currency = mainModel.callNewCurrencyWindow(currencyCode);
                //обновить карту валют
                currencyMap = mainModel.getBankMap();
            } else{
                currency = new Currency(currencyCode, currencyMap.get(currencyCode));
            }
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
        setDatePickerFormat(birthdayDatePicker);
        setDatePickerFormat(initdateDatePicker);
        setDatePickerFormat(finishdateDatePicker);
    }

    /**
     * задаёт формат даты для указанного объекта класса DatePicker
     *
     * @param datePicker
     */
    private void setDatePickerFormat(DatePicker datePicker) {
        datePicker.setConverter(createConverter("dd.MM.yyyy"));
    }

    /**
     * Задаёт конвертор формата по заданому шаблону
     *
     * @param pattern описывает требуемый формат
     * @return
     */
    private StringConverter<LocalDate> createConverter(String pattern) {
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        return converter;
    }

    private void setScreenForms(LoanInfo loanInfo) {
        if (loanInfo != null) {
            setScreenForms(loanInfo.getPerson());
            initialAmountTextField.setText(loanInfo.getInitAmount().toString());
            balanceTextField.setText(loanInfo.getBalance().toString());
            bankCodeComboBox.setValue(loanInfo.getBank().getCode());
            currencyCodeComboBox.setValue(loanInfo.getCurrency().getCode());
            initdateDatePicker.setValue(loanInfo.getInitDate());
            finishdateDatePicker.setValue(loanInfo.getFinishDate());
            arrearsCheckBox.setSelected(loanInfo.getArrears());
        }
    }

    private void setScreenForms(Person curPerson) {
        if (curPerson != null) {
            fioTextField.setText(curPerson.getSurname() + " " + curPerson.getName());
            if (curPerson.getPatronymic() != null) fioTextField.appendText(" " + curPerson.getPatronymic());
            passportTextField.setText(curPerson.getPassSerial() + " " + curPerson.getPassNumber());
            birthdayDatePicker.setValue(curPerson.getBirthday());
            innTextField.setText(curPerson.getInn());
        }
    }

    public void setLoanInfo(LoanInfo searchInfo) {
        this.searchInfo = searchInfo;
        setScreenForms(searchInfo);
    }

    public void setPerson(Person person) {
        this.person =  person;
        setScreenForms(person);
    }


    public Person getSearchableObject() {
        return person;
    }

    public LoanInfo getNewInfo() {
        return searchInfo;
    }

    public void setCurrencyMap(Map<String, String> currencyMap) {
        this.currencyMap = currencyMap;
    }

    public void setBankMap(Map<String, String> bankMap) {
        this.bankMap = bankMap;
    }
}

