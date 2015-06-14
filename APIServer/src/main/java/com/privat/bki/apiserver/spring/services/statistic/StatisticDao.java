package com.privat.bki.apiserver.spring.services.statistic;

import com.privat.bki.business.entities.Bank;

import java.util.List;
import java.util.Map;

/**
 * Created by sting on 5/6/15.
 */
public interface StatisticDao {
    /**
     * вытягивает с сервера статистику по использованию банков в заданые года
     * @return возвращает мапку год-банк
     */
    List<Map> theMostPreferredBank(int... years);

    /**
     * вытягивает с сервера статистику по использованию банков для указанного года
     * @return возвращает мапку год-банк
     */
    Bank theMostPreferredBank(int year);

    /**
     * вытягивает с сервера статистику по самым берущим кредиты возрастам по годам
     * @return возвращает мапку классификация возраста-возраст
     */
    List<Map> theMostCreditAge(String bankName, int... years);
    /**
     * вытягивает с сервера статистику по самым берущим кредиты возрастам за год
     * @return возвращает мапку классификация возраста-возраст
     */
    String theMostCreditAge(String bankName, int year);

    /**
     * получение количества кредитов в указанном банке за заданные года
     * @return map с годами и значением, типа
     * Map<String, List<String>>
     */
    Map<String, List> bankCreditStatisticByYears(String bankName, Integer ... years);

    /**
     * получение количества кредитов в указанном банке за все года
     * @return map с годами и значением, типа
     * Map<String, List<String>>
     */
    Map<String, List> bankCreditStatisticByYears(String bankName);

    Integer bankCreditStatisticByYear(String bankName, Integer year);

    /**
     * получение всех готов, в которые у банка брали кредиты
     * @param bankName имя бака
     * @return возвращает список годов в формате yyyy
     */
    List<Integer> getCreditYearsOfBank(String bankName);
}
