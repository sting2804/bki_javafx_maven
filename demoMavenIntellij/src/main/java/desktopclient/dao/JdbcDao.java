package desktopclient.dao;


import desktopclient.dao.util.IConnectionFactory;
import desktopclient.dao.util.SimpleConnectionFactory;
import desktopclient.entities.Bank;
import desktopclient.entities.Currency;
import desktopclient.entities.LoanInfo;
import desktopclient.entities.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sting
 */
public class JdbcDao implements IBkiDao {
    IConnectionFactory connectionFactory;
    Connection connection;
    private List <String> columnNames;
    private List<LoanInfo> data;
    private List<Bank> banks;
    private List<Currency> currencies;
    private int columnCount;

    public JdbcDao() {
        this.data = new ArrayList<>();
        this.currencies = new ArrayList<>();
        this.banks = new ArrayList<>();
        connectionFactory = new SimpleConnectionFactory();
        try {
            //задумывалось использование шаблонного метода
            connection = connectionFactory.createConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * метод для получения всех записей из базы
     * @return
     */
    @Override
    public List<LoanInfo> getAllRecords(){
        data.clear();
        String query = "select * from client_info_view";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet answer = ps.executeQuery()){
                Person person;
                LoanInfo loanInfo;
                while (answer.next()){
                    person = new Person();
                    loanInfo = new LoanInfo();
                    person.setName(answer.getString(1));
                    person.setSurname(answer.getString(2));
                    person.setPatronymic(answer.getString(3));
                    person.setBirthday(answer.getDate(4).toLocalDate());
                    person.setInn(answer.getString(5));
                    person.setPassSerial(answer.getString(6));
                    person.setPassNumber(answer.getString(7));
                    person.setId(answer.getInt(15));
                    loanInfo.setPerson(person);
                    loanInfo.setInitAmount(answer.getDouble(8));
                    try {
                        loanInfo.setInitDate(answer.getDate(9).toLocalDate());
                    } catch (Exception e){}
                    try {
                        loanInfo.setFinishDate(answer.getDate(10).toLocalDate());
                    } catch (Exception e){}
                    try{
                        loanInfo.setBalance(answer.getDouble(11));
                    } catch (Exception e){}
                    loanInfo.setArrears(answer.getBoolean(12));
                    loanInfo.setBank(answer.getString(13));
                    loanInfo.setCurrency(answer.getString(14));
                    loanInfo.setId(answer.getInt(16));
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
        String query = "select * from client_info_view where (name=? and surname=? and patronymic=? and birthday=?) or "+
                " inn=? or  (pass_serial=? and pass_number=?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, person.getName());
            ps.setString(2, person.getSurname());
            ps.setString(3, person.getPatronymic());
            try {
                ps.setString(4, person.getBirthday().toString());
            }catch (Exception e){
                ps.setString(4, "");
            }
            ps.setString(5, person.getInn());
            ps.setString(6, person.getPassSerial());
            ps.setString(7, person.getPassNumber());
            try (ResultSet answer = ps.executeQuery()){
                if (answer.next()) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean addNewPerson(Person person) {
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
        data.clear();
        String query = "select name, surname, patronymic, birthday, inn, pass_serial, pass_number, "+
            "init_amount, init_date, finish_date, balance, arrears, bank, currency, client_id, loan_id "+
            "from client_info_view where (name=? and surname=? and patronymic=? and birthday =?) "+
            " or inn=? or  (pass_serial=? and pass_number=?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, person.getName());
            ps.setString(2, person.getSurname());
            ps.setString(3, person.getPatronymic());
            try {
                ps.setString(4, person.getBirthday().toString());
            }catch (Exception e){
                ps.setString(4, "");
            }
            ps.setString(5, person.getInn());
            ps.setString(6, person.getPassSerial());
            ps.setString(7, person.getPassNumber());
            try (ResultSet answer = ps.executeQuery()){
                LoanInfo loanInfo;
                while (answer.next()){
                    person = new Person();
                    loanInfo = new LoanInfo();
                    person.setName(answer.getString(1));
                    person.setSurname(answer.getString(2));
                    person.setPatronymic(answer.getString(3));
                    person.setBirthday(answer.getDate(4).toLocalDate());
                    person.setInn(answer.getString(5));
                    person.setPassSerial(answer.getString(6));
                    person.setPassNumber(answer.getString(7));
                    person.setId(answer.getInt(15));
                    loanInfo.setPerson(person);
                    loanInfo.setInitAmount(answer.getDouble(8));
                    try {
                        loanInfo.setInitDate(answer.getDate(9).toLocalDate());
                    } catch (Exception e){}
                    try {
                        loanInfo.setFinishDate(answer.getDate(10).toLocalDate());
                    } catch (Exception e){}
                    try{
                        loanInfo.setBalance(answer.getDouble(11));
                    } catch (Exception e){}
                    loanInfo.setArrears(answer.getBoolean(12));
                    loanInfo.setBank(answer.getString(13));
                    loanInfo.setCurrency(answer.getString(14));
                    loanInfo.setId(answer.getInt(16));
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
    public List<Bank> getBanksList() {
        banks.clear();
        String query = "select * from bank";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet answer = ps.executeQuery()){
                Bank bank;
                while (answer.next()){
                    bank = new Bank();
                    bank.setBankCode(answer.getString(1));
                    bank.setBankName(answer.getString(2));
                    banks.add(bank);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return banks;
    }

    @Override
    public List<Currency> getCurrenciesList() {
        currencies.clear();
        String query = "select * from currency";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet answer = ps.executeQuery()){
                Currency currency;
                while (answer.next()){
                    currency = new Currency();
                    currency.setCurrencyCode(answer.getString(1));
                    currency.setCurrencyName(answer.getString(2));
                    currencies.add(currency);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return currencies;
    }
}
