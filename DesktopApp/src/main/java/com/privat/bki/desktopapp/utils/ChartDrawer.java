package com.privat.bki.desktopapp.utils;

import javafx.scene.Scene;
import javafx.scene.chart.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ChartDrawer {

    public static AreaChart<Number,Number> drawStatisticForBankByYears(Map<String,List> statistic, String bankName){
        List<Map<String,Integer>> bankInfo = statistic.get(bankName);
        List<Integer> years = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for (Map<String, Integer> aBankInfo : bankInfo) {
            years.add(aBankInfo.get("year"));
            count.add(aBankInfo.get("count"));
        }
        final NumberAxis xAxis = new NumberAxis(Collections.min(years),Collections.max(years),1);
        final NumberAxis yAxis = new NumberAxis(0,Collections.max(count),1);
        AreaChart<Number,Number> areaChart = new AreaChart<>(xAxis, yAxis);
        areaChart.setTitle("Статистика для "+bankName);
        areaChart.setMaxSize(520,320);
        XYChart.Series series= new XYChart.Series();
        series.setName(bankName);
        for(int i=0; i<years.size(); i++){
            series.getData().add(new XYChart.Data(years.get(i), count.get(i)));
        }
        areaChart.getData().addAll(series);
        return areaChart;

    }
}
