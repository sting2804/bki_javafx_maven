
package com.privat.bki.desktopapp.gui.searchuser;
import com.privat.bki.business.entities.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @author sting
 */
public class ClientSearchWindowController implements Initializable {

    @FXML
    public Button searchButton;
    private Person person;

    @FXML
    public ComboBox<String> methodComboBox;
    @FXML
    public GridPane byPassportGridPane;
    @FXML
    public GridPane byFIOGridPane;
    @FXML
    public GridPane byINNGridPane;
    @FXML
    public TextField surnameSearchTextField;
    @FXML
    public TextField nameSearchTextField;
    @FXML
    public TextField patrinymicSearchTextField;
    @FXML
    public DatePicker birthdaySearchDatePicker;
    @FXML
    public TextField passportSerialTextField;
    @FXML
    public TextField passportNumberTextField;
    @FXML
    public TextField innTextField;
    private int state;

    public int getState() {
        return state;
    }

    public ClientSearchWindowController() {
        
    }

    private void searchClient(){
        state = methodComboBox.getSelectionModel().getSelectedIndex();
        person = new Person();
        switch (state) {
            case 0:
                person.setName(nameSearchTextField.getText());
                person.setSurname(surnameSearchTextField.getText());
                if (!patrinymicSearchTextField.getText().isEmpty())
                    person.setPatronymic(patrinymicSearchTextField.getText());
                person.setBirthday(birthdaySearchDatePicker.getValue());
                break;
            case 1:
                person.setInn(innTextField.getText());
                break;
            case 2:
                person.setPassNumber(passportNumberTextField.getText());
                person.setPassSerial(passportSerialTextField.getText());
                break;
        }
    }
    @FXML
    void choiceBoxMethodsOnAction(ActionEvent event) {
        state = methodComboBox.getSelectionModel().getSelectedIndex();
        switch (state){
            case 0:
                clearForms();
                byINNGridPane.setVisible(false);
                byPassportGridPane.setVisible(false);
                byFIOGridPane.setVisible(true);
                break;
            case 1:
                clearForms();
                byINNGridPane.setVisible(true);
                byPassportGridPane.setVisible(false);
                byFIOGridPane.setVisible(false);
                break;
            case 2:
                clearForms();
                byINNGridPane.setVisible(false);
                byPassportGridPane.setVisible(true);
                byFIOGridPane.setVisible(false);
                break;
            default:
                clearForms();
                byINNGridPane.setVisible(false);
                byPassportGridPane.setVisible(false);
                byFIOGridPane.setVisible(true);
                break;
        }
    }
    @FXML
    public void searchButtonOnClick(ActionEvent event) {
        searchClient();
        Stage curStage = (Stage)searchButton.getScene().getWindow();
        curStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList <String> observableList = FXCollections.observableArrayList();
        observableList.add(0, "По ФИО + ДР");
        observableList.add(1, "По ИНН");
        observableList.add(2, "По паспорту");
        methodComboBox.setItems(observableList);
        methodComboBox.getSelectionModel().selectFirst();
        setDatePickerFormat(birthdaySearchDatePicker);
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


    private void clearForms(){
        surnameSearchTextField.clear();
        nameSearchTextField.clear();
        patrinymicSearchTextField.clear();
        birthdaySearchDatePicker.setValue(null);
        passportSerialTextField.clear();
        passportNumberTextField.clear();
        innTextField.clear();
    }

    public Person getSearchablePerson() {
        return person;
    }
}
