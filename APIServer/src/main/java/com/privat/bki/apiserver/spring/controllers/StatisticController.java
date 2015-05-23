package com.privat.bki.apiserver.spring.controllers;

import com.privat.bki.apiserver.spring.services.statistic.StatisticService;
import com.privat.bki.business.entities.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by sting on 5/6/15.
 */
@Controller
@RequestMapping(value = "/statistic")
public class StatisticController {

    @Qualifier("statisticService")
    @Autowired
    StatisticService statisticService;

    /**
     * статистика самых предпочитаемых банков по годам
     * @param years строка вида ../preferredBank?years=2013,3013,...
     * @return
     */
    @RequestMapping(value = "/preferredBank", method = GET, params = "years")
    @ResponseBody
    public List<Map> getTheMostPreferredBank(@RequestParam(value = "years") String years){
        //сделать сплит по запятым, преобразовать к инту и вызвать метод сервиса
        String[] splitedYears = years.split(",");
        int [] integerYears = new int[splitedYears.length];
        int i=0;
        try {
            for (String year : splitedYears) {
                integerYears[i++] = Integer.parseInt(year);
            }
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
        return statisticService.getTheMostPreferredBank(integerYears);
    }

    /**
     * статистика самых кредитируемых возрастов по годам
     * @param years строка вида ../creditAge?years=2013,3013,...
     * @return
     */
    @RequestMapping(value = "/creditAge", method = GET, params = "years")
    @ResponseBody
    public List<Map> getTheMostCreditAge(@RequestParam(value = "years") String years){
        String[] splitedYears = years.split(",");
        int [] integerYears = new int[splitedYears.length];
        int i=0;
        try {
            for (String year : splitedYears) {
                integerYears[i++] = Integer.parseInt(year);
            }
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
        return statisticService.getTheMostCreditAge(integerYears);
    }

    /**
     * статистика количества кредитов в указанном банке по годам
     * ../forBank?bankName=bankName&years=2013,3013,...
     * @param years строка вида years=2013,3013,...
     * @param bankName имя банка для статистики
     * @return
     */
    @RequestMapping(value = "/forBank", method = GET, params = {"bankName"})
    @ResponseBody
    public Map<String, List> getStatisticByBankAndYears(@RequestParam("bankName") String bankName,
                                                        @RequestParam(value = "years", required = false)String years){
        if(years != null && !years.equals("")) {
            String[] splitedYears = years.split(",");
            Integer[] integerYears = new Integer[splitedYears.length];
            int i = 0;
            try {
                for (String year : splitedYears) {
                    integerYears[i++] = Integer.parseInt(year);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return statisticService.getStatisticByBankAndYears(bankName, integerYears);
        } else{
            return statisticService.getStatisticByBankAndYears(bankName);
        }
    }

    @RequestMapping(value = "/prognos/forBank", method = GET, params = {"bankName","prognosticationYear"})
    @ResponseBody
    public double getPrognosticationForBank(@RequestParam("bankName") String bankName,
                                            @RequestParam("prognosticationYear") String prognosticationYear){
        Integer year=0;
        try {
            year = Integer.parseInt(prognosticationYear);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        List<Integer> res = statisticService.getCreditYearsOfBank(bankName);
        Integer [] bankYears = new Integer[res.size()];
        for(int i=0; i<res.size(); i++){
            bankYears[i]=res.get(i);
        }
        return statisticService.calculatePrognosticationForBank(
                statisticService.getStatisticByBankAndYears(
                        bankName, bankYears
                ), bankName, year);
    }

}
