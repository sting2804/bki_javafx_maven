/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopclient.entities;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 *
 * @author sting
 */
public class LoanInfo implements ISearchable{
    private DoubleProperty initAmount;
    private ObjectProperty<LocalDate> initDate;
    private ObjectProperty<LocalDate> finishDate;
    private DoubleProperty balance;
    private BooleanProperty arrears;
    private StringProperty bank;
    private StringProperty currency;
    private ObjectProperty<Person> person;

    public LoanInfo() {
        this(0d, null, null, 0d, false, "", "", null);
    }
    
    

    public LoanInfo(Double initAmount, LocalDate initDate, LocalDate finishDate, Double balance, Boolean arrears, String bank, String currency, Person person) {
        this.initAmount = new SimpleDoubleProperty(initAmount);
        this.initDate = new SimpleObjectProperty<>(initDate);
        this.finishDate = new SimpleObjectProperty<>(finishDate);
        this.balance = new SimpleDoubleProperty(balance);
        this.arrears = new SimpleBooleanProperty(arrears);
        this.bank = new SimpleStringProperty(bank);
        this.currency = new SimpleStringProperty(currency);
        this.person = new SimpleObjectProperty<>(person);
    }

    public Double getInitAmount() {
        return initAmount.getValue();
    }

    public void setInitAmount(Double initAmount) {
        this.initAmount.setValue(initAmount);
    }

    public LocalDate getInitDate() {
        return initDate.getValue();
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate.setValue(initDate);
    }

    public LocalDate getFinishDate() {
        return finishDate.getValue();
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate.setValue(finishDate);
    }

    public Double getBalance() {
        return balance.getValue();
    }

    public void setBalance(Double balance) {
        this.balance.setValue(balance);
    }

    public Boolean getArrears() {
        return arrears.getValue();
    }

    public void setArrears(Boolean arrears) {
        this.arrears.setValue(arrears);
    }

    public String getBank() {
        return bank.getValue();
    }

    public void setBank(String bank) {
        this.bank.setValue(bank);
    }

    public String getCurrency() {
        return currency.getValue();
    }

    public void setCurrency(String currency) {
        this.currency.setValue(currency);
    }

    public Person getPerson() {
        return person.getValue();
    }

    public void setPerson(Person person) {
        this.person.setValue(person);
    }

    @Override
    public String toString() {
        return "LoanInfo{" +
                "initAmount=" + initAmount +
                ", initDate=" + initDate +
                ", finishDate=" + finishDate +
                ", balance=" + balance +
                ", arrears=" + arrears +
                ", bank=" + bank +
                ", currency=" + currency +
                ", person=" + person +
                '}';
    }
}
