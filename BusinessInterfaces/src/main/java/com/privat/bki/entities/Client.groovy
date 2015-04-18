package com.privat.bki.entities

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import java.time.LocalDate

/**
 * Created by sting on 3/4/15.
 */
@XmlRootElement
class Client {
    private int id
    private String name
    private String surname
    private String patronymic
    private LocalDate birthday
    private String inn
    private String passNumber
    private String passSerial

    Client() {
        name = ""
        surname = ""
        patronymic = ""
        inn = ""
        passNumber = ""
        passSerial = ""
        birthday = LocalDate.MIN
    }

    int getId() {
        return id
    }

    void setId(int id) {
        this.id = id
    }

    @XmlElement
    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    @XmlElement
    String getSurname() {
        return surname
    }

    void setSurname(String surname) {
        this.surname = surname
    }

    @XmlElement
    String getPatronymic() {
        return patronymic
    }

    void setPatronymic(String patronymic) {
        this.patronymic = patronymic
    }

    @XmlElement
    LocalDate getBirthday() {
        return birthday
    }

    void setBirthday(LocalDate birthday) {
        this.birthday = birthday
    }

    @XmlElement
    String getInn() {
        return inn
    }

    void setInn(String inn) {
        this.inn = inn
    }

    @XmlElement
    String getPassNumber() {
        return passNumber
    }

    void setPassNumber(String passNumber) {
        this.passNumber = passNumber
    }

    @XmlElement
    String getPassSerial() {
        return passSerial
    }

    void setPassSerial(String passSerial) {
        this.passSerial = passSerial
    }

    @Override
    String toString() {
        return "Client{" +
                "id=" + id +
                ", name=" + name +
                ", surname=" + surname +
                ", patronymic=" + patronymic +
                ", birthday=" + birthday +
                ", inn=" + inn +
                ", passNumber=" + passNumber +
                ", passSerial=" + passSerial +
                '}'
    }
}
