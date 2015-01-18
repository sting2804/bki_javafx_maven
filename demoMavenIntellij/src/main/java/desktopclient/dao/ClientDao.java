package desktopclient.dao;


import desktopclient.dao.util.IConnectionFactory;
import desktopclient.dao.util.SimpleConnectionFactory;
import desktopclient.entities.LoanInfo;
import desktopclient.entities.Person;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by sting
 */
public class ClientDao {
    IConnectionFactory connectionFactory;
    Connection connection;
    private ArrayList <String> columnNames;
    private ArrayList <LoanInfo> data;
    private int columnCount;

    public ClientDao() {
        this.data = new ArrayList<>();
        connectionFactory = new SimpleConnectionFactory();
        try {
            //задумывалось использование шаблонного метода
            connection = connectionFactory.createConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LoanInfo> getAllRecords(){
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
                    data.add(loanInfo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return data;
    }

    public boolean isClientExists(Person person) {
        /*String query = "select * from client_info_view where (name=? and surname=? and patronymic=? and birthday=?) or "+
                " inn=? or  (pass_serial=? and pass_number=?)";*/
        String query = "select * from client_info_view where (name=? /*and surname=? and patronymic=? and birthday=?*/)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            //ps.setString(1, "11");
            ps.setString(1, person.getName());
            /*ps.setString(2, "'"+person.getSurname()+"'");
            ps.setString(3, "'"+person.getPatronymic()+"'");
            ps.setString(4, person.getBirthday().toString());
            ps.setString(5, "'"+person.getInn()+"'");
            ps.setString(6, "'"+person.getPassSerial()+"'");
            ps.setString(7, "'"+person.getPassNumber()+"'");*/
            try (ResultSet answer = ps.executeQuery()){
                System.out.println(Charset.defaultCharset());
                if (answer.next()) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean addNewClient() {
        return false;
    }

    public boolean changeLoanInfo(LoanInfo curInfo, LoanInfo newInfo) {
        return false;
    }

    private String switchMethod(Person person, int searchType, String query){

        return "";
    }
    public ArrayList<LoanInfo> getClientInfo(Person person) {
        if (!isClientExists(person)) return null;
        String query = "select name, surname, patronymic, birthday, pass_serial, pass_number "+
            "init_amount, init_date, finish_date, balance, arrears, bank, currency "+
            "from client_info_view where (name=? and surname=? and patronymic=? and birthday =?) "+
            " or inn=? or  (pass_serial=? and pass_number=?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, person.getName());
            ps.setString(2, person.getSurname());
            ps.setString(3, person.getPatronymic());
            ps.setString(4, person.getBirthday().toString());
            ps.setString(5, person.getInn());
            ps.setString(6, person.getPassSerial());
            ps.setString(7, person.getPassNumber());
            try (ResultSet answer = ps.executeQuery()){
                while (answer.next()){
                    System.out.println(answer.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //jdbcTemplate = new JdbcTemplate(dataSource);
        //SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query);
        //SqlRowSetMetaData metaData = rowSet.getMetaData();
        //columnCount = metaData.getColumnCount();
        //columnNames = new ArrayList(columnCount);
        for (int i = 1; i <= columnCount; i++) {
            //columnNames.add(metaData.getColumnName(i));
        }
        //while (rowSet.next()) {
         //   ArrayList row = new ArrayList(columnCount);
         //   for (int i = 1; i <= columnCount; i++) {
                //row.add(rowSet.getString(i));
         //   }
            //data.add(row);
       // }
        return data;
    }
}
