package com.privat.entities;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author sting
 */
public class Person implements Serializable {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty surname;
    private StringProperty patronymic;
    private LocalDate birthday;
    private StringProperty inn;
    private StringProperty passNumber;
    private StringProperty passSerial;


    public Person() {
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        surname = new SimpleStringProperty();
        patronymic = new SimpleStringProperty();
        inn = new SimpleStringProperty();
        passNumber = new SimpleStringProperty();
        passSerial = new SimpleStringProperty();
    }

    /**
     *
     * @param name имя клиента
     * @param surname фамилия
     * @param patronymic отчество
     * @param birthday день рождения
     * @param inn идентификационный номер
     * @param passNumber номер паспорта
     * @param passSerial серия паспорта
     */
    public Person(Integer id, String name, String surname, String patronymic,
                  LocalDate birthday, String inn, String passNumber, String passSerial) {
        this.birthday = birthday;
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.inn = new SimpleStringProperty(inn);
        this.passNumber = new SimpleStringProperty(passNumber);
        this.passSerial = new SimpleStringProperty(passSerial);
    }

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getPatronymic() {
        return patronymic.get();
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getInn() {
        return inn.get();
    }

    public void setInn(String inn) {
        this.inn.set(inn);
    }

    public String getPassNumber() {
        return passNumber.get();
    }

    public void setPassNumber(String passNumber) {
        this.passNumber.set(passNumber);
    }

    public String getPassSerial() {
        return passSerial.get();
    }

    public void setPassSerial(String passSerial) {
        this.passSerial.set(passSerial);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthday +
                ", inn='" + inn + '\'' +
                ", passNumber='" + passNumber + '\'' +
                ", passSerial='" + passSerial + '\'' +
                '}';
    }
    
    public IntegerProperty idProperty() {
      return id;
    }
    public StringProperty nameProperty() {
      return name;
    }
    public StringProperty surnameProperty() {
      return surname;
    }
    public StringProperty patronymicProperty() {
      return patronymic;
    }
    public StringProperty passNumberProperty() {
      return passNumber;
    }
    public StringProperty passSerialProperty() {
      return passSerial;
    }
    public StringProperty innProperty() {
      return inn;
    }
}
