/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.privat.bki.desktopapp.utils;
import com.privat.bki.business.entities.Bank;
import com.privat.bki.business.entities.Currency;
import com.privat.bki.business.entities.LoanInfo;
import com.privat.bki.business.entities.Person;
import com.privat.bki.business.wrappers.BankMapWrapper;
import com.privat.bki.business.wrappers.CurrencyMapWrapper;
import com.privat.bki.business.wrappers.LoanInfoListWrapper;
import com.privat.bki.business.wrappers.StatisticWrapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author user
 */
@Service
public class DaoRestTemplateService {

    public RestTemplate restTemplate;
    private HttpHeaders headers;
    private LoanInfoListWrapper data;
    private static final String baseUrl = "http://localhost:8190/api";
    private static final String statisticUrl = "http://localhost:8190/api/statistic";

    public DaoRestTemplateService() {
    }

    @Autowired
    public DaoRestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean restAuthenticate(String username, String password){
        ResponseEntity response = doAuthenticate(username, password);
        return response != null && response.getStatusCode().value() == 302;
    }

    private HttpHeaders createHeaders(String username, String password ){
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        HttpHeaders headers =  new HttpHeaders(){
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(
                        auth.getBytes(Charset.forName("US-ASCII")) );
                String authHeader = "Basic " + new String( encodedAuth );
                set( "Authorization", authHeader );
            }
        };
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes);
        this.headers=headers;
        return headers;
    }

    private ResponseEntity doAuthenticate(String username, String password){
        try {
            HttpHeaders headers = createHeaders(username,password);
            HttpEntity request = new HttpEntity(headers);
            return restTemplate.exchange("http://localhost:8190/api/loans",
                    HttpMethod.POST, request, LoanInfoListWrapper.class);
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
                    baseUrl + "/info/{id}",
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
//            HttpEntity<String> entity = new HttpEntity<>();
//            HttpEntity<String> entity = new HttpEntity<>(headers);
            /*ResponseEntity<LoanInfoListWrapper> response = restTemplate.exchange(
                    baseUrl + "/all", HttpMethod.GET, entity,
                    LoanInfoListWrapper.class);*/
            ResponseEntity<LoanInfoListWrapper> response = restTemplate.getForEntity(
                    baseUrl+"/loans", LoanInfoListWrapper.class);
            data = response.getBody();
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
                    baseUrl + "/loans/byClient", client, LoanInfoListWrapper.class);
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
                    baseUrl + "/loans/isExists", client, Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Map<String, String> getBanksMap() {
        BankMapWrapper map = null;
        try {
            ResponseEntity<BankMapWrapper> response = restTemplate.getForEntity(
                    baseUrl + "/banks",
                    BankMapWrapper.class);
            map = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map != null ? map.getBanks() : null;
    }

    public Map<String, String> getCurrenciesMap() {
        CurrencyMapWrapper map = null;
        try {
            ResponseEntity<CurrencyMapWrapper> response = restTemplate.getForEntity(
                    baseUrl + "/currencies",
                    CurrencyMapWrapper.class);
            map = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map != null ? map.getCurrencies() : null;
    }

    public boolean addInfo(LoanInfo info, int clientId) {
        Boolean res = false;
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/loans/byClient/{clientId}", info, Boolean.class, clientId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Boolean addNewClient(LoanInfo info) {
        Boolean res = false;
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/client", info, Boolean.class);
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
        try {
            HttpEntity<LoanInfo> info = new HttpEntity<>(newInfo);
            ResponseEntity <Boolean> res = restTemplate.exchange(
                    baseUrl + "/loans/{loanId}?clientId={clientId}", HttpMethod.PUT, info, Boolean.class, loanId, clientId);
            return res.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addBank(Bank bank) {
        Boolean res = false;
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/banks", bank, Boolean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean addCurrency(Currency currency) {
        Boolean res = false;
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/currencies", currency, Boolean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Map<String, List> getStatisticForBank(String bankName){
        Map<String, List> res;
        try{
            ParameterizedTypeReference<Map<String,List>> responseType = new ParameterizedTypeReference<Map<String,List>>() {
            };
            ResponseEntity<Map<String,List>> response = restTemplate.exchange(
                    statisticUrl + "/forBank?bankName={bankName}", HttpMethod.GET, null,
                    responseType, bankName);
            res = response.getBody();
        } catch (Exception ex){
            ex.printStackTrace();
            res = null;
        }
        return res;
    }

    public Double getPrognosticationForBankAndYear(String bankName, Integer prognosticationYear) {
        Double res;
        try{
            ResponseEntity<Double> response = restTemplate.getForEntity(
                    statisticUrl + "/prognos/forBank?bankName={bankName}&prognosticationYear={prognosticationYear}", Double.class, bankName, prognosticationYear);
            res = response.getBody();
        } catch (Exception ex){
            ex.printStackTrace();
            res = null;
        }
        return res;
    }
}
