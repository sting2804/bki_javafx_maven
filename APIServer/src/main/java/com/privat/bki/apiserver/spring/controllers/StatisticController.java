package com.privat.bki.apiserver.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by sting on 5/6/15.
 */
@Controller
@RequestMapping(value = "/statistic")
public class StatisticController {

    /**
     *
     * @param years строка вида ../preferredBank?years=2013,3013,...
     * @return
     */
    @RequestMapping(value = "/preferredBank", method = GET)
    @ResponseBody
    public Map<String,String> getTheMostPreferredBank(String years){
        //сделать сплит по запятым, преобразовать к инту и вызвать метод сервиса
        return new HashMap<>();
    }
}
