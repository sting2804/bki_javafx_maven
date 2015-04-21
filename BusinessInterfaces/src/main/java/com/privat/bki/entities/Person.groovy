package com.privat.bki.entities

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.privat.bki.utils.JsonDateDeserializer
import com.privat.bki.utils.JsonDateSerializer
import com.privat.bki.utils.LocalDateAdapter
import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import java.time.LocalDate

@XmlRootElement(name = "client")
class Person implements Serializable {
    private IntegerProperty id
    private StringProperty name
    private StringProperty surname
    private StringProperty patronymic
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate birthday
    private StringProperty inn
    private StringProperty passNumber
    private StringProperty passSerial


    Person() {
        id = new SimpleIntegerProperty()
        name = new SimpleStringProperty()
        surname = new SimpleStringProperty()
        patronymic = new SimpleStringProperty()
        inn = new SimpleStringProperty()
        passNumber = new SimpleStringProperty()
        passSerial = new SimpleStringProperty()
    }

    /**
     * @param name       имя клиента
     * @param surname    фамилия
     * @param patronymic отчество
     * @param birthday   день рождения
     * @param inn        идентификационный номер
     * @param passNumber номер паспорта
     * @param passSerial серия паспорта
     */
    Person(Integer id, String name, String surname, String patronymic,
                  LocalDate birthday, String inn, String passNumber, String passSerial) {
        this.birthday = birthday
        this.id = new SimpleIntegerProperty(id)
        this.name = new SimpleStringProperty(name)
        this.surname = new SimpleStringProperty(surname)
        this.patronymic = new SimpleStringProperty(patronymic)
        this.inn = new SimpleStringProperty(inn)
        this.passNumber = new SimpleStringProperty(passNumber)
        this.passSerial = new SimpleStringProperty(passSerial)
    }

    @XmlElement
    Integer getId() {
        return id.get()
    }

    void setId(Integer id) {
        this.id.set(id)
    }

    @XmlElement
    String getName() {
        return name.get()
    }

    void setName(String name) {
        this.name.set(name)
    }

    @XmlElement
    String getSurname() {
        return surname.get()
    }

    void setSurname(String surname) {
        this.surname.set(surname)
    }

    @XmlElement
    String getPatronymic() {
        return patronymic.get()
    }

    void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic)
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    LocalDate getBirthday() {
        return birthday
    }

    void setBirthday(LocalDate birthday) {
        this.birthday = birthday
    }

    String getInn() {
        return inn.get()
    }

    void setInn(String inn) {
        this.inn.set(inn)
    }

    @XmlElement
    String getPassNumber() {
        return passNumber.get()
    }

    void setPassNumber(String passNumber) {
        this.passNumber.set(passNumber)
    }

    @XmlElement
    String getPassSerial() {
        return passSerial.get()
    }

    void setPassSerial(String passSerial) {
        this.passSerial.set(passSerial)
    }

    @Override
    String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthday +
                ", inn='" + inn + '\'' +
                ", passNumber='" + passNumber + '\'' +
                ", passSerial='" + passSerial + '\'' +
                '}'
    }

    IntegerProperty idProperty() {
        return id
    }

    StringProperty nameProperty() {
        return name
    }

    StringProperty surnameProperty() {
        return surname
    }

    StringProperty patronymicProperty() {
        return patronymic
    }

    StringProperty passNumberProperty() {
        return passNumber
    }

    StringProperty passSerialProperty() {
        return passSerial
    }

    StringProperty innProperty() {
        return inn
    }
}
