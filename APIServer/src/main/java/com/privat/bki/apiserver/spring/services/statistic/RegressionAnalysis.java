package com.privat.bki.apiserver.spring.services.statistic;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RegressionAnalysis {

    private RegressionModel regressionModel;

    public RegressionAnalysis() {
        regressionModel = new LinearRegressionModel();
    }

    public double calculatePrognosticationForBank(Map<String,List> statistics,
                                                  String bankName, int prognosticationYear) {
        List<Integer> yearList = new ArrayList<>();
        List dataList = new ArrayList<>();
        List<Map> statisticWithoutBankName = statistics.get(bankName);
        try {
            for (Map stat : statisticWithoutBankName) {
                yearList.add((Integer) stat.get("year"));
                dataList.add(stat.get("count"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        double [] yearsArray = convertListOfIntToDoubleArray(yearList);
        double [] dataArray = convertListOfIntToDoubleArray(dataList);
        regressionModel.setXValues(yearsArray);
        regressionModel.setYValues(dataArray);
        regressionModel.compute();
        return regressionModel.evaluateAt(prognosticationYear);
    }

    public String calculatePrognosticationForClientAgesByBank(Map<String,List> statistics,
                                                  String bankName, int prognosticationYear) {
        List<Integer> yearList = new ArrayList<>();
        List dataList = new ArrayList<>();
        List<Map> statisticWithoutBankName = statistics.get(bankName);
        try {
            for (Map stat : statisticWithoutBankName) {
                yearList.add((Integer) stat.get("year"));
                dataList.add(stat.get("age"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        double [] yearsArray = convertListOfIntToDoubleArray(yearList);
        double [] dataArray = convertListOfAgesToDoubleArray(dataList);
        regressionModel.setXValues(yearsArray);
        regressionModel.setYValues(dataArray);
        regressionModel.compute();
        return doubleValueToAgeCategory(regressionModel.evaluateAt(prognosticationYear));
    }

    private double[] convertListOfIntToDoubleArray(List<Integer> yearList) {
        double [] yearsArray = new double[yearList.size()];
        int i=0;
        for(int val : yearList){
            yearsArray[i++]=val;
        }
        return  yearsArray;
    }

    private static List<String> ageCategories = new LinkedList<>();
    static {
        ageCategories.add("young");
        ageCategories.add("middle_I");
        ageCategories.add("middle_II");
        ageCategories.add("advanced");
        ageCategories.add("senium");
        ageCategories.add("long_liver");
    }
    private double[] convertListOfAgesToDoubleArray(List <String> data){
        double [] dataArray = new double[data.size()];
        int i=0;
        for(String s : data){
            dataArray[i++] = ageCategories.indexOf(s)+1;
        }
        return dataArray;
    }

    private String doubleValueToAgeCategory(double value){
        int roundedValue = (int) Math.round(value);
        return ageCategories.get(roundedValue);
    }
}
