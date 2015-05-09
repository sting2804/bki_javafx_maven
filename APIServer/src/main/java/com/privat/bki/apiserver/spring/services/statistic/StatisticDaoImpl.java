package com.privat.bki.apiserver.spring.services.statistic;

import com.privat.bki.apiserver.mappers.PreferredBankRowMapper;
import com.privat.bki.business.entities.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by sting on 5/6/15.
 */
@Component("statisticDao")
public class StatisticDaoImpl implements StatisticDao {

    private List data;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public StatisticDaoImpl() {
        this.data = new ArrayList<>();
    }

    @Override
    public List<Map> theMostPreferredBank(int... years) {
        List<Map> bankList = new ArrayList<>();
        Map <String,Object> banks;
        for(int year : years){
            banks = new LinkedHashMap<>();
            banks.put("year", year);
            banks.put("bank", theMostPreferredBank(year));
            bankList.add(banks);
        }
        return bankList;
    }

    @Override
    public Bank theMostPreferredBank(int year) {
        data.removeAll(data);
        String query =
                "select distinct count(*) cnt, b.bank_code, b.bank_name " +
                        "from bank b " +
                        "join loan l on b.bank_code=l.bank_code " +
                        "where datepart(year,l.init_date)=:year " +
                        "GROUP BY b.bank_name " +
                        "HAVING count(*)=(" +
                        "select max(count(*)) mx " +
                        "from loan l2 " +
                        "JOIN bank b2 ON b2.bank_code = l2.bank_code " +
                        "WHERE DATEPART(year,l2.init_date)=:year " +
                        "GROUP BY b2.bank_code" +
                        ")";
        Map<String, Integer> params = new LinkedHashMap<>();
        params.put("year",year);
        data = jdbcTemplate.query(query, params, new PreferredBankRowMapper());
        if(data!=null && data.size()>0)
            return (Bank) data.get(0);
        return null;
    }

    @Override
    public List<Map> theMostCreditAge(int... years) {
        List<Map> ages = new ArrayList<>();
        Map age;
        for(int year : years){
            age = new LinkedHashMap<>();
            age.put("year", year);
            age.put("age", theMostCreditAge(year));
            ages.add(age);
        }
        return ages;
    }

    @Override
    public String theMostCreditAge(int year) {
        String query =
                "select (" +
                        "case when young=0 and middle_I=0 and middle_II=0 and advanced=0 and senium=0 and long_liver=0 then 'neither' " +
                        "       when young>=middle_I and young>=middle_II and young>=advanced and young>=senium and young>=long_liver then 'young' " +
                        "       when middle_I>young and middle_I>=middle_II and middle_I>=advanced and middle_I>=senium and middle_I>=long_liver then 'middle_I' " +
                        "       when middle_II>young and middle_II>middle_I and middle_II>=advanced and middle_II>=senium and middle_II>=long_liver then 'middle_II' " +
                        "       when advanced>young and advanced>middle_I and advanced>middle_II and advanced>=senium and advanced>=long_liver then 'advanced' " +
                        "       when senium>young and senium>middle_I and senium>middle_II and senium>advanced and senium>=long_liver then 'senium' " +
                        "       when long_liver>young and long_liver>middle_I and long_liver>middle_II and long_liver>advanced and long_liver>senium then 'long_liver' " +
                        "       end) greatest " +
                        "from ( " +
                        "--calculate the summary of loans of men and women by ages \n" +
                        "select " +
                        "sum(summary)summary, " +
                        "sum(young)young, " +
                        "sum(middle_I)middle_I, " +
                        "sum(middle_II)middle_II, " +
                        "sum(advanced)advanced, " +
                        "sum(senium)senium, " +
                        "sum(long_liver)long_liver " +
                        "from(" +
                        "--for men \n" +
                        "select count(*) summary, " +
                        "count(case when datediff(year,c.birthday,getdate()) between 17 and 21 then 1 end) young, " +
                        "count(case when datediff(year,c.birthday,getdate()) between 22 and 35 then 1 end) middle_I, " +
                        "count(case when datediff(year,c.birthday,getdate()) between 36 and 60 then 1 end) middle_II, " +
                        "count(case when datediff(year,c.birthday,getdate()) between 61 and 75 then 1 end) advanced, " +
                        "count(case when datediff(year,c.birthday,getdate()) between 76 and 90 then 1 end) senium, " +
                        "count(case when datediff(year,c.birthday,getdate()) > 90 then 1 end) long_liver " +
                        "from client c " +
                        "join loan l on l.client_id=c.client_id " +
                        "where datepart(year,l.init_date)=:year " +
                        "and c.gender='man' " +
                        "union " +
                        "--for women \n" +
                        "select count(*) summary, " +
                        "count(case when datediff(year,c.birthday,getdate()) between 16 and 20 then 1 end) young, " +
                        "count(case when datediff(year,c.birthday,getdate()) between 21 and 35 then 1 end) middle_I, " +
                        "count(case when datediff(year,c.birthday,getdate()) between 36 and 55 then 1 end) middle_II, " +
                        "count(case when datediff(year,c.birthday,getdate()) between 56 and 75 then 1 end) advanced, " +
                        "count(case when datediff(year,c.birthday,getdate()) between 76 and 90 then 1 end) senium, " +
                        "count(case when datediff(year,c.birthday,getdate()) > 90 then 1 end) long_liver " +
                        "from client c " +
                        "join loan l on l.client_id=c.client_id " +
                        "where datepart(year,l.init_date)=:year " +
                        "and c.gender='woman' " +
                        ") as ages " +
                        ") as grouped ";
        Map <String,Integer> namedParameters = new LinkedHashMap<>();
        namedParameters.put("year",year);
        String data = jdbcTemplate.queryForObject(query, namedParameters, String.class);

        if(data!= null && data.length()>0)
            return data;
        return null;
    }
}
