package com.privat.bki.apiserver.spring.services.statistic;

import com.privat.bki.business.entities.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by sting on 5/6/15.
 */
@Component
@Service
public class StatisticService {
    @Qualifier("statisticDao")
    @Autowired
    StatisticDao dao;

    public Map getTheMostPreferredBank(int[] years) {
        return dao.theMostPreferredBank(years);
    }

    public Bank getTheMostPreferredBank(int year) {
        return dao.theMostPreferredBank(year);
    }

    public Map<Integer, Map<String,Integer>> getTheMostCreditAge(int[] years) {
        return dao.theMostCreditAge(years);
    }

    public Map<String,Integer> getTheMostCreditAge(int year) {
        return dao.theMostCreditAge(year);
    }
}
