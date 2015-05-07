package com.privat.bki.apiserver.spring.services.statistic;

import com.privat.bki.apiserver.mappers.BanksRowMapper;
import com.privat.bki.apiserver.mappers.PreferredBankRowMapper;
import com.privat.bki.business.entities.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sting on 5/6/15.
 */
@Component("statisticDao")
public class StatisticDaoImpl implements StatisticDao {

    private List data;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public StatisticDaoImpl() {
        this.data = new ArrayList<>();
    }

    @Override
    public Map<Integer, Bank> theMostPreferredBank(int... years) {
        Map<Integer, Bank> banks = new LinkedHashMap<>();
        for(int year : years){
            banks.put(year, theMostPreferredBank(year));
        }
        return banks;
    }

    @Override
    public Bank theMostPreferredBank(int year) {
        data.removeAll(data);
        String query =
                "select distinct count(*) cnt, b.bank_code, b.bank_name " +
                        "from bank b " +
                        "join loan l on b.bank_code=l.bank_code " +
                        "where datepart(year,l.init_date)=? " +
                        "GROUP BY b.bank_name " +
                        "HAVING count(*)=(" +
                        "select max(count(*)) mx " +
                        "from loan l2 " +
                        "JOIN bank b2 ON b2.bank_code = l2.bank_code " +
                        "WHERE DATEPART(year,l2.init_date)=? " +
                        "GROUP BY b2.bank_code" +
                        ")";
        data = jdbcTemplate.query(query,
                preparedStatement -> {
                    preparedStatement.setString(1, String.valueOf(year));
                    preparedStatement.setString(2, String.valueOf(year));
                }, new PreferredBankRowMapper()
        );
        if(data!=null && data.size()>0)
            return (Bank) data.get(0);
        return null;
    }

    @Override
    public Map<Integer, Map<String,Integer>> theMostCreditAge(int... years) {
        Map<Integer, Map<String,Integer>> ages = new LinkedHashMap<>();
        for(int year : years){
            ages.put(year, theMostCreditAge(year));
        }
        return ages;
    }

    @Override
    public Map<String, Integer> theMostCreditAge(int year) {
        return null;
    }
}
