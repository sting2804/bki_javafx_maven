package com.privat.bki.apiserver.services

import com.privat.bki.apiserver.services.dao.JdbcDao
import com.privat.bki.apiserver.validators.ParamValidator
import com.privat.bki.business.entities.*
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class LoanDaoService {
    def jdbcDao
    ParamValidator validator

    List<LoanInfo> getRecord(int id) {
        return jdbcDao.getRecord(id)
    }

    List<LoanInfo> listAll() {
        return jdbcDao.getAllRecords()
    }

    List<LoanInfo> listSpecificInfo(Person person) {
        return jdbcDao.getPersonInfo(person)
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
