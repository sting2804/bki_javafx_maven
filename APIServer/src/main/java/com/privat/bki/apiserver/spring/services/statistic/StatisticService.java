package com.privat.bki.apiserver.spring.services.statistic;

import com.privat.bki.business.entities.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Service
public class StatisticService {
    @Qualifier("statisticDao")
    @Autowired
    StatisticDao dao;
    @Autowired
    RegressionAnalysis ra;

    public List<Map> getTheMostPreferredBank(int[] years) {
        return dao.theMostPreferredBank(years);
    }

    public Bank getTheMostPreferredBank(int year) {
        return dao.theMostPreferredBank(year);
    }

    private List<Map> getTheMostCreditAge(String bankName, int ... years) {
        return dao.theMostCreditAge(bankName, years);
    }

    public String getTheMostCreditAge(int year, String bankName) {
        return dao.theMostCreditAge(bankName, year);
    }

    public Map<String, List> getStatisticByBankAndYears(String bankName, Integer ... years){
        return dao.bankCreditStatisticByYears(bankName, years);
    }

    public Map<String, List> getStatisticByBankAndYears(String bankName){
        return dao.bankCreditStatisticByYears(bankName);
    }

    public double calculatePrognosticationForBank(Map<String,List> statistics, String bankName, int prognosticationYear){
        return ra.calculatePrognosticationForBank(statistics, bankName, prognosticationYear);
    }

    public String calculatePrognosticationForClientAgesByBank(Map<String,List> statistics, String bankName, int prognosticationYear){
        return ra.calculatePrognosticationForClientAgesByBank(statistics, bankName, prognosticationYear);
    }

    /**
     * получить список годов, на которые в банке брали кредиты
     * @param bankName название банка
     * @return список годов
     */
    public List<Integer> getCreditYearsOfBank(String bankName){
        return dao.getCreditYearsOfBank(bankName);
    }

    public Map<String,List> getStatisticOfClientAgesByBank(String bankName) {
        List<Integer> years = getCreditYearsOfBank(bankName);
        List theMostCreditAges = getTheMostCreditAge(bankName, listToIntArray(years));
        Map<String, List> clientAgesByYears = new LinkedHashMap<>();
        clientAgesByYears.put(bankName, theMostCreditAges);
        return clientAgesByYears;
    }

    private int[] listToIntArray(List<Integer> integerList){
        int size = integerList.size();
        int [] intArray = new int[size];
        int i=0;
        for (Integer val : integerList){
            intArray[i++] = val;
        }
        return intArray;
    }
}
