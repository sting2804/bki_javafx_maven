package com.privat.bki.apiserver.spring.beans.dao;
import com.privat.bki.apiserver.mappers.BanksRowMapper;
import com.privat.bki.apiserver.mappers.CurrencyRowMapper;
import com.privat.bki.apiserver.mappers.LoanInfoRowMapper;
import com.privat.bki.business.entities.Bank;
import com.privat.bki.business.entities.Currency;
import com.privat.bki.business.entities.LoanInfo;
import com.privat.bki.business.entities.Person;
import com.privat.bki.business.utils.Converter;
import com.privat.bki.business.utils.SqlDateConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Component("jdbcDao")
@Service
public class JdbcDao implements IBkiDao {

    private static final Logger log = Logger.getLogger(JdbcDao.class);

    private List<LoanInfo> data;
    private Map<String, String> banks;
    private Map<String, String> currencies;
    private Converter<Date, LocalDate> dateConverter;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcDao() {
        dateConverter = new SqlDateConverter();
        this.data = new ArrayList<>();
        this.currencies = new HashMap<>();
        this.banks = new HashMap<>();
    }


    public List<LoanInfo> getRecord(int id) {
        data.removeAll(data);
        String query = "select * from client_info_view where loan_id=" + id;
        data = jdbcTemplate.query(query, new LoanInfoRowMapper());
        return data;
    }

    @Override
    public List<LoanInfo> getAllRecords() {
        data.removeAll(data);
        String query = "SELECT * FROM client_info_view";
        return data = jdbcTemplate.query(query, new LoanInfoRowMapper());
    }

    @Override
    public Integer isClientExists(Person person) {
        String query = "SELECT TOP 1 client_id FROM client_info_view WHERE (name=? AND surname=? AND patronymic=? AND birthday=?) OR " +
                " inn=? OR  (pass_serial=? AND pass_number=?)";
        Integer res = jdbcTemplate.queryForObject(query, Integer.class, person.getName(), person.getSurname(), person.getPatronymic(),
                dateConverter.to(person.getBirthday()), person.getInn(), person.getPassSerial(), person.getPassNumber());
        return res;
    }

    @Override
    public List<LoanInfo> getPersonInfo(Person person) {
        //Integer clientId = isClientExists(person);
        Integer clientId = person.getId();
        if (clientId == null) return null;
        data.removeAll(data);
        String query = "SELECT name, surname, patronymic, birthday, inn, " +
                "pass_serial, pass_number, init_amount, init_date, finish_date, " +
                "balance, arrears, bank_code, bank_name, currency_code,currency_name, client_id, loan_id " +
                "FROM client_info_view WHERE (name=? AND surname=? AND patronymic=? AND birthday =?) " +
                " OR inn=? OR  (pass_serial=? AND pass_number=?)";
        return data = jdbcTemplate.query(query, new LoanInfoRowMapper(), person.getName(), person.getSurname(), person.getPatronymic(),
                dateConverter.to(person.getBirthday()), person.getInn(), person.getPassSerial(), person.getPassNumber());
    }

    @Override
    public Boolean addNewClient(LoanInfo info) {
        int clientId = addNewPerson(info.getPerson());
        if (clientId != -1) {
            if (addNewInfo(info, clientId))
                return true;
        }
        return false;
    }

