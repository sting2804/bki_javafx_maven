/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.privat.bki.business.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.privat.bki.business.utils.JsonDateDeserializer;
import com.privat.bki.business.utils.JsonDateSerializer;
import com.privat.bki.business.utils.LocalDateAdapter;
import javafx.beans.property.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement(name = "information")
public class LoanInfo implements Serializable {

    private IntegerProperty id;
    private DoubleProperty initAmount;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDate initDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
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

    @XmlElement
    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    @XmlElement
    public Double getInitAmount() {
        return initAmount.get();
    }

    public void setInitAmount(Double initAmount) {
        this.initAmount.set(initAmount);
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    @XmlElement
    public Double getBalance() {
        return balance.get();
    }

    public void setBalance(Double balance) {
        this.balance.set(balance);
    }

    @XmlElement
    public Boolean getArrears() {
        return arrears.get();
    }

    public void setArrears(Boolean arrears) {
        this.arrears.set(arrears);
    }

    @XmlElement
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @XmlElement
    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @XmlElement
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
}
