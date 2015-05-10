package com.privat.bki.apiserver.spring.services.loans;

import com.privat.bki.business.entities.Bank;
import com.privat.bki.business.entities.Currency;
import com.privat.bki.business.entities.LoanInfo;
import com.privat.bki.business.entities.Person;

import java.util.List;
import java.util.Map;


/**
 * Created by sting on 1/23/15.
 */
public interface LoanInfoDao {
    List<LoanInfo> getRecord(int id);
    /**
     * получение всех записей из базы
     * @return возвращает список со всеми записями из бд, полученными из view
     */
    List<LoanInfo> getAllRecords();

    /**
     * получение информации о существовании конкретного клиента
     * @param person параметр для поиска
     * @return возвращает id  если клиент существует и -1 если такой клиент не найден в бд
     */
    Integer isClientExists(Person person);

    /**
     * добавляет только информацию для существующего клиента
     *
     * @param info данные для дабавления в таблицу loan
     * @param clientId указывает id клиента, котрому будет добавлена информация
     * @return возвращает true в случае успеха и false в случае неудачи
     */
    Boolean addNewInfo(LoanInfo info, int clientId);

    /**
     * Обновление информации о клиенте и кредите
     * Вызывает updatePerson() для обновления информации в таблице client,
     * после чего обновляет данные в таблице loan
     * @param infoId id таблицы loan, данные которой нужно изменить
     * @param clientId id таблицы client, данные которой нужно изменить
     * @param newInfo содержит обновлённую информацию
     * @return возвращает true в случае успеха и false в случае неудачи
     */
    Boolean changeInfo(int infoId, int clientId, LoanInfo newInfo);

    /**
     * Получение кредитов указанного клиента
     * @param person основная информация о клиенте
     * @return возвращает список кредитов
     */
    List<LoanInfo> getPersonInfo(Person person);

    /**
     * Получает список банков из бд
     * @return возвращает map <код, имя> банка
     */
    Map<String, String> getBanksMap();

    /**
     * Получает список валют из бд
     * @return возвращает map <код, имя> валюты
     */
    Map<String, String> getCurrenciesMap();

    /**
     * добавляет запись в таблицы client и loan.
     * Вызывает метод addNewPerson() для добавления информации о клиенте
     * и метод addNewInfo() для добавления информации о кредите
     * @param info входной параметр, содержащий информацию новом клиенте и кредите
     * @return Возвращает true в случае успешного добавления и false в случае ошибки
     */
    Boolean addNewClient(LoanInfo info);

    /**
     * Добавление новой валюты в бд
     * @param currency сожержит код и название валюты
     * @return Возвращает true в случае успешного добавления и false в случае ошибки
     */
    Boolean addNewCurrency(Currency currency);

    /**
     * Добавление нового банка в бд
     * @param bank сожержит код и название валюты
     * @return Возвращает true в случае успешного добавления и false в случае ошибки
     */
    Boolean addNewBank(Bank bank);

}
