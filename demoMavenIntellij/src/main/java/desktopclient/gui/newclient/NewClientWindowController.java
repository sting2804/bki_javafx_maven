
package desktopclient.gui.newclient;


import desktopclient.entities.LoanInfo;
import desktopclient.entities.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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
public class NewClientWindowController implements  Initializable {
    @FXML
    public Button applyButton;

    private LoanInfo searchInfo;
    @FXML
    TextField fioTextField;
    @FXML
    TextField passportTextField;
    @FXML
    TextField innTextField;
    @FXML
    DatePicker birthdayDatePicker;
   
    private Person person;

    public NewClientWindowController() {
        
    }

    @FXML
    private void applyNewClientOnClick(ActionEvent event) {
        String [] fio = fioTextField.getText().split("\\s+");
        String [] passport = passportTextField.getText().split("\\s+");
        String inn = innTextField.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        LocalDate birthday = birthdayDatePicker.getValue();
        if (fio[0].equals("") || passport[0].equals("") || inn.equals("") || birthday==null){
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
            Stage curStage = (Stage) (applyButton.getScene().getWindow());
            curStage.close();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDatePickerFormat(birthdayDatePicker);
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


    public void setSearchObject(LoanInfo curInfo) {
        this.searchInfo = curInfo;
        setScreenForms();
    }

    private LocalDate convertDateToLocalDate(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        LocalDate res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return res;
    }

    private void setScreenForms() {
        if (searchInfo != null) {
            Person curPerson = searchInfo.getPerson();
            fioTextField.setText(curPerson.getSurname() + " " + curPerson.getName() + " " + curPerson.getPatronymic());
            passportTextField.setText(curPerson.getPassSerial() + " " + curPerson.getPassNumber());
            birthdayDatePicker.setValue(curPerson.getBirthday());
            innTextField.setText(curPerson.getInn());
        }
    }

    public Person getPerson() {
        return person;
    }
}

