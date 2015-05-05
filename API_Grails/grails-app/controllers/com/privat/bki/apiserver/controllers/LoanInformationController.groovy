package com.privat.bki.apiserver.controllers

import com.privat.bki.business.entities.Person
import com.privat.bki.business.wrappers.LoanInfoListWrapper
import grails.converters.JSON
import org.springframework.web.bind.annotation.RequestBody

class LoanInformationController {

    //@Autowired
    def loanDaoService

    //@RequestMapping("/index")
    public String callHello() {
        return ;
    }

    //@RequestMapping(value = "/select/all", method = GET)
    //@ResponseBody
    public def selectAll() {
        loanDaoService.listAll() as JSON
    }

    //@RequestMapping(value = "/select/client", method = POST)
    //@ResponseBody
    def selectClient() {
        new Date() as JSON
    }

/*    //@RequestMapping(value = "/select/info/{id}", method = GET)
    //@ResponseBody
    public LoanInfoListWrapper selectInfoById(int id) {
        return new LoanInfoListWrapper(loanDaoService.getRecord(id));
    }

    //@RequestMapping(value = "/select/is-exists", method = POST)
    //@ResponseBody
    public Integer isClientExists(Person client) {
        return loanDaoService.isClientExists(client);
    }

    //@RequestMapping(value = "/insert/info/{clientId}", method = POST)
    //@ResponseBody
    public Boolean insertInfo( LoanInfo info, int clientId) {
        return loanDaoService.addInfo(info, clientId);
    }

    //@RequestMapping(value = "/insert/client", method = POST)
    //@ResponseBody
    public boolean insertClient( LoanInfo info) {
        return loanDaoService.addNewClient(info);
    }

    //@RequestMapping(value = "/update/info/{loanId}/{clientId}", method = POST)
    //@ResponseBody
    public boolean update(int loanId, int clientId, newInfo) {
        return loanDaoService.modify(loanId, clientId, newInfo);
    }

    //@RequestMapping(value = "/select/banks", method = GET)
    //@ResponseBody
    public BankMapWrapper selectBanks() {
        return new BankMapWrapper(loanDaoService.getBanksMap());
    }

    //@RequestMapping(value = "/select/currencies", method = GET)
    //@ResponseBody
    public CurrencyMapWrapper selectCurrencies() {
        return new CurrencyMapWrapper(loanDaoService.getCurrenciesMap());
    }

    //@RequestMapping(value = "/insert/bank", method = POST)
    //@ResponseBody
    public boolean insertBank( Bank bank) {
        return loanDaoService.addBank(bank);
    }

    //@RequestMapping(value = "/insert/currency", method = POST)
    //@ResponseBody
    public boolean insertCurrency( Currency currency) {
        return loanDaoService.addCurrency(currency);
    }*/
}
