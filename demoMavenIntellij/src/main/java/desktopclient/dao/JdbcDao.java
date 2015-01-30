package desktopclient.dao;


import desktopclient.dao.util.ConnectionManager;
import desktopclient.dao.util.IConnectionManager;
import desktopclient.entities.Bank;
import desktopclient.entities.Currency;
import desktopclient.entities.LoanInfo;
import desktopclient.entities.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by sting
 */
public class JdbcDao implements IBkiDao {
    Connection connection;
    private List<LoanInfo> data;
    private Map<String, String> banks;
    private Map<String, String> currencies;
    private IConnectionManager connectionManager;

    public JdbcDao() {
        this.data = new ArrayList<>();
        this.currencies = new HashMap<>();
        this.banks = new HashMap<>();
        connectionManager = new ConnectionManager();
        try {
            //задумывалось использование шаблонного метода
            connection = connectionManager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод для получения всех записей из базы
     *
     * @return
     */
    @Override
    public List<LoanInfo> getAllRecords() {
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
                    person.setName(answer.getString(1));
                    person.setSurname(answer.getString(2));
                    person.setPatronymic(answer.getString(3));
                    person.setBirthday(answer.getDate(4).toLocalDate());
                    person.setInn(answer.getString(5));
                    person.setPassSerial(answer.getString(6));
                    person.setPassNumber(answer.getString(7));
                    person.setId(answer.getInt(17));

                    loanInfo.setPerson(person);

                    bank.setCode(answer.getString(13));
                    bank.setName(answer.getString(14));

                    loanInfo.setBank(bank);

                    currency.setCode(answer.getString(15));
                    currency.setName(answer.getString(16));

                    loanInfo.setCurrency(currency);

                    loanInfo.setInitAmount(answer.getDouble(8));
                    try {
                        loanInfo.setInitDate(answer.getDate(9).toLocalDate());
                    } catch (Exception e) {
                    }
                    try {
                        loanInfo.setFinishDate(answer.getDate(10).toLocalDate());
                    } catch (Exception e) {
                    }
                    try {
                        loanInfo.setBalance(answer.getDouble(11));
                    } catch (Exception e) {
                    }
                    loanInfo.setArrears(answer.getBoolean(12));
                    loanInfo.setId(answer.getInt(18));
                    data.add(loanInfo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return data;
    }

    @Override
    public boolean isClientExists(Person person) {
        String query = "select 1 from client_info_view where (name=? and surname=? and patronymic=? and birthday=?) or " +
                " inn=? or  (pass_serial=? and pass_number=?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, person.getName());
            ps.setString(2, person.getSurname());
            ps.setString(3, person.getPatronymic());
            try {
                ps.setString(4, person.getBirthday().toString());
            } catch (Exception e) {
                ps.setString(4, "");
            }
            ps.setString(5, person.getInn());
            ps.setString(6, person.getPassSerial());
            ps.setString(7, person.getPassNumber());
            try (ResultSet answer = ps.executeQuery()) {
                if (answer.next()) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    @Override
    public boolean addNewInfo(LoanInfo info) {
        return false;
    }

    @Override
    public boolean changeInfo(LoanInfo curInfo) {
        return false;
    }

    @Override
    public List<LoanInfo> getPersonInfo(Person person) {
        if (!isClientExists(person)) return null;
        data.removeAll(data);
        String query = "select name, surname, patronymic, birthday, inn, " +
                "pass_serial, pass_number, init_amount, init_date, finish_date, " +
                "balance, arrears, bank_code, bank_name, currency_code,currency_name, client_id, loan_id " +
                "from client_info_view where (name=? and surname=? and patronymic=? and birthday =?) " +
                " or inn=? or  (pass_serial=? and pass_number=?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, person.getName());
            ps.setString(2, person.getSurname());
            ps.setString(3, person.getPatronymic());
            try {
                ps.setString(4, person.getBirthday().toString());
            } catch (Exception e) {
                ps.setString(4, null);
            }
            ps.setString(5, person.getInn());
            ps.setString(6, person.getPassSerial());
            ps.setString(7, person.getPassNumber());
            try (ResultSet answer = ps.executeQuery()) {
                LoanInfo loanInfo;
                Bank bank;
                Currency currency;
                while (answer.next()) {
                    bank = new Bank();
                    currency = new Currency();
                    person = new Person();
                    loanInfo = new LoanInfo();
                    person.setName(answer.getString(1));
                    person.setSurname(answer.getString(2));
                    person.setPatronymic(answer.getString(3));
                    person.setBirthday(answer.getDate(4).toLocalDate());
                    person.setInn(answer.getString(5));
                    person.setPassSerial(answer.getString(6));
                    person.setPassNumber(answer.getString(7));
                    person.setId(answer.getInt(17));

                    loanInfo.setPerson(person);

                    bank.setCode(answer.getString(13));
                    bank.setName(answer.getString(14));

                    loanInfo.setBank(bank);

                    currency.setCode(answer.getString(15));
                    currency.setName(answer.getString(16));

                    loanInfo.setCurrency(currency);

                    loanInfo.setInitAmount(answer.getDouble(8));
                    try {
                        loanInfo.setInitDate(answer.getDate(9).toLocalDate());
                    } catch (Exception e) {
                    }
                    try {
                        loanInfo.setFinishDate(answer.getDate(10).toLocalDate());
                    } catch (Exception e) {
                    }
                    try {
                        loanInfo.setBalance(answer.getDouble(11));
                    } catch (Exception e) {
                    }
                    loanInfo.setArrears(answer.getBoolean(12));
                    loanInfo.setId(answer.getInt(18));
                    data.add(loanInfo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public boolean deletePerson(int personId) {
        return false;
    }

    @Override
    public Map<String, String> getBanksMap() {
        banks.clear();
        String query = "select bank_code, bank_name from bank";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet answer = ps.executeQuery()) {
                while (answer.next()) {
                    banks.put(answer.getString(1), answer.getString(2));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return banks;
    }

    @Override
    public Map<String, String> getCurrenciesMap() {
        currencies.clear();
        String query = "select currency_code, currency_name from currency";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet answer = ps.executeQuery()) {
                while (answer.next()) {
                    currencies.put(answer.getString(1), answer.getString(2));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return currencies;
    }

    @Override
    public boolean addNewClient(LoanInfo info) {
        if (addNewPerson(info.getPerson())){

        }
        return false;
    }

    private boolean addNewPerson(Person person) {
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
            ps.setDate(i++, new Date(person.getBirthday().toEpochDay()));
            ps.setString(i++, person.getPassSerial());
            ps.setString(i++, person.getPassNumber());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
