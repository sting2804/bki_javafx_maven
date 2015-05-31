package com.privat.bki.desktopapp.utils;

import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.List;
import java.util.Map;

/**
 * Created by sting on 5/31/15.
 */
public class ChartDrawer {

    public static Chart drawStatisticForBankByYears(Map<String,List> statistic, String bankName, AreaChart<Number,Number> areaChart){
        List years = statistic.get("years");
        List count = statistic.get("count");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        areaChart.setTitle("Статистика для "+bankName);

        XYChart.Series series= new XYChart.Series();
        series.setName(bankName);
        for(int i=0; i<years.size(); i++){
            series.getData().add(new XYChart.Data(years.get(i), count.get(i)));
        }
        areaChart.getData().addAll(series);
        return areaChart;

        /*XYChart.Series seriesMay = new XYChart.Series();
        seriesMay.setName("May");
        seriesMay.getData().add(new XYChart.Data(1, 20));
        seriesMay.getData().add(new XYChart.Data(3, 15));
        seriesMay.getData().add(new XYChart.Data(6, 13));
        seriesMay.getData().add(new XYChart.Data(9, 12));
        seriesMay.getData().add(new XYChart.Data(12, 14));
        seriesMay.getData().add(new XYChart.Data(15, 18));
        seriesMay.getData().add(new XYChart.Data(18, 25));
        seriesMay.getData().add(new XYChart.Data(21, 25));
        seriesMay.getData().add(new XYChart.Data(24, 23));
        seriesMay.getData().add(new XYChart.Data(27, 26));
        seriesMay.getData().add(new XYChart.Data(31, 26));*/
    }
}
