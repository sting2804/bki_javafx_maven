/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.privat.bki.apiserver.spring.controllers;

import com.privat.bki.apiserver.spring.services.loans.LoanInfoService;
import com.privat.bki.business.entities.Bank;
import com.privat.bki.business.entities.Currency;
import com.privat.bki.business.entities.LoanInfo;
import com.privat.bki.business.entities.Person;
import com.privat.bki.business.wrappers.BankMapWrapper;
import com.privat.bki.business.wrappers.CurrencyMapWrapper;
import com.privat.bki.business.wrappers.LoanInfoListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author sting
 */
@Controller
@RequestMapping
public class MainController {

    @Autowired
    LoanInfoService loanInfoService;

    @RequestMapping("/index")
    public String callHello() {
        return "index";
    }

    @RequestMapping(value = "/loans", method = GET)
    @ResponseBody
    public LoanInfoListWrapper selectAll() {
        return new LoanInfoListWrapper(loanInfoService.listAll());
    }

    @RequestMapping(value = "/loans/byClient", method = POST)
    @ResponseBody
    public LoanInfoListWrapper selectClient(@RequestBody Person client) {
        return new LoanInfoListWrapper(loanInfoService.listSpecificInfo(client));
    }

    @RequestMapping(value = "/loans/{id}", method = GET)
    @ResponseBody
    public LoanInfoListWrapper selectInfoById(@PathVariable("id") int id) {
        return new LoanInfoListWrapper(loanInfoService.getRecord(id));
    }

    @RequestMapping(value = "/loans/isExists", method = POST)
    @ResponseBody
    public Integer isClientExists(@RequestBody Person client) {
        return loanInfoService.isClientExists(client);
    }

    @RequestMapping(value = "/loans/byClient/{clientId}", method = POST)
    @ResponseBody
    public Boolean insertInfo(@RequestBody LoanInfo info, @PathVariable("clientId") int clientId) {
        return loanInfoService.addInfo(info, clientId);
    }

    @RequestMapping(value = "/client", method = POST)
    @ResponseBody
    public boolean insertClient(@RequestBody LoanInfo info) {
        return loanInfoService.addNewClient(info);
    }

    @RequestMapping(value = "/loans/{loanId}", method = PUT)
    @ResponseBody
    public boolean update(@PathVariable("loanId") int loanId, @RequestParam("clientId") int clientId,  @RequestBody LoanInfo newInfo) {
        return loanInfoService.modify(loanId, clientId, newInfo);
    }

    @RequestMapping(value = "/banks", method = GET)
    @ResponseBody
    public BankMapWrapper selectBanks() {
        return new BankMapWrapper(loanInfoService.getBanksMap());
    }

    @RequestMapping(value = "/currencies", method = GET)
    @ResponseBody
    public CurrencyMapWrapper selectCurrencies() {
        return new CurrencyMapWrapper(loanInfoService.getCurrenciesMap());
    }

    @RequestMapping(value = "/banks", method = POST)
    @ResponseBody
    public boolean insertBank(@RequestBody Bank bank) {
        return loanInfoService.addBank(bank);
    }

    @RequestMapping(value = "/currencies", method = POST)
    @ResponseBody
    public boolean insertCurrency(@RequestBody Currency currency) {
        return loanInfoService.addCurrency(currency);
    }

}