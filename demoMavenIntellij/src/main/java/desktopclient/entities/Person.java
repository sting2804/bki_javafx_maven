package desktopclient.entities;


import java.time.LocalDate;

/**
 *
 * @author sting
 */
public class Person implements ISearchable {
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthday;
    private String inn;
    private String passNumber;
    private String passSerial;


    public Person() {
        this("", "", "", null, "", "", "");
        
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
    public Person(String name, String surname, String patronymic,
                  LocalDate birthday, String inn, String passNumber, String passSerial) {
        super();
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.inn = inn;
        this.passNumber = passNumber;
        this.passSerial = passSerial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getPassNumber() {
        return passNumber;
    }

    public void setPassNumber(String passNumber) {
        this.passNumber = passNumber;
    }

    public String getPassSerial() {
        return passSerial;
    }

    public void setPassSerial(String passSerial) {
        this.passSerial = passSerial;
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
