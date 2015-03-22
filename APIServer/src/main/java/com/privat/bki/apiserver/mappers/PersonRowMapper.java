package com.privat.bki.apiserver.mappers;

import com.privat.bki.entities.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sting on 3/2/15.
 */
public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int ii) throws SQLException {
        Person person;
        person = new Person();
        int i = 1;
        person.setName(rs.getString(i++));
        person.setSurname(rs.getString(i++));
        person.setPatronymic(rs.getString(i++));
        person.setBirthday(rs.getDate(i++).toLocalDate());
        person.setInn(rs.getString(i++));
        person.setPassSerial(rs.getString(i++));
        person.setPassNumber(rs.getString(i++));
        person.setId(rs.getInt(i));

        return person;
    }
}
