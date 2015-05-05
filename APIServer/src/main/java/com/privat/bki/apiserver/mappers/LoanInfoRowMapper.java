package com.privat.bki.apiserver.mappers;

import com.privat.bki.business.entities.Bank;
import com.privat.bki.business.entities.Currency;
import com.privat.bki.business.entities.LoanInfo;
import com.privat.bki.business.entities.Person;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Created by sting on 3/2/15.
 */
public class LoanInfoRowMapper implements RowMapper<LoanInfo>{
    private static final Logger log = Logger.getLogger(LoanInfoRowMapper.class);
    @Override
    public LoanInfo mapRow(ResultSet rs, int ii) throws SQLException {
        LoanInfo loanInfo;
        Bank bank;
        Person person;
        Currency currency;
        bank = new Bank();
        currency = new Currency();
        person = new Person();
        loanInfo = new LoanInfo();
        int i = 1;
        person.setName(rs.getString(i++));
        person.setSurname(rs.getString(i++));
        person.setPatronymic(rs.getString(i++));
        person.setBirthday(rs.getDate(i++).toLocalDate());
        person.setInn(rs.getString(i++));
        person.setPassSerial(rs.getString(i++));
        person.setPassNumber(rs.getString(i++));
        loanInfo.setInitAmount(rs.getDouble(i++));
        try {
            loanInfo.setInitDate(rs.getDate(i++).toLocalDate());
        } catch (SQLException | NullPointerException e) {
            //log.error(e.getMessage());
            loanInfo.setInitDate(null);
        }
        try {
            loanInfo.setFinishDate(rs.getDate(i++).toLocalDate());
        } catch (SQLException | NullPointerException e) {
            //log.error(e.getMessage());
            loanInfo.setFinishDate(null);
        }
        try {
            loanInfo.setBalance(rs.getDouble(i++));
        } catch (SQLException | NullPointerException e) {
            //log.error(e.getMessage());
            loanInfo.setBalance(null);
        }
        loanInfo.setArrears(rs.getBoolean(i++));
        bank.setCode(rs.getString(i++));
        bank.setName(rs.getString(i++));

        currency.setCode(rs.getString(i++));
        currency.setName(rs.getString(i++));
        person.setId(rs.getInt(i++));

        loanInfo.setId(rs.getInt(i));

        loanInfo.setPerson(person);
        loanInfo.setBank(bank);
        loanInfo.setCurrency(currency);

        return loanInfo;
    }
}
