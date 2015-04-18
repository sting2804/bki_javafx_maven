/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.privat.bki.entities

import com.privat.bki.utils.LocalDateAdapter
import javafx.beans.property.*

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import java.time.LocalDate

@XmlRootElement(name = "information")
class LoanInfo implements Serializable {
    private IntegerProperty id
    private DoubleProperty initAmount
    private LocalDate initDate
    private LocalDate finishDate
    private DoubleProperty balance
    private BooleanProperty arrears
    private Bank bank
    private Currency currency
    private Person person

    LoanInfo() {
        this.arrears = new SimpleBooleanProperty()
        this.id = new SimpleIntegerProperty()
        this.initAmount = new SimpleDoubleProperty()
        this.balance = new SimpleDoubleProperty()
    }


    LoanInfo(Integer id, Double initAmount, LocalDate initDate,
                    LocalDate finishDate, Double balance, Boolean arrears,
                    Bank bank, Currency currency, Person person) {
        this.id = new SimpleIntegerProperty(id)
        this.initAmount = new SimpleDoubleProperty(initAmount)
        this.initDate = initDate
        this.finishDate = finishDate
        this.balance = new SimpleDoubleProperty(balance)
        this.arrears = new SimpleBooleanProperty(arrears)
        this.bank = bank
        this.currency = currency
        this.person = person
    }

    @XmlElement
    Integer getId() {
        return id.get()
    }

    void setId(Integer id) {
        this.id.set(id)
    }

    @XmlElement
    Double getInitAmount() {
        return initAmount.get()
    }

    void setInitAmount(Double initAmount) {
        this.initAmount.set(initAmount)
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    LocalDate getInitDate() {
        return initDate
    }

    void setInitDate(LocalDate initDate) {
        this.initDate = initDate
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    LocalDate getFinishDate() {
        return finishDate
    }

    void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate
    }

    @XmlElement
    Double getBalance() {
        return balance.get()
    }

    void setBalance(Double balance) {
        this.balance.set(balance)
    }

    @XmlElement
    Boolean getArrears() {
        return arrears.get()
    }

    void setArrears(Boolean arrears) {
        this.arrears.set(arrears)
    }

    @XmlElement
    Person getPerson() {
        return person
    }

    void setPerson(Person person) {
        this.person = person
    }

    @XmlElement
    Bank getBank() {
        return bank
    }

    void setBank(Bank bank) {
        this.bank = bank
    }

    @XmlElement
    Currency getCurrency() {
        return currency
    }

    void setCurrency(Currency currency) {
        this.currency = currency
    }

    BooleanProperty arrearsProperty() {
        return arrears
    }

    DoubleProperty balanceProperty() {
        return balance
    }

    DoubleProperty initAmountProperty() {
        return initAmount

    }

    IntegerProperty idProperty() {
        return id
    }

    @Override
    String toString() {
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
                '}'
    }
}
