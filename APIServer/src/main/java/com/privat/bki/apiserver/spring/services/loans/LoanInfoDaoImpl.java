package com.privat.bki.apiserver.spring.services.loans;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Component("loanInfoDao")
@Service
public class LoanInfoDaoImpl implements LoanInfoDao {

    private static final Logger log = Logger.getLogger(LoanInfoDaoImpl.class);

    private List<LoanInfo> data;
    private Map<String, String> banks;
    private Map<String, String> currencies;
    private Converter<Date, LocalDate> dateConverter;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    public LoanInfoDaoImpl() {
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
        String query = "SELECT TOP 1 client_id FROM client_info_view WHERE (name=:name AND surname=:surname AND patronymic=:patronymic AND birthday=:birthday) OR " +
                " inn=:inn OR  (pass_serial=:pass_serial AND pass_number=:pass_number)";
        Map params = new HashMap<>();
        params.put("name", person.getName());
        params.put("surname", person.getSurname());
        params.put("patronymic", person.getPatronymic());
        params.put("birthday", dateConverter.to(person.getBirthday()));
        params.put("inn", person.getInn());
        params.put("pass_serial", person.getPassSerial());
        params.put("pass_number", person.getPassNumber());
        try{
            return jdbcTemplate.queryForObject(query, params, Integer.class);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<LoanInfo> getPersonInfo(Person person) {
        Integer clientId = isClientExists(person);
        //Integer clientId = person.getId();
        if (clientId == null) return null;
        data.removeAll(data);
        String query = "SELECT name, surname, patronymic, birthday, inn, " +
                "pass_serial, pass_number, gender, init_amount, init_date, finish_date, " +
                "balance, arrears, bank_code, bank_name, currency_code,currency_name, client_id, loan_id " +
                "FROM client_info_view WHERE client_id=:clientId";
        Map params = new HashMap<>();
        params.put("name", person.getName());
        params.put("surname", person.getSurname());
        params.put("patronymic", person.getPatronymic());
        params.put("birthday", dateConverter.to(person.getBirthday()));
        params.put("inn", person.getInn());
        params.put("pass_serial",  person.getPassSerial());
        params.put("pass_number", person.getPassNumber());
        params.put("clientId", clientId);
        data = jdbcTemplate.query(query, params, new LoanInfoRowMapper());
        return data;
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
        Integer id=isClientExists(person);
        String query = "INSERT INTO client(client_id, inn, name, surname, patronymic," +
                "birthday, pass_serial, pass_number, gender) VALUES(:client_id, :inn, :name, :surname, :patronymic," +
                ":birthday, :pass_serial, :pass_number, :gender)";
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("GET_ID");
        Map<String, String> inParamMap = new HashMap();
        inParamMap.put("TABLE_NAME", "client");
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);

        Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(in);
        if(id!=null)
            return -1;
        else id = (int) simpleJdbcCallResult.get("ID_OUT");
        Map params = new HashMap<>();
        params.put("client_id", id);
        params.put("name", person.getName());
        params.put("surname", person.getSurname());
        params.put("patronymic", person.getPatronymic());
        params.put("birthday", dateConverter.to(person.getBirthday()));
        params.put("inn", person.getInn());
        params.put("pass_serial",  person.getPassSerial());
        params.put("pass_number", person.getPassNumber());
        params.put("gender", person.getGender());
        Integer res = jdbcTemplate.update(query, params);
        if (res != 0 && res != null) return id;
        else return -1;
    }


    @Override
    public Boolean addNewInfo(LoanInfo info, int clientId) {
        int id;
        String query = "INSERT INTO loan(loan_id, currency_code, bank_code, client_id, " +
                "init_amount, init_date, finish_date, balance, arrears) " +
                "VALUES(:loan_id, :currency_code, :bank_code, :client_id,:init_amount, :init_date, :finish_date, :balance, :arrears)";
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("GET_ID");
        Map<String, String> inParamMap = new HashMap();
        inParamMap.put("TABLE_NAME", "loan");
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);
        Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(in);
        id = (int) simpleJdbcCallResult.get("ID_OUT");
        Map params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("loan_id", id);
        params.put("currency_code", info.getCurrency().getCode());
        params.put("bank_code", info.getBank().getCode());
        params.put("init_amount", info.getInitAmount());
        params.put("init_date", dateConverter.to(info.getInitDate()));
        params.put("finish_date", dateConverter.to(info.getFinishDate()));
        params.put("balance",  info.getBalance());
        params.put("arrears", info.getArrears());
        return jdbcTemplate.update(query, params) > 0;
    }

    @Override
    public Boolean changeInfo(int loanId, int clientId, LoanInfo newInfo) {
        if (updatePerson(clientId, newInfo.getPerson())) {
            String str = "currency_code=:currency_code, bank_code=:bank_code, client_id=:client_id," +
                    " init_amount=:init_amount, init_date=:init_date, finish_date=:finish_date, " +
                    "balance=:balance, arrears=:arrears";
            String query = "update loan set " + str + " where loan_id=:loan_id";
            Map params = new HashMap<>();
            params.put("client_id", clientId);
            params.put("loan_id", loanId);
            params.put("currency_code", newInfo.getCurrency().getCode());
            params.put("bank_code", newInfo.getBank().getCode());
            params.put("init_amount", newInfo.getInitAmount());
            params.put("init_date", dateConverter.to(newInfo.getInitDate()));
            params.put("finish_date", dateConverter.to(newInfo.getFinishDate()));
            params.put("balance",  newInfo.getBalance());
            params.put("arrears", newInfo.getArrears());
            return jdbcTemplate.update(query, params) > 0;
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
        String str = "inn=:inn, name=:name, surname=:surname, patronymic=:patronymic," +
                "birthday=:birthday, pass_serial=:pass_serial, pass_number=:pass_number, gender=:gender";
        String query = "update client set " + str + " where client_id=:client_id";
        Map params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("name", newPerson.getName());
        params.put("surname", newPerson.getSurname());
        params.put("patronymic", newPerson.getPatronymic());
        params.put("birthday", dateConverter.to(newPerson.getBirthday()));
        params.put("inn", newPerson.getInn());
        params.put("pass_serial",  newPerson.getPassSerial());
        params.put("pass_number", newPerson.getPassNumber());
        params.put("gender", newPerson.getGender());
        return jdbcTemplate.update(query, params) > 0;
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
        String query = "INSERT INTO currency(currency_code, currency_name) VALUES(:currency_code, :currency_name)";
        Map params = new HashMap<>();
        params.put("currency_code",currency.getCode());
        params.put("currency_name",currency.getName());
        return jdbcTemplate.update(query, params) > 0;
    }

    @Override
    public Boolean addNewBank(Bank bank) {
        String query = "INSERT INTO bank(bank_code, bank_name) VALUES(:bank_code, :bank_name)";
        Map params = new HashMap<>();
        params.put("currency_code",bank.getCode());
        params.put("currency_name",bank.getName());
        return jdbcTemplate.update(query, params) > 0;
    }


}