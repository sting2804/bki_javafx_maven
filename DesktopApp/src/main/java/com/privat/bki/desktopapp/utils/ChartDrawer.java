package com.privat.bki.desktopapp.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;

import java.util.*;

public class ChartDrawer {

    private static List<String> ageCategories = new LinkedList<>();
    static {
        ageCategories.add("young");
        ageCategories.add("middle_I");
        ageCategories.add("middle_II");
        ageCategories.add("advanced");
        ageCategories.add("senium");
        ageCategories.add("long_liver");
    }

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
     * отрисовка статистики кредитируемых возрастов для указанного банка
     * @param statistic статистические данные
     * @param bankName название банка
     * @return возвращает графики
     */
    public static Chart drawStatisticForCreditAgesByBankAndYears(Map<String,List> statistic, String bankName){
        List<Map> ageStats = statistic.get(bankName);
        List<Integer> years = new ArrayList<>();
        List<String> ages = new ArrayList<>();
        for (Map agesInfo : ageStats) {
            years.add((Integer)agesInfo.get("year"));
            ages.add((String)agesInfo.get("age"));
        }
        final NumberAxis xAxis = new NumberAxis(Collections.min(years)-1,Collections.max(years)+1,1);
        ObservableList<String> observableAgesList = FXCollections.observableList(ageCategories);
        final CategoryAxis yAxis = new CategoryAxis(observableAgesList);
        xAxis.tickLabelRotationProperty().setValue(80);
        LineChart<Number,String> areaChart = new LineChart<>(xAxis, yAxis);
        areaChart.setTitle("Статистика для "+bankName);
        areaChart.setMaxSize(535,370);
        XYChart.Series series= new XYChart.Series<>();
        series.setName(bankName);
        for(int i=0; i<years.size(); i++){
            series.getData().add(new XYChart.Data(years.get(i), ages.get(i)));
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
    public static Chart drawPrognosticationForBankForYears(Map<String,List> statistic, String bankName, Map prognosticationMap){
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

    /**
     * отрисовка статистики вместе с прогнозом для возрастов
     * @param statistic статистические данные по указанному банку
     * @param bankName имя банка
     * @param prognosticationMap содержит год прогнозв и значение на этот год
     * @return возвращает график
     */
    public static Chart drawPrognosticationForCreaditAgeByBankForYears(Map<String,List> statistic, String bankName, Map prognosticationMap){
        List<Map> ageStats = statistic.get(bankName);
        List<Integer> years = new ArrayList<>();
        List<String> ages = new ArrayList<>();
        for (Map agesInfo : ageStats) {
            years.add((Integer)agesInfo.get("year"));
            ages.add((String)agesInfo.get("age"));
        }

        Integer prognosticationYear = (Integer) prognosticationMap.get("year");
        String prognosticationValue = (String) prognosticationMap.get("value");
        Integer latestStatisticYear = Collections.max(years);
        String latestStatisticValue = ages.get(years.indexOf(latestStatisticYear));

        final NumberAxis xAxis = new NumberAxis(Collections.min(years)-1,prognosticationYear+1,1);
        ObservableList<String> observableAgesList = FXCollections.observableList(ageCategories);
        final CategoryAxis yAxis = new CategoryAxis(observableAgesList);
        xAxis.tickLabelRotationProperty().setValue(80);
        LineChart<Number, String> areaChart = new LineChart<>(xAxis, yAxis);
        areaChart.setTitle("Статистика для "+bankName);
        areaChart.setMaxSize(535,370);
        XYChart.Series statisticSeries= new XYChart.Series();
        statisticSeries.setName("Статистика");
        for(int i=0; i<years.size(); i++){
            statisticSeries.getData().add(new XYChart.Data(years.get(i), ages.get(i)));
        }
        XYChart.Series prognosticationSeries= new XYChart.Series();
        prognosticationSeries.setName("Прогноз");
        prognosticationSeries.getData().add(new XYChart.Data(latestStatisticYear, latestStatisticValue));
        prognosticationSeries.getData().add(new XYChart.Data(prognosticationYear, prognosticationValue));

        areaChart.getData().addAll(statisticSeries, prognosticationSeries);
        return areaChart;

    }
}
