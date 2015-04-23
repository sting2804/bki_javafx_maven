package com.privat.bki.apiserver.spring.controllers
import com.privat.bki.apiserver.spring.beans.DaoService
import com.privat.bki.business.entities.Bank
import com.privat.bki.business.entities.Currency
import com.privat.bki.business.entities.LoanInfo
import com.privat.bki.business.entities.Person
import com.privat.bki.business.wrappers.BankMapWrapper
import com.privat.bki.business.wrappers.CurrencyMapWrapper
import com.privat.bki.business.wrappers.LoanInfoListWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST

/**
 * @author sting
 */
@Controller
@RequestMapping("/loans")
class MainController {

    @Autowired
    DaoService daoService

    //@Secured (["ROLE_USER", "ROLE_ADMIN"])
    @RequestMapping(method = RequestMethod.GET)
    String callHello(Model model) {
        return "index"
    }

    //@Secured(["ROLE_USER", "ROLE_ADMIN"])
    @RequestMapping(value = "/get/all", method = GET)
    @ResponseBody
    LoanInfoListWrapper selectAll() {
        return new LoanInfoListWrapper(daoService.listAll())
    }

    //@Secured('ROLE_USER')
    @RequestMapping(value = "/get/client", method = POST)
    @ResponseBody
    LoanInfoListWrapper selectClient(@RequestBody Person client) {
        return new LoanInfoListWrapper(daoService.listSpecificInfo(client))
    }

    //@Secured('ROLE_USER')
    @RequestMapping(value = "/get/by/{id}", method = GET)
    @ResponseBody
    LoanInfoListWrapper selectInfoById(@PathVariable("id") int id) {
        return new LoanInfoListWrapper(daoService.getRecord(id))
    }

    //@Secured('ROLE_USER')
    @RequestMapping(value = "/get/is-exists", method = POST)
    @ResponseBody
    Integer isClientExists(@RequestBody Person client) {
        return daoService.isClientExists(client)
    }

    //@Secured('ROLE_USER')
    @RequestMapping(value = "/new/info/{clientId}", method = POST)
    @ResponseBody
    Boolean insertInfo(@RequestBody LoanInfo info, @PathVariable("clientId") int clientId) {
        return daoService.addInfo(info, clientId)
    }

    //@Secured('ROLE_USER')
    @RequestMapping(value = "/new/client", method = POST)
    @ResponseBody
    boolean insertClient(@RequestBody LoanInfo info) {
        return daoService.addNewClient(info)
    }

    //@Secured('ROLE_USER')
    @RequestMapping(value = "/update/info/{loanId}/{clientId}", method = POST)
    @ResponseBody
    boolean update(@PathVariable("loanId") int loanId, @PathVariable("clientId") int clientId,  @RequestBody LoanInfo newInfo) {
        return daoService.modify(loanId, clientId, newInfo)
    }

    //@Secured('ROLE_USER')
    @RequestMapping(value = "/get/banks", method = GET)
    @ResponseBody
    BankMapWrapper selectBanks() {
        return new BankMapWrapper(daoService.getBanksMap())
    }

    //@Secured('ROLE_USER')
    @RequestMapping(value = "/get/currencies", method = GET)
    @ResponseBody
    CurrencyMapWrapper selectCurrencies() {
        return new CurrencyMapWrapper(daoService.getCurrenciesMap())
    }

    //@Secured('ROLE_ADMIN')
    @RequestMapping(value = "/new/bank", method = POST)
    @ResponseBody
    boolean insertBank(@RequestBody Bank bank) {
        return daoService.addBank(bank)
    }

    //@Secured('ROLE_ADMIN')
    @RequestMapping(value = "/new/currency", method = POST)
    @ResponseBody
    boolean insertCurrency(@RequestBody Currency currency) {
        return daoService.addCurrency(currency)
    }

}
