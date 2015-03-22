/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.privat.bki.desktopapp.utils;

import com.privat.bki.entities.Bank;
import com.privat.bki.entities.Currency;
import com.privat.bki.entities.LoanInfo;
import com.privat.bki.entities.Person;
import com.privat.bki.wrappers.BankMapWrapper;
import com.privat.bki.wrappers.CurrencyMapWrapper;
import com.privat.bki.wrappers.LoanInfoListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author user
 */
@Service
public class DaoRestTemplateService {

    RestTemplate restTemplate;
    private LoanInfoListWrapper data;
    private static final String baseUrl = "http://localhost:8181/";

    public DaoRestTemplateService() {
    }
/*
    public static RestTemplate getRestTemplate() {
        return context.getBean(RestTemplate.class);
    }*/
    @Autowired
    public DaoRestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<LoanInfo> getRecord(int id) {
        if (data != null)
            data.setClients(null);
        try {
            ResponseEntity<LoanInfoListWrapper> response = restTemplate.getForEntity(
                    baseUrl + "select/info/{id}",
                    LoanInfoListWrapper.class, id);
            data = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.getClients();
    }


    public List<LoanInfo> listAll() {
        if (data != null)
            data.setClients(null);
        try {
            ResponseEntity<LoanInfoListWrapper> response = restTemplate.getForEntity(
                    baseUrl + "select/all",
                    LoanInfoListWrapper.class);
            data = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.getClients();
    }

    public List<LoanInfo> listSpecificInfo(Person person) {
        if (data != null)
            data.setClients(null);
        try {
            HttpEntity<Person> client = new HttpEntity<>(person);
            ResponseEntity<LoanInfoListWrapper> response = restTemplate.postForEntity(
                    baseUrl + "/select/client", client, LoanInfoListWrapper.class);
            data = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.getClients();
    }

    public int isClientExists(Person person) {
        int res = -1;
        try {
            HttpEntity<Person> client = new HttpEntity<>(person);
            res = restTemplate.postForObject(
                    baseUrl + "/select/is-exists", client, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Map<String, String> getBanksMap() {
        BankMapWrapper map = null;
        try {
            ResponseEntity<BankMapWrapper> response = restTemplate.getForEntity(
                    baseUrl + "select/banks",
                    BankMapWrapper.class);
            map = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map.getBanks();
    }

    public Map<String, String> getCurrenciesMap() {
        CurrencyMapWrapper map = null;
        try {
            ResponseEntity<CurrencyMapWrapper> response = restTemplate.getForEntity(
                    baseUrl + "select/currencies",
                    CurrencyMapWrapper.class);
            map = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map.getCurrencies();
    }

    public boolean addInfo(LoanInfo info, int clientId) {
        Boolean res = false;
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/insert/info/{clientId}", info, Boolean.class, clientId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Boolean addNewClient(LoanInfo info) {
        Boolean res = false;
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/insert/client", info, Boolean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean modify(LoanInfo oldInfo, LoanInfo newInfo) {
        int loanId = oldInfo.getId();
        int clientId = oldInfo.getPerson().getId();
        return modify(loanId, clientId, newInfo);
    }

    public boolean modify(int loanId, int clientId, LoanInfo newInfo) {
        Boolean res = false;
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/update/info/{loanId}/{clientId}", newInfo, Boolean.class, loanId, clientId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean addBank(Bank bank) {
        Boolean res = false;
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/insert/bank", bank, Boolean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean addCurrency(Currency currency) {
        Boolean res = false;
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/insert/currency", currency, Boolean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
