package com.privat.bki.apiserver.spring.services.statistic;

import com.privat.bki.business.entities.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Map> getTheMostPreferredBank(int[] years) {
        return dao.theMostPreferredBank(years);
    }

    public Bank getTheMostPreferredBank(int year) {
        return dao.theMostPreferredBank(year);
    }

    public List<Map> getTheMostCreditAge(int[] years) {
        return dao.theMostCreditAge(years);
    }

    public String getTheMostCreditAge(int year) {
        return dao.theMostCreditAge(year);
    }
}
