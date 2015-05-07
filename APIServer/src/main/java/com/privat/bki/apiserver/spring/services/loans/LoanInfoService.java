package com.privat.bki.apiserver.spring.services.loans;

import com.privat.bki.business.entities.Bank;
import com.privat.bki.business.entities.Currency;
import com.privat.bki.business.entities.LoanInfo;
import com.privat.bki.business.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Created by sting on 3/1/15.
 */

@Service
public class LoanInfoService {

    @Qualifier("loanInfoDao")
    @Autowired
    private LoanInfoDao dao;

    public List<LoanInfo> getRecord(int id) {
        return dao.getRecord(id);
    }

    public List<LoanInfo> listAll() {
        return dao.getAllRecords();
    }

    public List<LoanInfo> listSpecificInfo(Person person) {
        return dao.getPersonInfo(person);
    }

    public Integer isClientExists(Person person) {
        return dao.isClientExists(person);
    }

    public Boolean addInfo(LoanInfo info, int clientId) {
        return dao.addNewInfo(info, clientId);
    }

    public Boolean addNewClient(LoanInfo info) {
        return dao.addNewClient(info);
    }

    public Boolean modify(int loanId, int clientId, LoanInfo newInfo) {
        return dao.changeInfo(loanId, clientId, newInfo);
    }

    public Map<String, String> getBanksMap() {
        return dao.getBanksMap();
    }

    public Map<String, String> getCurrenciesMap() {
        return dao.getCurrenciesMap();
    }

    public Boolean addBank(Bank bank) {
        return dao.addNewBank(bank);
    }

    public Boolean addCurrency(Currency currency) {
        return dao.addNewCurrency(currency);
    }

}
