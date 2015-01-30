/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopclient.entities;

import java.io.Serializable;
import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author sting
 */
public class LoanInfo implements Serializable{
    private IntegerProperty id;
    private DoubleProperty initAmount;
    private LocalDate initDate;
    private LocalDate finishDate;
    private DoubleProperty balance;
    private BooleanProperty arrears;
    private Bank bank;
    private Currency currency;
    private Person person;

    public LoanInfo() {
        this.arrears = new SimpleBooleanProperty();
        this.id = new SimpleIntegerProperty();
        this.initAmount = new SimpleDoubleProperty();
        this.balance = new SimpleDoubleProperty();
    }


    public LoanInfo(Integer id, Double initAmount, LocalDate initDate,
                    LocalDate finishDate, Double balance, Boolean arrears,
                    Bank bank, Currency currency, Person person) {
        this.id = new SimpleIntegerProperty(id);
        this.initAmount = new SimpleDoubleProperty(initAmount);
        this.initDate = initDate;
        this.finishDate = finishDate;
        this.balance = new SimpleDoubleProperty(balance);
        this.arrears = new SimpleBooleanProperty(arrears);
        this.bank = bank;
        this.currency = currency;
        this.person = person;
    }


    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public Double getInitAmount() {
        return initAmount.get();
    }

    public void setInitAmount(Double initAmount) {
        this.initAmount.set(initAmount);
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public Double getBalance() {
        return balance.get();
    }

    public void setBalance(Double balance) {
        this.balance.set(balance);
    }

    public Boolean getArrears() {
        return arrears.get();
    }

    public void setArrears(Boolean arrears) {
        this.arrears.set(arrears);
    }
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "LoanInfo{" +
                "id=" + id +
                ", initAmount=" + initAmount +
                ", initDate=" + initDate +
                ", finishDate=" + finishDate +
                ", balance=" + balance +
                ", arrears=" + arrears +
                ", bank=" + bank +
                ", currency=" + currency +
                ", person=" + person +
                '}';
    }
    
    
    public BooleanProperty arrearsProperty() {
      return arrears;
    }
    public DoubleProperty balanceProperty() {
      return balance;
      
    }
    public DoubleProperty initAmountProperty() {
      return initAmount;
      
    }
    public IntegerProperty idProperty() {
      return id;
    }
}
