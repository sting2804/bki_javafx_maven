package com.privat.bki.apiserver.mappers;

import com.privat.bki.entities.Currency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sting on 3/2/15.
 */
public class CurrencyRowMapper implements RowMapper<Currency> {
    @Override
    public Currency mapRow(ResultSet rs, int ii) throws SQLException {
        Currency currency = new Currency();
        int i = 1;
        currency.setCode(rs.getString(i++));
        currency.setName(rs.getString(i));
        return currency;
    }
}
