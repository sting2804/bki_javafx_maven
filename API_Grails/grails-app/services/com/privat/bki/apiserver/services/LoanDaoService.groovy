package com.privat.bki.apiserver.services

import com.privat.bki.apiserver.services.dao.IBkiDao
import com.privat.bki.apiserver.validators.ParamValidator
import com.privat.bki.entities.Bank
import com.privat.bki.entities.LoanInfo
import com.privat.bki.entities.Person
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class LoanDaoService {
    private IBkiDao jdbcDao
    private ParamValidator validator

    List<LoanInfo> getRecord(int id) {
        return jdbcDao.getRecord(id)
    }

    List<LoanInfo> listAll() {
        return jdbcDao.getAllRecords()
    }

    List<LoanInfo> listSpecificInfo(GrailsParameterMap params){//Person person) {
        validator = new ParamValidator(['person'], params)
        Person person = new Person(params.id, params.name, )
        return validator.isValid()?jdbcDao.getPersonInfo(person):validator.errors
    }

    Integer isClientExists(Person person) {
        return jdbcDao.isClientExists(person)
    }

    Boolean addInfo(LoanInfo info, int clientId) {
        return jdbcDao.addNewInfo(info, clientId)
    }

    Boolean addNewClient(LoanInfo info) {
        return jdbcDao.addNewClient(info)
    }

    Boolean modify(int loanId, int clientId, LoanInfo newInfo) {
        return jdbcDao.changeInfo(loanId, clientId, newInfo)
    }

    Map<String, String> getBanksMap() {
        return jdbcDao.getBanksMap()
    }

    Map<String, String> getCurrenciesMap() {
        return jdbcDao.getCurrenciesMap()
    }

    Boolean addBank(Bank bank) {
        return jdbcDao.addNewBank(bank)
    }

    Boolean addCurrency(Currency currency) {
        return jdbcDao.addNewCurrency(currency)
    }
}