    /**
     * Добавляет одну запись в таблицу client
     *
     * @param person содержит информацию о клиенте
     * @return Возвращает id клиента, вставленного в базу. Если вставка не произошла, возвращает -1
     */
    private Integer addNewPerson(Person person) {
        int id;
        String query = "INSERT INTO client(client_id, inn, name, surname, patronymic," +
                "birthday, pass_serial, pass_number) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("GET_ID");
        Map<String, String> inParamMap = new HashMap();
        inParamMap.put("TABLE_NAME", "client");
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);

        Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(in);
        id = (int) simpleJdbcCallResult.get("ID_OUT");
        Integer res = jdbcTemplate.update(query, id, person.getInn(), person.getName(),
                person.getSurname(), person.getPatronymic(), dateConverter.to(person.getBirthday()),
                person.getPassSerial(), person.getPassNumber());
        if (res != 0 && res != null) return id;
        else return -1;
    }


    @Override
    public Boolean addNewInfo(LoanInfo info, int clientId) {
        int id;
        String query = "INSERT INTO loan(loan_id, currency_code, bank_code, client_id, " +
                "init_amount, init_date, finish_date, balance, arrears) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("GET_ID");
        Map<String, String> inParamMap = new HashMap();
        inParamMap.put("TABLE_NAME", "loan");
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(in);
        id = (int) simpleJdbcCallResult.get("ID_OUT");
        Object[] params = new Object[]{id, info.getCurrency().getCode(), info.getBank().getCode(),
            clientId, info.getInitAmount(), dateConverter.to(info.getInitDate()),
            dateConverter.to(info.getFinishDate()), info.getBalance(), info.getArrears()};
        return jdbcTemplate.update(query, params) > 0;
    }

    @Override
    public Boolean changeInfo(int loanId, int clientId, LoanInfo newInfo) {
        if (updatePerson(clientId, newInfo.getPerson())) {
            String str = "currency_code=?, bank_code=?, client_id=?," +
                    " init_amount=?, init_date=?, finish_date=?, balance=?, arrears=?";
            String query = "update loan set " + str + " where loan_id=?";
            return jdbcTemplate.update(query, newInfo.getCurrency().getCode(),
                    newInfo.getBank().getCode(), clientId,
                    newInfo.getInitAmount(), dateConverter.to(newInfo.getInitDate()),
                    dateConverter.to(newInfo.getFinishDate()), newInfo.getBalance(),
                    newInfo.getArrears(), loanId) > 0;
        }
        return false;
    }

    /**
     * Обновляет запись в таблице клиентов
     *
     * @param clientId данные, на которые запрос ориентируется для изменения записи
     * @param newPerson новые данные
     * @return возвращает true в случае удачи и false в обратном
     */
    private Boolean updatePerson(int clientId, Person newPerson) {
        String str = "inn=?, name=?, surname=?, patronymic=?," +
                "birthday=?, pass_serial=?, pass_number=?";
        String query = "update client set " + str + " where client_id=?";
        return jdbcTemplate.update(query, newPerson.getInn(), newPerson.getName(),
                newPerson.getSurname(), newPerson.getPatronymic(),
                dateConverter.to(newPerson.getBirthday()), newPerson.getPassSerial(),
                newPerson.getPassNumber(), clientId) > 0;
    }


    @Override
    public Map<String, String> getBanksMap() {
        banks.clear();
        String query = "SELECT bank_code, bank_name FROM bank";
        List<Bank> bl = jdbcTemplate.query(query, new BanksRowMapper());
        for (Bank b : bl) {
            banks.put(b.getCode(), b.getName());
        }
        return banks;
    }

    @Override
    public Map<String, String> getCurrenciesMap() {
        currencies.clear();
        String query = "SELECT currency_code, currency_name FROM currency";
        List<Currency> cl = jdbcTemplate.query(query, new CurrencyRowMapper());
        for (Currency c : cl) {
            currencies.put(c.getCode(), c.getName());
        }
        return currencies;
    }


    @Override
    public Boolean addNewCurrency(Currency currency) {
        String query = "INSERT INTO currency(currency_code, currency_name) VALUES(?, ?)";
        return jdbcTemplate.update(query, currency.getCode(), currency.getName()) > 0;
    }

    @Override
    public Boolean addNewBank(Bank bank) {
        String query = "INSERT INTO bank(bank_code, bank_name) VALUES(?, ?)";
        return jdbcTemplate.update(query, bank.getCode(), bank.getName()) > 0;
    }


}