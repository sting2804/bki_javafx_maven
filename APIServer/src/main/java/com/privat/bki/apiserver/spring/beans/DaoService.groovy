package com.privat.bki.apiserver.spring.beans
import com.privat.bki.apiserver.spring.beans.dao.IBkiDao
import com.privat.bki.business.entities.Bank
import com.privat.bki.business.entities.Currency
import com.privat.bki.business.entities.LoanInfo
import com.privat.bki.business.entities.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service


@Component("daoService")
@Service
class DaoService {
    @Autowired
    @Qualifier("jdbcDao")
    private IBkiDao dao

    List<LoanInfo> getRecord(int id) {
        return dao.getRecord(id)
    }

    List<LoanInfo> listAll() {
        return dao.getAllRecords()
    }

    List<LoanInfo> listSpecificInfo(Person person) {
        return dao.getPersonInfo(person)
    }

    Integer isClientExists(Person person) {
        return dao.isClientExists(person)
    }

    Boolean addInfo(LoanInfo info, int clientId) {
        return dao.addNewInfo(info, clientId)
    }

    Boolean addNewClient(LoanInfo info) {
        return dao.addNewClient(info)
    }

    Boolean modify(int loanId, int clientId, LoanInfo newInfo) {
        return dao.changeInfo(loanId, clientId, newInfo)
    }

    Map<String, String> getBanksMap() {
        return dao.getBanksMap()
    }

    Map<String, String> getCurrenciesMap() {
        return dao.getCurrenciesMap()
    }

    Boolean addBank(Bank bank) {
        return dao.addNewBank(bank)
    }

    Boolean addCurrency(Currency currency) {
        return dao.addNewCurrency(currency)
    }

    /**
     * самый предпочитаемый банк
     */
    def getTheMostPreferredBank(){

    }

    /**
     * возвращает возраст клиентов, которые чаще берут кредиты
     */
    def getOftenTakingClientsAge(){

    }

    /**
     * статистика использования банков в разных регионах
     */
    def getBankUsageByRegions(){

    }

    /**
     * предпочитаемые периоды времени для кредитов
     */
    def getTheMostPreferredPeriodsForCredits(){

    }

    /**
     * статистика по срочности (продолжительности срока) кредитов
     */
    def getTimesForCreditingDuration(){

    }

}
