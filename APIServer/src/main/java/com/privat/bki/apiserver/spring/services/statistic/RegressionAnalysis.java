package com.privat.bki.apiserver.spring.services.statistic;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    private double[] convertListOfIntToDoubleArray(List<Integer> yearList) {
        double [] yearsArray = new double[yearList.size()];
        int i=0;
        for(int val : yearList){
            yearsArray[i++]=val;
        }
        return  yearsArray;
    }
}
