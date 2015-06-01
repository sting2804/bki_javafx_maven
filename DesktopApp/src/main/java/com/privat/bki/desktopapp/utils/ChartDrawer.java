package com.privat.bki.desktopapp.utils;

import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ChartDrawer {

    /**
     * отрисовка статистики для указанного банка
     * @param statistic статистические данные
     * @param bankName название банка
     * @return возвращает графики
     */
    public static Chart drawStatisticForBankByYears(Map<String,List> statistic, String bankName){
        List<Map<String,Integer>> bankInfo = statistic.get(bankName);
        List<Integer> years = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for (Map<String, Integer> aBankInfo : bankInfo) {
            years.add(aBankInfo.get("year"));
            count.add(aBankInfo.get("count"));
        }
        final NumberAxis xAxis = new NumberAxis(Collections.min(years)-1,Collections.max(years)+1,1);
        final NumberAxis yAxis = new NumberAxis(0,Collections.max(count)+1,1);
        xAxis.tickLabelRotationProperty().setValue(80);
        LineChart<Number,Number> areaChart = new LineChart<>(xAxis, yAxis);
        areaChart.setTitle("Статистика для "+bankName);
        areaChart.setMaxSize(535,370);
        XYChart.Series<Number,Number> series= new XYChart.Series<>();
        series.setName(bankName);
        for(int i=0; i<years.size(); i++){
            series.getData().add(new XYChart.Data(years.get(i), count.get(i)));
        }
        areaChart.getData().addAll(series);
        return areaChart;

    }

    /**
     * отрисовка статистики вместе с прогнозом
     * @param statistic статистические данные по указанному банку
     * @param bankName имя банка
     * @param prognosticationMap содержит год прогнозв и значение на этот год
     * @return возвращает график
     */
    public static Chart drawPrognosticationForBankForYears(Map<String,List> statistic, String bankName, Map<String, Number> prognosticationMap){
        List<Map<String,Integer>> bankInfo = statistic.get(bankName);
        List<Integer> years = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for (Map<String, Integer> aBankInfo : bankInfo) {
            years.add(aBankInfo.get("year"));
            count.add(aBankInfo.get("count"));
        }

        Integer prognosticationYear = (Integer) prognosticationMap.get("year");
        Double prognosticationValue = (Double) prognosticationMap.get("value");
        Integer latestStatisticYear = years.get(years.size()-1);
        Integer latestStatisticValue = count.get(count.size()-1);

        final NumberAxis xAxis = new NumberAxis(Collections.min(years)-1,prognosticationYear+1,1);
        final NumberAxis yAxis = new NumberAxis(0,Collections.max(count)+1,1);
        xAxis.tickLabelRotationProperty().setValue(80);
        LineChart<Number,Number> areaChart = new LineChart<>(xAxis, yAxis);
        areaChart.setTitle("Статистика для "+bankName);
        areaChart.setMaxSize(535,370);
        XYChart.Series<Number,Number> statisticSeries= new XYChart.Series();
        statisticSeries.setName("Статистика");
        for(int i=0; i<years.size(); i++){
            statisticSeries.getData().add(new XYChart.Data(years.get(i), count.get(i)));
        }
        XYChart.Series<Number,Number> prognosticationSeries= new XYChart.Series();
        prognosticationSeries.setName("Прогноз");
        prognosticationSeries.getData().add(new XYChart.Data(latestStatisticYear, latestStatisticValue));
        prognosticationSeries.getData().add(new XYChart.Data(prognosticationYear, prognosticationValue));

        areaChart.getData().addAll(statisticSeries, prognosticationSeries);
        return areaChart;

    }
}
