package com.privat.bki.apiserver.spring.services.statistic;

import org.springframework.stereotype.Component;

import java.util.*;

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


    private double findAvgFromList(List<Integer> data){
        double avg = 0;

        for(int i=0; i< data.size(); i++){
            avg+=data.get(i);
        }
        return avg/data.size();
    }

    private double findHelixAngle(List<Integer> data, double avgValue, double avgCount){
        tmp=0;
        tmp2=0;
        for(int i=0; i< data.size(); i++){
            tmp+=(data.get(i)-avgValue)*(i-avgCount);
            tmp2+=Math.pow(i - avgCount, 2);
        }
        return tmp/tmp2;
    }

    private double findIntersectionWithAxis(double avgValue, double avgCount, double helixAngle) {
        return avgValue-helixAngle*avgCount;
    }

    private double findCorrelationCoefficient(double avgValue, double avgCount, double prognosticationPeriod){
        double correlation = tmp/prognosticationPeriod;
        correlation=correlation/(Math.sqrt(avgValue)*Math.sqrt(avgCount));
        return correlation;
    }


    private int calculatePrognosticationPeriod(List<Integer> years, int year){
        return year-Collections.min(years);
    }

    private double calculatePrognostication(List<Integer> yearList, List<Integer> dataList, int prognosticationYear){
        avgValue = findAvgFromList(dataList);
        avgCount = findAvgFromList(yearList);
        prognosticationPeriod = calculatePrognosticationPeriod(yearList, prognosticationYear);
        helixAngle = findHelixAngle(dataList, avgValue, avgCount);
        intersactionWithAxis = findIntersectionWithAxis(avgValue, avgCount, helixAngle);
        correlationCoefficient = findCorrelationCoefficient(avgValue,avgCount,prognosticationPeriod);
        return intersactionWithAxis + helixAngle*prognosticationPeriod;
    }

    public double calculatePrognosticationForBank(Map<String,List> statistics, String bankName, int prognosticationYear){
        List<Integer> yearList = new ArrayList<>();
        List dataList = new ArrayList<>();
        List<Map> statisticWithoutBankName = statistics.get(bankName);
        for(Map stat : statisticWithoutBankName){
            yearList.add((Integer) stat.get("year"));
            dataList.add(stat.get("count"));
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
