package com.privat.bki.apiserver.mappers;

import com.privat.bki.entities.Bank;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sting on 3/2/15.
 */
public class BanksRowMapper implements RowMapper<Bank> {
    @Override
    public Bank mapRow(ResultSet rs, int ii) throws SQLException {
        Bank bank = new Bank();
        int i = 1;
        bank.setCode(rs.getString(i++));
        bank.setName(rs.getString(i));
        return bank;
    }
}
