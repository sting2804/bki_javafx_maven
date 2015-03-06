package com.privat.bki.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

/**
 * Created by sting on 3/4/15.
 */
@XmlRootElement
public class Client {
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthday;
    private String inn;
    private String passNumber;
    private String passSerial;

    public Client() {
        name = "";
        surname = "";
        patronymic = "";
        inn = "";
        passNumber = "";
        passSerial = "";
        birthday = LocalDate.MIN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @XmlElement
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @XmlElement
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @XmlElement
    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    @XmlElement
    public String getPassNumber() {
        return passNumber;
    }

    public void setPassNumber(String passNumber) {
        this.passNumber = passNumber;
    }

    @XmlElement
    public String getPassSerial() {
        return passSerial;
    }

    public void setPassSerial(String passSerial) {
        this.passSerial = passSerial;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name=" + name +
                ", surname=" + surname +
                ", patronymic=" + patronymic +
                ", birthday=" + birthday +
                ", inn=" + inn +
                ", passNumber=" + passNumber +
                ", passSerial=" + passSerial +
                '}';
    }
}
