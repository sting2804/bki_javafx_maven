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
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author user
 */
@Service
public class DaoRestTemplateService {



    RestTemplate restTemplate;
    HttpHeaders headers;
    private LoanInfoListWrapper data;
    private static final String baseUrl = "http://localhost:8181/loans";

    public DaoRestTemplateService() {
    }

    @Autowired
    public DaoRestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean restAuthenticate(String username, String password){
        ResponseEntity response = doAuthenticate(username, password);
        if(response !=null && response.getStatusCode().value()==302)
            return true;
        return false;
    }

    private HttpHeaders createHeaders(String username, String password ){
        HttpHeaders headers =  new HttpHeaders(){
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(
                        auth.getBytes(Charset.forName("US-ASCII")) );
                String authHeader = "Basic " + new String( encodedAuth );
                set( "Authorization", authHeader );
            }
        };
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");

        this.headers=headers;

        return headers;
    }

    private ResponseEntity doAuthenticate(String username, String password){
        try {
            //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HttpHeaders headers = createHeaders(username,password);
            HttpEntity request = new HttpEntity(headers);
            ResponseEntity<LoanInfoListWrapper> response = restTemplate.exchange("http://localhost:8181/loans",
                    HttpMethod.POST, request, LoanInfoListWrapper.class);
            return response;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<LoanInfo> getRecord(int id) {
        if (data != null)
            data.setClients(null);
        try {
            ResponseEntity<LoanInfoListWrapper> response = restTemplate.getForEntity(
                    baseUrl + "/get/info/{id}",
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

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<LoanInfoListWrapper> response = restTemplate.exchange(
                    baseUrl + "/get/all", HttpMethod.GET, entity,
                    LoanInfoListWrapper.class);
            data = response.getBody();
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return data.getClients();
    }

    public List<LoanInfo> listSpecificInfo(Person person) {
        if (data != null)
            data.setClients(null);
        try {
            HttpEntity<Person> client = new HttpEntity<>(person);
            ResponseEntity<LoanInfoListWrapper> response = restTemplate.postForEntity(
                    baseUrl + "/get/client", client, LoanInfoListWrapper.class);
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
                    baseUrl + "/get/is-exists", client, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Map<String, String> getBanksMap() {
        BankMapWrapper map = null;
        try {
            ResponseEntity<BankMapWrapper> response = restTemplate.getForEntity(
                    baseUrl + "/get/banks",
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
                    baseUrl + "/get/currencies",
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
                    baseUrl + "/new/info/{clientId}", info, Boolean.class, clientId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Boolean addNewClient(LoanInfo info) {
        Boolean res = false;
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/new/client", info, Boolean.class);
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
                    baseUrl + "/new/bank", bank, Boolean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean addCurrency(Currency currency) {
        Boolean res = false;
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/new/currency", currency, Boolean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
