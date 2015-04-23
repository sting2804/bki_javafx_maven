/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.privat.bki.desktopapp.utils
import com.privat.bki.business.entities.Bank
import com.privat.bki.business.entities.Currency
import com.privat.bki.business.entities.LoanInfo
import com.privat.bki.business.entities.Person
import com.privat.bki.business.wrappers.BankMapWrapper
import com.privat.bki.business.wrappers.CurrencyMapWrapper
import com.privat.bki.business.wrappers.LoanInfoListWrapper
import org.apache.commons.codec.binary.Base64
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

import java.nio.charset.Charset
/**
 * @author user
 */
@Service
class DaoRestTemplateService {



    RestTemplate restTemplate
    HttpHeaders headers
    private LoanInfoListWrapper data
    private static final String baseUrl = "http://localhost:8181/loans"

    DaoRestTemplateService() {
    }

    @Autowired
    DaoRestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    boolean restAuthenticate(String username, String password){
        ResponseEntity response = doAuthenticate(username, password)
        if(response !=null && response.getStatusCode().value()==302)
            return true
        return false
    }

    private HttpHeaders createHeaders(String username, String password ){
        List<MediaType> acceptableMediaTypes = new ArrayList<>()
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON)
        HttpHeaders headers =  new HttpHeaders(){
            {
                String auth = username + ":" + password
                byte[] encodedAuth = Base64.encodeBase64(
                        auth.getBytes(Charset.forName("US-ASCII")) )
                String authHeader = "Basic " + new String( encodedAuth )
                set( "Authorization", authHeader )
            }
        }
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.setAccept(acceptableMediaTypes)
        this.headers=headers
        return headers
    }

    private ResponseEntity doAuthenticate(String username, String password){
        try {
            HttpHeaders headers = createHeaders(username,password)
            HttpEntity request = new HttpEntity(headers)
            return restTemplate.exchange("http://localhost:8181/loans",
                    HttpMethod.POST, request, LoanInfoListWrapper.class)
        }
        catch (Exception e){
            e.printStackTrace()
            return null
        }
    }

    List<LoanInfo> getRecord(int id) {
        if (data != null)
            data.setClients(null)
        try {
            ResponseEntity<LoanInfoListWrapper> response = restTemplate.getForEntity(
                    baseUrl + "/get/info/{id}",
                    LoanInfoListWrapper.class, id)
            data = response.getBody()
        } catch (Exception e) {
            e.printStackTrace()
        }
        return data.getClients()
    }


    List<LoanInfo> listAll() {
        if (data != null)
            data.setClients(null)
        try {
            List<MediaType> acceptableMediaTypes = new ArrayList<>()
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON)
            HttpHeaders headers =  new HttpHeaders()
            headers.setContentType(MediaType.APPLICATION_JSON)
            headers.setAccept(acceptableMediaTypes)
            //HttpEntity<String> entity = new HttpEntity<>(createHeaders("admin","admin"))
            HttpEntity<String> entity = new HttpEntity<>(headers)
            ResponseEntity<LoanInfoListWrapper> response = restTemplate.exchange(
                    baseUrl + "/get/all", HttpMethod.GET, entity,
                    LoanInfoListWrapper.class)
            data = response.getBody()
            System.out.println(response.toString())
        } catch (Exception e) {
            e.printStackTrace()
            return null
        }
        return data.getClients()
    }

    List<LoanInfo> listSpecificInfo(Person person) {
        if (data != null)
            data.setClients(null)
        try {
            HttpEntity<Person> client = new HttpEntity<>(person)
            ResponseEntity<LoanInfoListWrapper> response = restTemplate.postForEntity(
                    baseUrl + "/get/client", client, LoanInfoListWrapper.class)
            data = response.getBody()
        } catch (Exception e) {
            e.printStackTrace()
        }
        return data.getClients()
    }

    int isClientExists(Person person) {
        int res = -1
        try {
            HttpEntity<Person> client = new HttpEntity<>(person)
            res = restTemplate.postForObject(
                    baseUrl + "/get/is-exists", client, Integer.class)
        } catch (Exception e) {
            e.printStackTrace()
        }
        return res
    }

    Map<String, String> getBanksMap() {
        BankMapWrapper map = null
        try {
            ResponseEntity<BankMapWrapper> response = restTemplate.getForEntity(
                    baseUrl + "/get/banks",
                    BankMapWrapper.class)
            map = response.getBody()
        } catch (Exception e) {
            e.printStackTrace()
        }
        return map.getBanks()
    }

    Map<String, String> getCurrenciesMap() {
        CurrencyMapWrapper map = null
        try {
            ResponseEntity<CurrencyMapWrapper> response = restTemplate.getForEntity(
                    baseUrl + "/get/currencies",
                    CurrencyMapWrapper.class)
            map = response.getBody()
        } catch (Exception e) {
            e.printStackTrace()
        }
        return map.getCurrencies()
    }

    boolean addInfo(LoanInfo info, int clientId) {
        Boolean res = false
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/new/info/{clientId}", info, Boolean.class, clientId)
        } catch (Exception e) {
            e.printStackTrace()
        }
        return res
    }

    Boolean addNewClient(LoanInfo info) {
        Boolean res = false
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/new/client", info, Boolean.class)
        } catch (Exception e) {
            e.printStackTrace()
        }
        return res
    }

    boolean modify(LoanInfo oldInfo, LoanInfo newInfo) {
        int loanId = oldInfo.getId()
        int clientId = oldInfo.getPerson().getId()
        return modify(loanId, clientId, newInfo)
    }

    boolean modify(int loanId, int clientId, LoanInfo newInfo) {
        Boolean res = false
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/update/info/{loanId}/{clientId}", newInfo, Boolean.class, loanId, clientId)
        } catch (Exception e) {
            e.printStackTrace()
        }
        return res
    }

    boolean addBank(Bank bank) {
        Boolean res = false
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/new/bank", bank, Boolean.class)
        } catch (Exception e) {
            e.printStackTrace()
        }
        return res
    }

    boolean addCurrency(Currency currency) {
        Boolean res = false
        try {
            res = restTemplate.postForObject(
                    baseUrl + "/new/currency", currency, Boolean.class)
        } catch (Exception e) {
            e.printStackTrace()
        }
        return res
    }
}
