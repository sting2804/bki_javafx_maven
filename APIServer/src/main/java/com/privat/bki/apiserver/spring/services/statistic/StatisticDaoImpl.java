package com.privat.bki.apiserver.spring.services.statistic;

import com.privat.bki.business.entities.Bank;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sting on 5/6/15.
 */
@Component("statisticDao")
public class StatisticDaoImpl implements StatisticDao {

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
        return null;
    }

    @Override
    public Map<String, Integer> theMostCreditAge(int... years) {
        return null;
    }

    @Override
    public Integer theMostCreditAge(int year) {
        return null;
    }
}
