package com.privat.bki.desktopapp.utils;

import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by sting on 5/31/15.
 */
public class ChartDrawer {

    public static Chart drawStatisticForBankByYears(Map<String,List> statistic, String bankName, AreaChart<Number,Number> areaChart){
        List<Map<String,Integer>> bankInfo = statistic.get(bankName);
        List<Integer> years = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for(int i=0; i < bankInfo.size(); i++){
            years.add(bankInfo.get(i).get("year"));
            count.add(bankInfo.get(i).get("count"));
        }
        final NumberAxis xAxis = new NumberAxis(Collections.min(years),Collections.max(years),1);
        final NumberAxis yAxis = new NumberAxis(0,Collections.max(count),0.1);
        //areaChart = new AreaChart<Number, Number>(xAxis,yAxis);
        areaChart.setTitle("Статистика для "+bankName);

        XYChart.Series series= new XYChart.Series();
        series.setName(bankName);
        for(int i=0; i<years.size(); i++){
            series.getData().add(new XYChart.Data(years.get(i), count.get(i)));
        }
        areaChart.getData().addAll(series);
        return areaChart;

    }
}
