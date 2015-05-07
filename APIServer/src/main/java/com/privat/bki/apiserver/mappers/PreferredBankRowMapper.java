package com.privat.bki.apiserver.mappers;

import com.privat.bki.business.entities.Bank;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sting on 3/2/15.
 */
public class PreferredBankRowMapper implements RowMapper<Bank> {
    @Override
    public Bank mapRow(ResultSet rs, int ii) throws SQLException {
        Bank bank = new Bank();
        /*в выборке 3 поля, первое - количество кредитов в банке, а остальные 2 - информация о банке.
        поэтому отсчёт идёт сразу со второго столбца*/
        int i = 2;
        bank.setCode(rs.getString(i++));
        bank.setName(rs.getString(i));
        return bank;
    }
}
