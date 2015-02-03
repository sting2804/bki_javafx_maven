package desktopclient.dao;


import desktopclient.dao.util.ConnectionManager;
import desktopclient.entities.Bank;
import desktopclient.entities.Currency;
import desktopclient.entities.LoanInfo;
import desktopclient.entities.Person;
import desktopclient.utils.Converter;
import desktopclient.utils.DateConverter;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JdbcDao implements IBkiDao {

    private static final Logger log = Logger.getLogger(JdbcDao.class);

    Connection connection;
    private List<LoanInfo> data;
    private Map<String, String> banks;
    private Map<String, String> currencies;
    private Converter<Date, LocalDate> dateConverter;

    public JdbcDao() {
        this.data = new ArrayList<>();
        this.currencies = new HashMap<>();
        this.banks = new HashMap<>();
        //задумывалось использование шаблонного метода
        connection = ConnectionManager.getConnection();
        dateConverter = new DateConverter();
    }

    @Override
    public List<LoanInfo> getAllRecords() {
        if (connection == null) return null;
        data.removeAll(data);
        String query = "select * from client_info_view";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet answer = ps.executeQuery()) {
                LoanInfo loanInfo;
                Bank bank;
                Person person;
                Currency currency;
                while (answer.next()) {
                    bank = new Bank();
                    currency = new Currency();
                    person = new Person();
                    loanInfo = new LoanInfo();
                    int i = 1;
                    person.setName(answer.getString(i++));
                    person.setSurname(answer.getString(i++));
                    person.setPatronymic(answer.getString(i++));
                    person.setBirthday(answer.getDate(i++).toLocalDate());
                    person.setInn(answer.getString(i++));
                    person.setPassSerial(answer.getString(i++));
                    person.setPassNumber(answer.getString(i++));
                    loanInfo.setInitAmount(answer.getDouble(i++));
                    try {
                        loanInfo.setInitDate(answer.getDate(i++).toLocalDate());
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    try {
                        loanInfo.setFinishDate(answer.getDate(i++).toLocalDate());
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    try {
                        loanInfo.setBalance(answer.getDouble(i++));
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    loanInfo.setArrears(answer.getBoolean(i++));
                    bank.setCode(answer.getString(i++));
                    bank.setName(answer.getString(i++));

                    currency.setCode(answer.getString(i++));
                    currency.setName(answer.getString(i++));
                    person.setId(answer.getInt(i++));

                    loanInfo.setId(answer.getInt(i));

                    loanInfo.setPerson(person);
                    loanInfo.setBank(bank);
                    loanInfo.setCurrency(currency);

                    data.add(loanInfo);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return data;
    }

    @Override
    public int isClientExists(Person person) {
        if (connection == null) return -1;
        String query = "select client_id from client_info_view where (name=? and surname=? and patronymic=? and birthday=?) or " +
                " inn=? or  (pass_serial=? and pass_number=?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int i = 1;
            ps.setString(i++, person.getName());
            ps.setString(i++, person.getSurname());
            ps.setString(i++, person.getPatronymic());
            try {
                ps.setDate(i++, dateConverter.to(person.getBirthday()));
            } catch (SQLException e) {
                ps.setString(i, "");
            }
            ps.setString(i++, person.getInn());
            ps.setString(i++, person.getPassSerial());
            ps.setString(i, person.getPassNumber());
            try (ResultSet answer = ps.executeQuery()) {
                if (answer.next()) {
                    int id = answer.getInt(1);
                    return id;
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return -1;
        }
        return -1;
    }

    @Override
    public boolean addNewInfo(LoanInfo info, int clientId) {
        if (connection == null) return false;
        int id;
        String callQuery = "{call GET_ID (?,?)}";
        String query = "insert into loan(loan_id, currency_code, bank_code, client_id, " +
                "init_amount, init_date, finish_date, balance, arrears) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (CallableStatement cs = connection.prepareCall(callQuery);
             PreparedStatement ps = connection.prepareStatement(query)) {
            int i = 1;
            cs.setString(i++, "loan");
            cs.registerOutParameter(i, Types.INTEGER);
            cs.execute();
            id = cs.getInt(i);
            i = 1;
            ps.setInt(i++, id);
            ps.setString(i++, info.getCurrency().getCode());
            ps.setString(i++, info.getBank().getCode());
            ps.setInt(i++, clientId);
            ps.setDouble(i++, info.getInitAmount());
            ps.setDate(i++, dateConverter.to(info.getInitDate()));
            ps.setDate(i++, dateConverter.to(info.getFinishDate()));
            ps.setDouble(i++, info.getBalance());
            ps.setBoolean(i, info.getArrears());
            ps.execute();
        } catch (SQLException e) {
            log.info(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean changeInfo(LoanInfo oldInfo, LoanInfo newInfo) {
        if (connection == null) return false;
        if (updatePerson(oldInfo.getPerson(), newInfo.getPerson())) {
            String str = "currency_code=?, bank_code=?, client_id=?," +
                    " init_amount=?, init_date=?, finish_date=?, balance=?, arrears=?";
            String query = "update loan set " + str + " where loan_id=?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                int i = 1;
                ps.setString(i++, newInfo.getCurrency().getCode());
                ps.setString(i++, newInfo.getBank().getCode());
                ps.setInt(i++, oldInfo.getPerson().getId());
                ps.setDouble(i++, newInfo.getInitAmount());
                ps.setDate(i++, dateConverter.to(newInfo.getInitDate()));
                ps.setDate(i++, dateConverter.to(newInfo.getFinishDate()));
                ps.setDouble(i++, newInfo.getBalance());
                ps.setBoolean(i++, newInfo.getArrears());

                ps.setInt(i, oldInfo.getId());

                ps.execute();
            } catch (SQLException e) {
                log.error(e.getMessage());
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Обновляет запись в таблице клиентов
     *
     * @param oldPerson данные, на которые запрос ориентируется для изменения записи
     * @param newPerson новые данные
     * @return возвращает true в случае удачи и false в обратном
     */
    private boolean updatePerson(Person oldPerson, Person newPerson) {
        if (connection == null) return false;
        String str = "inn=?, name=?, surname=?, patronymic=?," +
                "birthday=?, pass_serial=?, pass_number=?";
        String query = "update client set " + str + " where client_id=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int i = 1;
            ps.setString(i++, newPerson.getInn());
            ps.setString(i++, newPerson.getName());
            ps.setString(i++, newPerson.getSurname());
            ps.setString(i++, newPerson.getPatronymic());
            ps.setDate(i++, dateConverter.to(newPerson.getBirthday()));
            ps.setString(i++, newPerson.getPassSerial());
            ps.setString(i++, newPerson.getPassNumber());

            ps.setInt(i, oldPerson.getId());

            ps.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<LoanInfo> getPersonInfo(Person person) {
        if (connection == null) return null;
        int clientId = isClientExists(person);
        if (clientId == -1) return null;
        data.removeAll(data);
        String query = "select name, surname, patronymic, birthday, inn, " +
                "pass_serial, pass_number, init_amount, init_date, finish_date, " +
                "balance, arrears, bank_code, bank_name, currency_code,currency_name, client_id, loan_id " +
                "from client_info_view where (name=? and surname=? and patronymic=? and birthday =?) " +
                " or inn=? or  (pass_serial=? and pass_number=?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int i = 1;
            ps.setString(i++, person.getName());
            ps.setString(i++, person.getSurname());
            ps.setString(i++, person.getPatronymic());
            try {
                ps.setDate(i++, dateConverter.to(person.getBirthday()));
            } catch (SQLException e) {
                ps.setString(i, null);
            }
            ps.setString(i++, person.getInn());
            ps.setString(i++, person.getPassSerial());
            ps.setString(i, person.getPassNumber());
            try (ResultSet answer = ps.executeQuery()) {
                LoanInfo loanInfo;
                Bank bank;
                Currency currency;
                while (answer.next()) {
                    bank = new Bank();
                    currency = new Currency();
                    person = new Person();
                    loanInfo = new LoanInfo();
                    i = 1;
                    person.setName(answer.getString(i++));
                    person.setSurname(answer.getString(i++));
                    person.setPatronymic(answer.getString(i++));
                    person.setBirthday(answer.getDate(i++).toLocalDate());
                    person.setInn(answer.getString(i++));
                    person.setPassSerial(answer.getString(i++));
                    person.setPassNumber(answer.getString(i++));
                    loanInfo.setInitAmount(answer.getDouble(i++));
                    try {
                        loanInfo.setInitDate(answer.getDate(i++).toLocalDate());
                    } catch (SQLException e) {
                        log.error(e.getMessage());
                    }
                    try {
                        loanInfo.setFinishDate(answer.getDate(i++).toLocalDate());
                    } catch (SQLException e) {
                        log.error(e.getMessage());
                    }
                    try {
                        loanInfo.setBalance(answer.getDouble(i++));
                    } catch (SQLException e) {
                        log.error(e.getMessage());
                    }
                    loanInfo.setArrears(answer.getBoolean(i++));
                    bank.setCode(answer.getString(i++));
                    bank.setName(answer.getString(i++));
                    currency.setCode(answer.getString(i++));
                    currency.setName(answer.getString(i++));
                    person.setId(answer.getInt(i++));
                    loanInfo.setId(answer.getInt(i));

                    loanInfo.setCurrency(currency);
                    loanInfo.setBank(bank);
                    loanInfo.setPerson(person);

                    data.add(loanInfo);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return data;
    }

    @Override
    public Map<String, String> getBanksMap() {
        if (connection == null) return null;
        banks.clear();
        String query = "select bank_code, bank_name from bank";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet answer = ps.executeQuery()) {
                while (answer.next()) {
                    banks.put(answer.getString(1), answer.getString(2));
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return banks;
    }

    @Override
    public Map<String, String> getCurrenciesMap() {
        if (connection == null) return null;
        currencies.clear();
        String query = "select currency_code, currency_name from currency";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet answer = ps.executeQuery()) {
                while (answer.next()) {
                    currencies.put(answer.getString(1), answer.getString(2));
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return currencies;
    }

    @Override
    public boolean addNewClient(LoanInfo info) {
        int clientId = addNewPerson(info.getPerson());
        if (clientId != -1) {
            if (addNewInfo(info, clientId))
                return true;
        }
        return false;
    }

    @Override
    public boolean addNewCurrency(Currency currency) {
        if (connection == null) return false;

        String query = "insert into currency(currency_code, currency_name) values(?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int i = 1;
            ps.setString(i++, currency.getCode());
            ps.setString(i, currency.getName());
            ps.execute();

        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean addNewBank(Bank bank) {
        if (connection == null) return false;

        String query = "insert into bank(bank_code, bank_name) values(?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int i = 1;
            ps.setString(i++, bank.getCode());
            ps.setString(i, bank.getName());
            ps.execute();

        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Добавляет одну запись в таблицу client
     *
     * @param person содержит информацию о клиенте
     * @return Возвращает id клиента, вставленного в базу. Если вставка не произошла, возвращает -1
     */
    private int addNewPerson(Person person) {
        if (connection == null) return -1;
        int id;
        String callQuery = "{call GET_ID (?,?)}";
        String query = "insert into client(client_id, inn, name, surname, patronymic," +
                "birthday, pass_serial, pass_number) values(?, ?, ?, ?, ?, ?, ?, ?)";
        try (CallableStatement cs = connection.prepareCall(callQuery);
             PreparedStatement ps = connection.prepareStatement(query)) {
            int i=1;
            cs.setString(i++, "client");
            cs.registerOutParameter(i, Types.INTEGER);
            cs.execute();
            id = cs.getInt(i);
            i=1;
            ps.setInt(i++, id);
            ps.setString(i++, person.getInn());
            ps.setString(i++, person.getName());
            ps.setString(i++, person.getSurname());
            ps.setString(i++, person.getPatronymic());
            ps.setDate(i++, dateConverter.to(person.getBirthday()));
            ps.setString(i++, person.getPassSerial());
            ps.setString(i, person.getPassNumber());
            ps.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return -1;
        }
        return id;
    }
}
