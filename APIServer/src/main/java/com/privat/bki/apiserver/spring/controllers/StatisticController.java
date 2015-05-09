package com.privat.bki.apiserver.spring.controllers;

import com.privat.bki.apiserver.spring.services.statistic.StatisticService;
import com.privat.bki.business.entities.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
     *
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
        return statisticService.getTheMostCreditAge(integerYears);
    }


}
