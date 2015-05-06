package com.privat.bki.apiserver.spring.services.statistic;

import com.privat.bki.business.entities.Bank;

import java.util.Map;

/**
 * Created by sting on 5/6/15.
 */
public interface StatisticDao {
    /**
     * вытягивает с сервера статистику по использованию банков в заданые года
     * @return возвращает мапку год-банк
     */
    Map<Integer,Bank> theMostPreferredBank(int... years);

    /**
     * вытягивает с сервера статистику по использованию банков для указанного года
     * @return возвращает мапку год-банк
     */
    Bank theMostPreferredBank(int year);

    /**
     * вытягивает с сервера статистику по самым берущим кредиты возрастам по годам
     * @return возвращает мапку классификация возраста-возраст
     */
    Map<String, Integer> theMostCreditAge(int... years);
    /**
     * вытягивает с сервера статистику по самым берущим кредиты возрастам за год
     * @return возвращает мапку классификация возраста-возраст
     */
    Integer theMostCreditAge(int year);

}
