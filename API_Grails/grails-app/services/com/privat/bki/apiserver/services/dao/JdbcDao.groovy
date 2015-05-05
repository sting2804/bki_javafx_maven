package com.privat.bki.apiserver.services.dao

import com.privat.bki.apiserver.mappers.BanksRowMapper
import com.privat.bki.apiserver.mappers.CurrencyRowMapper
import com.privat.bki.apiserver.mappers.LoanInfoRowMapper
import com.privat.bki.business.entities.*
import com.privat.bki.business.utils.*
import grails.transaction.Transactional
import org.apache.log4j.Logger
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcCall

import java.sql.Date
import java.time.LocalDate

/**
 *
 */
@Transactional
class JdbcDao implements IBkiDao {

    private static final Logger log = Logger.getLogger(JdbcDao.class)

    private List<LoanInfo> data
    private def banks
    private def currencies
    private Converter<Date, LocalDate> dateConverter
    JdbcTemplate jdbcTemplate

    JdbcDao() {
        dateConverter = new SqlDateConverter()
        this.data = []
        this.currencies = [:]
        this.banks = [:]
    }


    List<LoanInfo> getRecord(int id) {
        data.removeAll(data)
        String query = "select * from client_info_view where loan_id=" + id
        data = jdbcTemplate.query(query, new LoanInfoRowMapper())
        return data
    }

    @Override
    List<LoanInfo> getAllRecords() {
        data.removeAll(data)
        String query = "SELECT * FROM client_info_view"
        return data = jdbcTemplate.query(query, new LoanInfoRowMapper())
    }

    @Override
    Integer isClientExists(Person person) {
        String query = "SELECT TOP 1 client_id FROM client_info_view WHERE (name=? AND surname=? AND patronymic=? AND birthday=?) OR " +
                " inn=? OR  (pass_serial=? AND pass_number=?)"
        Integer res = jdbcTemplate.queryForObject(query, Integer.class, person.name, person.surname, person.patronymic,
                dateConverter.to(person.birthday), person.inn, person.passSerial, person.passNumber)
        return res
    }

    @Override
    List<LoanInfo> getPersonInfo(Person person) {
        Integer clientId = person.id
        if (clientId == null) return null
        data.removeAll(data)
        String query = "SELECT name, surname, patronymic, birthday, inn, " +
                "pass_serial, pass_number, init_amount, init_date, finish_date, " +
                "balance, arrears, bank_code, bank_name, currency_code,currency_name, client_id, loan_id " +
                "FROM client_info_view WHERE (name=? AND surname=? AND patronymic=? AND birthday =?) " +
                " OR inn=? OR  (pass_serial=? AND pass_number=?)"
        return data = jdbcTemplate.query(query, new LoanInfoRowMapper(), person.name, person.surname, person.patronymic,
                dateConverter.to(person.birthday), person.inn, person.passSerial, person.passNumber)
    }

    @Override
    Boolean addNewClient(LoanInfo info) {
        int clientId = addNewPerson(info.person)
        if (clientId != -1) {
            if (addNewInfo(info, clientId))
                return true
        }
        return false
    }

    /**
     * Добавляет одну запись в таблицу client
     *
     * @param person содержит информацию о клиенте
     * @return Возвращает id клиента, вставленного в базу. Если вставка не произошла, возвращает -1
     */
    private Integer addNewPerson(Person person) {
        int id
        String query = "INSERT INTO client(client_id, inn, name, surname, patronymic," +
                "birthday, pass_serial, pass_number) VALUES(?, ?, ?, ?, ?, ?, ?, ?)"
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("GET_ID")
        def inParamMap = [:]
        inParamMap.put("TABLE_NAME", "client")
        SqlParameterSource in1 = new MapSqlParameterSource(inParamMap)

        def simpleJdbcCallResult = jdbcCall.execute(in1)
        id = (int) simpleJdbcCallResult.get("ID_OUT")
        Integer res = jdbcTemplate.update(query, id, person.inn, person.name,
                person.surname, person.patronymic, dateConverter.to(person.birthday),
                person.passSerial, person.passNumber)
        if (res != 0 && res != null) return id
        else return -1
    }


    @Override
    Boolean addNewInfo(LoanInfo info, int clientId) {
        int id
        String query = "INSERT INTO loan(loan_id, currency_code, bank_code, client_id, " +
                "init_amount, init_date, finish_date, balance, arrears) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)"
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("GET_ID")
        def inParamMap = [:]
        inParamMap.put("TABLE_NAME", "loan")
        SqlParameterSource in1 = new MapSqlParameterSource(inParamMap)
        def simpleJdbcCallResult = jdbcCall.execute(in1)
        id = (int) simpleJdbcCallResult.get("ID_OUT")
        def params = [
            id, info.currency.code, info.bank.code,
            clientId, info.initAmount, dateConverter.to(info.initDate),
            dateConverter.to(info.finishDate), info.balance, info.arrears
        ]
        return jdbcTemplate.update(query, params) > 0
    }

    @Override
    Boolean changeInfo(int loanId, int clientId, LoanInfo newInfo) {
        if (updatePerson(clientId, newInfo.person)) {
            String str = "currency_code=?, bank_code=?, client_id=?," +
                    " init_amount=?, init_date=?, finish_date=?, balance=?, arrears=?"
            String query = "update loan set " + str + " where loan_id=?"
            return jdbcTemplate.update(query, newInfo.currency.code,
                    newInfo.bank.code, clientId,
                    newInfo.initAmount, dateConverter.to(newInfo.initDate),
                    dateConverter.to(newInfo.finishDate), newInfo.balance,
                    newInfo.arrears, loanId) > 0
        }
        return false
    }

    /**
     * Обновляет запись в таблице клиентов
     *
     * @param oldPerson данные, на которые запрос ориентируется для изменения записи
     * @param newPerson новые данные
     * @return возвращает true в случае удачи и false в обратном
     */
    private Boolean updatePerson(int clientId, Person newPerson) {
        String str = "inn=?, name=?, surname=?, patronymic=?," +
                "birthday=?, pass_serial=?, pass_number=?"
        String query = "update client set " + str + " where client_id=?"
        return jdbcTemplate.update(query, newPerson.inn, newPerson.name,
                newPerson.surname, newPerson.patronymic,
                dateConverter.to(newPerson.birthday), newPerson.passSerial,
                newPerson.passNumber, clientId) > 0
    }


    @Override
    Map<String, String> getBanksMap() {
        banks.clear()
        String query = "SELECT bank_code, bank_name FROM bank"
        List<Bank> bl = jdbcTemplate.query(query, new BanksRowMapper())
        for (Bank b : bl) {
            banks.put(b.code, b.name)
        }
        return banks
    }

    @Override
    Map<String, String> getCurrenciesMap() {
        currencies.clear()
        String query = "SELECT currency_code, currency_name FROM currency"
        List<Currency> cl = jdbcTemplate.query(query, new CurrencyRowMapper())
        for (Currency c : cl) {
            currencies.put(c.code, c.name)
        }
        return currencies
    }


    @Override
    Boolean addNewCurrency(Currency currency) {
        String query = "INSERT INTO currency(currency_code, currency_name) VALUES(?, ?)"
        return jdbcTemplate.update(query, currency.code, currency.name) > 0
    }

    @Override
    Boolean addNewBank(Bank bank) {
        String query = "INSERT INTO bank(bank_code, bank_name) VALUES(?, ?)"
        return jdbcTemplate.update(query, bank.code, bank.name) > 0
    }


}