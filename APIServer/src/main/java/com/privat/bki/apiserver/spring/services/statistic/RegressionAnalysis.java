package com.privat.bki.apiserver.spring.services.statistic;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sting on 5/16/15.
 */
public class RegressionAnalysis {
    private double
            avgValue,       //avenger of list values
            avgCount,       //avenger of list iteration numbers
            prognosticationPeriod, //X
            correlationCoefficient, //Y
            helixAngle, //b
            intersactionWithAxis, //a
            tmp,tmp2; //??

    private List<Double> values;

    public RegressionAnalysis(List<Double> values) {
        this.values = values;
    }

    public Map<String,Double> findAvgOnList(List<Double> values){
        avgValue = 0;
        avgCount = 0;
        Map <String,Double> resultMap = new LinkedHashMap<>(2);

        for(int i=0; i< values.size(); i++){
            avgValue+=values.get(i);
            avgCount+=i;
        }
        resultMap.put("avgValue",avgValue/values.size());
        resultMap.put("avgCount",avgCount/values.size());
        return resultMap;
    }


}
