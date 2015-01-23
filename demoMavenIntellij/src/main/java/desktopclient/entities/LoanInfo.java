/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopclient.entities;

import java.time.LocalDate;

/**
 *
 * @author sting
 */
public class LoanInfo implements ISearchable{
    private Integer id;
    private Double initAmount;
    private LocalDate initDate;
    private LocalDate finishDate;
    private Double balance;
    private Boolean arrears;
    private String bank;
    private String currency;
    private Person person;

    /*public LoanInfo() {
        this(0, 0d, null, null, 0d, false, "", "", null);
    }*/

    public LoanInfo() {
    }

    public LoanInfo(Integer id, Double initAmount, LocalDate initDate, LocalDate finishDate, Double balance, Boolean arrears, String bank, String currency, Person person) {
        this.id = id;
        this.initAmount = initAmount;
        this.initDate = initDate;
        this.finishDate = finishDate;
        this.balance = balance;
        this.arrears = arrears;
        this.bank = bank;
        this.currency = currency;
        this.person = person;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Double getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(Double initAmount) {
        this.initAmount = initAmount;
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
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getArrears() {
        return arrears;
    }

    public void setArrears(Boolean arrears) {
        this.arrears = arrears;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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
                ", bank='" + bank + '\'' +
                ", currency='" + currency + '\'' +
                ", person=" + person +
                '}';
    }
}
