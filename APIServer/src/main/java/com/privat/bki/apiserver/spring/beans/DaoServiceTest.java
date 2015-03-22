package com.privat.bki.apiserver.spring.beans;

import com.privat.bki.apiserver.spring.beans.dao.IBkiDao;
import com.privat.bki.entities.Bank;
import com.privat.bki.entities.Currency;
import com.privat.bki.entities.LoanInfo;
import com.privat.bki.entities.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DaoServiceTest {

    DaoService service;
    @Mock
    IBkiDao iBkiDao;

    @Before
    public void setUp() throws Exception {
        service = new DaoService();
    }

    @Test
    public void testGetRecord() throws Exception {
        List <LoanInfo> expectedResult = new ArrayList<>();
        Bank b = new Bank("1","1");
        Currency c = new Currency("1","1");
        Person p = new Person(1,"1","1","1",LocalDate.MIN,"1","1","1");
        expectedResult.add(new LoanInfo(1,1.0, LocalDate.MIN,LocalDate.MAX,1.0,true,b,c,p));
        when(iBkiDao.getRecord(0)).thenReturn(expectedResult);
        assertEquals(expectedResult,service.getRecord(0));
    }

    @Test
    public void testListAll() throws Exception {

    }

    @Test
    public void testListSpecificInfo() throws Exception {

    }

    @Test
    public void testIsClientExists() throws Exception {

    }

    @Test
    public void testAddInfo() throws Exception {

    }

    @Test
    public void testAddNewClient() throws Exception {

    }

    @Test
    public void testModify() throws Exception {

    }

    @Test
    public void testGetBanksMap() throws Exception {

    }

    @Test
    public void testGetCurrenciesMap() throws Exception {

    }

    @Test
    public void testAddBank() throws Exception {

    }

    @Test
    public void testAddCurrency() throws Exception {

    }
}