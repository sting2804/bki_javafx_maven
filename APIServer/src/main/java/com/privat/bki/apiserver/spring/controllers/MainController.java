/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.privat.bki.apiserver.spring.controllers;

import com.privat.bki.apiserver.spring.beans.DaoService;
import com.privat.bki.business.entities.Bank;
import com.privat.bki.business.entities.Currency;
import com.privat.bki.business.entities.LoanInfo;
import com.privat.bki.business.entities.Person;
import com.privat.bki.business.wrappers.BankMapWrapper;
import com.privat.bki.business.wrappers.CurrencyMapWrapper;
import com.privat.bki.business.wrappers.LoanInfoListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author sting
 */
@Controller
@RequestMapping(value = "/loans")
public class MainController {

    @Qualifier("daoService")
    @Autowired
    DaoService daoService;

    @RequestMapping("/index")
    public String callHello() {
        return "index";
    }

    @RequestMapping(value = "/get/all", method = GET)
    @ResponseBody
    public LoanInfoListWrapper selectAll() {
        return new LoanInfoListWrapper(daoService.listAll());
    }

    @RequestMapping(value = "/get/client", method = POST)
    @ResponseBody
    public LoanInfoListWrapper selectClient(@RequestBody Person client) {
        return new LoanInfoListWrapper(daoService.listSpecificInfo(client));
    }

    @RequestMapping(value = "/get/info/{id}", method = GET)
    @ResponseBody
    public LoanInfoListWrapper selectInfoById(@PathVariable("id") int id) {
        return new LoanInfoListWrapper(daoService.getRecord(id));
    }

    @RequestMapping(value = "/get/is-exists", method = POST)
    @ResponseBody
    public Integer isClientExists(@RequestBody Person client) {
        return daoService.isClientExists(client);
    }

    @RequestMapping(value = "/create/info/{clientId}", method = POST)
    @ResponseBody
    public Boolean insertInfo(@RequestBody LoanInfo info, @PathVariable("clientId") int clientId) {
        return daoService.addInfo(info, clientId);
    }

    @RequestMapping(value = "/create/client", method = POST)
    @ResponseBody
    public boolean insertClient(@RequestBody LoanInfo info) {
        return daoService.addNewClient(info);
    }

    @RequestMapping(value = "/update/info/{loanId}/{clientId}", method = POST)
    @ResponseBody
    public boolean update(@PathVariable("loanId") int loanId, @PathVariable("clientId") int clientId,  @RequestBody LoanInfo newInfo) {
        return daoService.modify(loanId, clientId, newInfo);
    }

    @RequestMapping(value = "/get/banks", method = GET)
    @ResponseBody
    public BankMapWrapper selectBanks() {
        return new BankMapWrapper(daoService.getBanksMap());
    }

    @RequestMapping(value = "/get/currencies", method = GET)
    @ResponseBody
    public CurrencyMapWrapper selectCurrencies() {
        return new CurrencyMapWrapper(daoService.getCurrenciesMap());
    }

    @RequestMapping(value = "/create/bank", method = POST)
    @ResponseBody
    public boolean insertBank(@RequestBody Bank bank) {
        return daoService.addBank(bank);
    }

    @RequestMapping(value = "/create/currency", method = POST)
    @ResponseBody
    public boolean insertCurrency(@RequestBody Currency currency) {
        return daoService.addCurrency(currency);
    }

}