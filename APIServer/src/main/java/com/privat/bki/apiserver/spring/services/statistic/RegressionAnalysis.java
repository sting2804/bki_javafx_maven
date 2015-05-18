package com.privat.bki.apiserver.spring.services.statistic;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sting on 5/16/15.
 */
@Component
public class RegressionAnalysis {
    private double
            avgValue,       //avenger of list values
            avgCount,       //avenger of list iteration numbers
            prognosticationPeriod, //X
            correlationCoefficient,
            helixAngle, //b
            intersactionWithAxis, //a
            tmp,tmp2; //??


    public Map<String,Double> findAvgFromList(List<Integer> values){
        double avgValue = 0;
        double avgCount = 0;
        Map <String,Double> resultMap = new LinkedHashMap<>(2);

        for(int i=0; i< values.size(); i++){
            avgValue+=values.get(i);
            avgCount+=i;
        }
        resultMap.put("avgValue",avgValue/values.size());
        resultMap.put("avgCount",avgCount/values.size());
        return resultMap;
    }

    public double findhelixAngle(List<Integer> data, double avgValue, double avgCount){
        double tmp=0,tmp2=0;
        for(int i=0; i< data.size(); i++){
            tmp+=(data.get(i)-avgValue)*(i-avgCount);
            tmp2+=Math.pow(i - avgCount, 2);
        }
        return tmp/tmp2;
    }

    public double findIntersactionWithAxis(double avgValue, double avgCount, double helixAngle) {
        return avgValue-helixAngle*avgCount;
    }

    public double findCorrelationCoefficient(double tmp, double avgValue, double avgCount, double valuesSize){
        double correlation = tmp/valuesSize;
        correlation/=Math.sqrt(avgValue)*Math.sqrt(avgCount);
        return correlation;
    }

    public int calculatePrognosticationPeriod(int year){
        return 0;
    }

    public double calculatePrognostication(List<Integer> yearList, List<Integer> dataList, int prognosticationYear){
        //avgValue = findAvgFromList(dataList);
        return 0;
    }
    public double calculatePrognosticationForBank(List<Map> statistics, int prognosticationYear){
        List<Integer> yearList = new ArrayList<>();
        List dataList = new ArrayList<>();
        for(Map stat : statistics){
            yearList.add((Integer) stat.get("year"));
            dataList.add(stat.get("bank"));
        }
        return calculatePrognostication(yearList, dataList, prognosticationYear);
    }

    public double calculatePrognosticationForAge(List<Map> statistics, int prognosticationYear){
        List<Integer> yearList = new ArrayList<>();
        List dataList = new ArrayList<>();
        for(Map stat : statistics){
            yearList.add((Integer) stat.get("year"));
            dataList.add(stat.get("age"));
        }
        return calculatePrognostication(yearList, dataList, prognosticationYear);
    }

}
