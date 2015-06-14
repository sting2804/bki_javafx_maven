package com.privat.bki.apiserver.spring.services.statistic;

import com.privat.bki.apiserver.mappers.PreferredBankRowMapper;
import com.privat.bki.business.entities.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

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
        Map<String, Object> banks;
        for (int year : years) {
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
                "SELECT DISTINCT count(*) cnt, b.bank_code, b.bank_name " +
                        "FROM bank b " +
                        "JOIN loan l ON b.bank_code=l.bank_code " +
                        "WHERE datepart(YEAR,l.init_date)=:year " +
                        "GROUP BY b.bank_name " +
                        "HAVING count(*)=(" +
                        "SELECT max(count(*)) mx " +
                        "FROM loan l2 " +
                        "JOIN bank b2 ON b2.bank_code = l2.bank_code " +
                        "WHERE DATEPART(YEAR,l2.init_date)=:year " +
                        "GROUP BY b2.bank_code" +
                        ")";
        Map<String, Integer> params = new LinkedHashMap<>();
        params.put("year", year);
        data = jdbcTemplate.query(query, params, new PreferredBankRowMapper());
        if (data != null && data.size() > 0)
            return (Bank) data.get(0);
        return null;
    }

    @Override
    public List<Map> theMostCreditAge(String bankName, int... years) {
        List<Map> ages = new ArrayList<>();
        Map age;
        for (int year : years) {
            age = new LinkedHashMap<>();
            age.put("year", year);
            age.put("age", theMostCreditAge(bankName, year));
            ages.add(age);
        }
        return ages;
    }

    @Override
    public String theMostCreditAge(String bankName, int year) {
        String query =
                "SELECT (" +
                        "CASE WHEN young=0 AND middle_I=0 AND middle_II=0 AND advanced=0 AND senium=0 AND long_liver=0 THEN 'neither' " +
                        "       WHEN young>=middle_I AND young>=middle_II AND young>=advanced AND young>=senium AND young>=long_liver THEN 'young' " +
                        "       WHEN middle_I>young AND middle_I>=middle_II AND middle_I>=advanced AND middle_I>=senium AND middle_I>=long_liver THEN 'middle_I' " +
                        "       WHEN middle_II>young AND middle_II>middle_I AND middle_II>=advanced AND middle_II>=senium AND middle_II>=long_liver THEN 'middle_II' " +
                        "       WHEN advanced>young AND advanced>middle_I AND advanced>middle_II AND advanced>=senium AND advanced>=long_liver THEN 'advanced' " +
                        "       WHEN senium>young AND senium>middle_I AND senium>middle_II AND senium>advanced AND senium>=long_liver THEN 'senium' " +
                        "       WHEN long_liver>young AND long_liver>middle_I AND long_liver>middle_II AND long_liver>advanced AND long_liver>senium THEN 'long_liver' " +
                        "       END) greatest " +
                    "FROM ( " +
                    "--calculate the summary of loans of men and women by ages \n" +
                    "SELECT " +
                    "sum(summary)summary, " +
                    "sum(young)young, " +
                    "sum(middle_I)middle_I, " +
                    "sum(middle_II)middle_II, " +
                    "sum(advanced)advanced, " +
                    "sum(senium)senium, " +
                    "sum(long_liver)long_liver " +
                    "FROM(" +
                        "--for men \n" +
                        "SELECT count(*) summary, " +
                        "count(CASE WHEN datediff(YEAR,c.birthday,getdate()) BETWEEN 17 AND 21 THEN 1 END) young, " +
                        "count(CASE WHEN datediff(YEAR,c.birthday,getdate()) BETWEEN 22 AND 35 THEN 1 END) middle_I, " +
                        "count(CASE WHEN datediff(YEAR,c.birthday,getdate()) BETWEEN 36 AND 60 THEN 1 END) middle_II, " +
                        "count(CASE WHEN datediff(YEAR,c.birthday,getdate()) BETWEEN 61 AND 75 THEN 1 END) advanced, " +
                        "count(CASE WHEN datediff(YEAR,c.birthday,getdate()) BETWEEN 76 AND 90 THEN 1 END) senium, " +
                        "count(CASE WHEN datediff(YEAR,c.birthday,getdate()) > 90 THEN 1 END) long_liver " +
                        "FROM client c " +
                        "JOIN loan l ON l.client_id=c.client_id " +
                        "join bank b on b.bank_code = l.bank_code "+
                        "WHERE datepart(YEAR,l.init_date)=:year " +
                        "AND c.gender='male' " +
                        "and b.bank_name=:bankName " +

                        "UNION " +

                        "--for women \n" +
                        "SELECT count(*) summary, " +
                        "count(CASE WHEN datediff(YEAR,c.birthday,getdate()) BETWEEN 16 AND 20 THEN 1 END) young, " +
                        "count(CASE WHEN datediff(YEAR,c.birthday,getdate()) BETWEEN 21 AND 35 THEN 1 END) middle_I, " +
                        "count(CASE WHEN datediff(YEAR,c.birthday,getdate()) BETWEEN 36 AND 55 THEN 1 END) middle_II, " +
                        "count(CASE WHEN datediff(YEAR,c.birthday,getdate()) BETWEEN 56 AND 75 THEN 1 END) advanced, " +
                        "count(CASE WHEN datediff(YEAR,c.birthday,getdate()) BETWEEN 76 AND 90 THEN 1 END) senium, " +
                        "count(CASE WHEN datediff(YEAR,c.birthday,getdate()) > 90 THEN 1 END) long_liver " +
                        "FROM client c " +
                        "JOIN loan l ON l.client_id=c.client_id " +
                        "join bank b on b.bank_code = l.bank_code "+
                        "WHERE datepart(YEAR,l.init_date)=:year " +
                        "AND c.gender='female' " +
                        "and b.bank_name=:bankName " +
                        ") AS ages " +
                    ") AS grouped ";
        Map namedParameters = new LinkedHashMap<>();
        namedParameters.put("year", year);
        namedParameters.put("bankName", bankName);
        String data = jdbcTemplate.queryForObject(query, namedParameters, String.class);

        if (data != null && data.length() > 0)
            return data;
        return null;
    }

    @Override
    public Map<String, List> bankCreditStatisticByYears(String bankName, Integer... years) {
        Map<String, List> bankCreditStatistic = new LinkedHashMap<>();
        List<Map> bankStatisticList = new ArrayList<>();
        Map<String,Integer> statistic;
        for (int year : years) {
            statistic = new LinkedHashMap<>();
            statistic.put("year", year);
            statistic.put("count", bankCreditStatisticByYear(bankName, year));
            bankStatisticList.add(statistic);
        }
        bankCreditStatistic.put(bankName, bankStatisticList);
        return bankCreditStatistic;
    }

    @Override
    public Map<String, List> bankCreditStatisticByYears(String bankName) {
        Map<String, List> bankCreditStatistic = new LinkedHashMap<>();
        List<Map> bankStatisticList = new ArrayList<>();
        Map<String,Integer> statistic;
        List<Integer> years = getCreditYearsOfBank(bankName);
        Collections.sort(years);
        for (int year : years) {
            statistic = new LinkedHashMap<>();
            statistic.put("year", year);
            statistic.put("count", bankCreditStatisticByYear(bankName, year));
            bankStatisticList.add(statistic);
        }
        bankCreditStatistic.put(bankName, bankStatisticList);
        return bankCreditStatistic;
    }

    @Override
    public Integer bankCreditStatisticByYear(String bankName, Integer year) {
        String query =
                "SELECT count(*) cnt " +
                        "FROM loan l " +
                        "RIGHT JOIN bank b ON b.bank_code = l.bank_code " +
                        "WHERE datepart(YEAR, l.init_date) = :year " +
                        "AND b.bank_name = :bankName " +
                        "GROUP BY datepart(YEAR,l.init_date)";
        Map params = new LinkedHashMap<>();
        params.put("year", year);
        params.put("bankName", bankName);
        Integer data;
        try{
            data = jdbcTemplate.queryForObject(query, params, Integer.class);
        } catch (EmptyResultDataAccessException e){
            data = 0;
        }
        return data;
    }

    @Override
    public List<Integer> getCreditYearsOfBank(String bankName) {
        String query =
                "SELECT DISTINCT datepart(YEAR,l.init_date) year " +
                        "FROM loan l " +
                        "RIGHT JOIN bank b ON b.bank_code = l.bank_code " +
                        "WHERE b.bank_name = :bankName " +
                        "GROUP BY datepart(YEAR,l.init_date)";
        Map params = new LinkedHashMap<>();
        params.put("bankName", bankName);

        List <Integer> data;
        try{
            data = jdbcTemplate.query(query, params, (rs, rowNum) -> {
                return rs.getInt(1);
            });

        } catch (EmptyResultDataAccessException e){
            data = new ArrayList<>();
        }
        return data;
    }

}
