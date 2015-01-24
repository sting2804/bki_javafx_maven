package desktopclient.entities;


import javafx.beans.property.*;

import java.time.LocalDate;

/**
 *
 * @author sting
 */
public class Person implements ISearchable {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty surname;
    private StringProperty patronymic;
    private ObjectProperty<LocalDate> birthday;
    private StringProperty inn;
    private StringProperty passNumber;
    private StringProperty passSerial;
    //private StringProperty fio;
    //private StringProperty passport;


    /*public Person() {
        this(0, "", "", "", null, "", "", "");
    }*/

    public Person() {
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        surname = new SimpleStringProperty();
        patronymic = new SimpleStringProperty();
        inn = new SimpleStringProperty();
        passNumber = new SimpleStringProperty();
        passSerial = new SimpleStringProperty();
        birthday = new SimpleObjectProperty<>();
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
    public Person(IntegerProperty id, StringProperty name, StringProperty surname,
                  StringProperty patronymic, ObjectProperty<LocalDate> birthday,
                  StringProperty inn, StringProperty passNumber, StringProperty passSerial) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.inn = inn;
        this.passNumber = passNumber;
        this.passSerial = passSerial;
    }

    @Override
    public Integer getId() {
        return id.get();
    }


    public IntegerProperty idProperty() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getPatronymic() {
        return patronymic.get();
    }

    public StringProperty patronymicProperty() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public String getInn() {
        return inn.get();
    }

    public StringProperty innProperty() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn.set(inn);
    }

    public String getPassNumber() {
        return passNumber.get();
    }

    public StringProperty passNumberProperty() {
        return passNumber;
    }

    public void setPassNumber(String passNumber) {
        this.passNumber.set(passNumber);
    }

    public String getPassSerial() {
        return passSerial.get();
    }

    public StringProperty passSerialProperty() {
        return passSerial;
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
}
