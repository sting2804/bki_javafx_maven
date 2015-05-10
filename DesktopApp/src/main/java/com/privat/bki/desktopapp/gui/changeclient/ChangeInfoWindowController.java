package com.privat.bki.desktopapp.gui.changeclient;
import com.privat.bki.business.entities.*;
import com.privat.bki.business.entities.Currency;
import com.privat.bki.desktopapp.gui.main.MainModel;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sting
 */
public class ChangeInfoWindowController implements Initializable {

    @FXML
    public Button applyButton;
    @FXML
    public ComboBox<String> bankNameComboBox;
    @FXML
    public ComboBox<String> currencyCodeComboBox;
    @FXML
    public TextField fioTextField;
    @FXML
    public TextField passportTextField;
    @FXML
    public TextField innTextField;
    @FXML
    public TextField initialAmountTextField;
    @FXML
    public TextField balanceTextField;
    @FXML
    public DatePicker initdateDatePicker;
    @FXML
    public DatePicker finishdateDatePicker;
    @FXML
    public DatePicker birthdayDatePicker;
    @FXML
    public CheckBox arrearsCheckBox;
    @FXML
    public ComboBox<String> genderComboBox;

    private Person person;
    private LoanInfo searchInfo;

    private ObservableList<String> bankNames;
    private ObservableList<String> currencyCodes;
    private ObservableList<String> genderCodes;

    private Map<String, String> bankMap;
    private Map<String, String> currencyMap;
    private Map<String,String> genderMap;
    private MainModel mainModel;


    public ChangeInfoWindowController() {
    }

    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
        initCollections();
    }

    private void initCollections() {
        genderMap = mainModel.getGenderMap();
        bankMap = mainModel.getBankMap();
        currencyMap = mainModel.getCurrencyMap();
        if (bankMap == null || currencyMap == null) return;
        bankNames = FXCollections.observableArrayList(bankMap.values());
        currencyCodes = FXCollections.observableArrayList(currencyMap.keySet());
        genderCodes = FXCollections.observableArrayList(genderMap.keySet());
        bankNameComboBox.setItems(bankNames);
        currencyCodeComboBox.setItems(currencyCodes);
        genderComboBox.setItems(genderCodes);
    }

    @FXML
    public void changeInfoOnClick(ActionEvent event) {
        String[] fio = fioTextField.getText().split("\\s+");
        String[] passport = passportTextField.getText().split("\\s+");
        String inn = innTextField.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        LocalDate birthday = birthdayDatePicker.getValue();
        Double initAmount;
        Double balance;
        try {
            initAmount = Double.parseDouble(initialAmountTextField.getText());
            balance = Double.parseDouble(balanceTextField.getText());
        } catch (NumberFormatException e) {
            initAmount = null;
            balance = null;
        }
        String currencyCode = currencyCodeComboBox.getValue();
        LocalDate initDate = initdateDatePicker.getValue();
        LocalDate finishDate;
        try{
            finishDate = finishdateDatePicker.getValue();
        } catch (NullPointerException e){
            finishDate = null;

        }
        String bankName = bankNameComboBox.getValue();
        String genderValue = genderMap.get(genderComboBox.getValue());
        Boolean arrears = arrearsCheckBox.isSelected();
        Bank bank;
        Currency currency;
        if (fio[0].equals("") || passport[0].equals("") || inn.equals("") || birthday == null ||
                initAmount == null || balance == null || currencyCode.equals("") ||
                initDate == null || bankName.equals("") || genderValue.equals("")) {
            alert.setContentText("Заполните все поля");
            alert.showAndWait();
        } else {
            if (!bankMap.containsValue(bankName)) {
                //вызов окна добавления нового банка
                bank = mainModel.callNewBankWindow(bankName);
                //обновить карту банков
                bankMap = mainModel.getBankMap();
            } else {
                bank = new Bank(getKeyByValue(bankMap,bankName), bankName);
            }
            if (currencyMap.get(currencyCode) == null) {
                //вызов окна добавления новой валюты
                currency = mainModel.callNewCurrencyWindow(currencyCode);
                //обновить карту валют
                currencyMap = mainModel.getBankMap();
            } else {
                currency = new Currency(currencyCode, currencyMap.get(currencyCode));
            }
            genderMap = mainModel.getGenderMap();

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
            person.setGender(genderValue);

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

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
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
        return new StringConverter<LocalDate>() {
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
    }

    private void setScreenForms(LoanInfo loanInfo) {
        if (loanInfo != null) {
            setScreenForms(loanInfo.getPerson());
            initialAmountTextField.setText(loanInfo.getInitAmount().toString());
            balanceTextField.setText(loanInfo.getBalance().toString());
            bankNameComboBox.setValue(loanInfo.getBank().getName());
            currencyCodeComboBox.setValue(loanInfo.getCurrency().getCode());
            initdateDatePicker.setValue(loanInfo.getInitDate());
            try{
                finishdateDatePicker.setValue(loanInfo.getFinishDate());
            }
            catch (Exception e){
                e.printStackTrace();
            }
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
            bankNameComboBox.setValue(mainModel.getGenderMap().get(curPerson.getGender()));
        }
    }

    public void setLoanInfo(LoanInfo searchInfo) {
        this.searchInfo = searchInfo;
        setScreenForms(searchInfo);
    }

    public void setPerson(Person person) {
        this.person = person;
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

    public void setGenderMap(Map<String, String> genderMap) {
        this.genderMap = genderMap;
    }
}

