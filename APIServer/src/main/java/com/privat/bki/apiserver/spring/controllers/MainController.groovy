package com.privat.bki.apiserver.spring.controllers

import com.privat.bki.apiserver.spring.beans.DaoService
import com.privat.bki.entities.Bank
import com.privat.bki.entities.Currency
import com.privat.bki.entities.LoanInfo
import com.privat.bki.entities.Person
import com.privat.bki.wrappers.BankMapWrapper
import com.privat.bki.wrappers.CurrencyMapWrapper
import com.privat.bki.wrappers.LoanInfoListWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

import static org.springframework.web.bind.annotation.RequestMethod.*

/**
 * @author sting
 */
@Controller
@RequestMapping("/loans")
class MainController {

    @Autowired
    DaoService daoService

    @RequestMapping("/index")
    String callHello() {
        return "index"
    }

    @RequestMapping(value = "/get/all", method = GET)
    @ResponseBody
    LoanInfoListWrapper selectAll() {
        return new LoanInfoListWrapper(daoService.listAll())
    }

    @RequestMapping(value = "/get/client", method = POST)
    @ResponseBody
    LoanInfoListWrapper selectClient(@RequestBody Person client) {
        return new LoanInfoListWrapper(daoService.listSpecificInfo(client))
    }

    @RequestMapping(value = "/get/by/{id}", method = GET)
    @ResponseBody
    LoanInfoListWrapper selectInfoById(@PathVariable("id") int id) {
        return new LoanInfoListWrapper(daoService.getRecord(id))
    }

    @RequestMapping(value = "/get/is-exists", method = POST)
    @ResponseBody
    Integer isClientExists(@RequestBody Person client) {
        return daoService.isClientExists(client)
    }

    @RequestMapping(value = "/new/info/{clientId}", method = POST)
    @ResponseBody
    Boolean insertInfo(@RequestBody LoanInfo info, @PathVariable("clientId") int clientId) {
        return daoService.addInfo(info, clientId)
    }

    @RequestMapping(value = "/new/client", method = POST)
    @ResponseBody
    boolean insertClient(@RequestBody LoanInfo info) {
        return daoService.addNewClient(info)
    }

    @RequestMapping(value = "/update/info/{loanId}/{clientId}", method = POST)
    @ResponseBody
    boolean update(@PathVariable("loanId") int loanId, @PathVariable("clientId") int clientId,  @RequestBody LoanInfo newInfo) {
        return daoService.modify(loanId, clientId, newInfo)
    }

    @RequestMapping(value = "/get/banks", method = GET)
    @ResponseBody
    BankMapWrapper selectBanks() {
        return new BankMapWrapper(daoService.getBanksMap())
    }

    @RequestMapping(value = "/get/currencies", method = GET)
    @ResponseBody
    CurrencyMapWrapper selectCurrencies() {
        return new CurrencyMapWrapper(daoService.getCurrenciesMap())
    }

    @RequestMapping(value = "/new/bank", method = POST)
    @ResponseBody
    boolean insertBank(@RequestBody Bank bank) {
        return daoService.addBank(bank)
    }

    @RequestMapping(value = "/new/currency", method = POST)
    @ResponseBody
    boolean insertCurrency(@RequestBody Currency currency) {
        return daoService.addCurrency(currency)
    }

}
