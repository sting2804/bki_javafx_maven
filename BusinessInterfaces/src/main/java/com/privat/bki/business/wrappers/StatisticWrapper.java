package com.privat.bki.business.wrappers;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.Map;

public class StatisticWrapper {
    private Map<String, List> stat;

    public StatisticWrapper() {
    }

    public StatisticWrapper(Map<String, List> stat) {
        this.stat = stat;
    }

    @XmlElement(name = "statistic")
    public Map<String, List> getStat() {
        return stat;
    }

    public void setStat(Map<String, List> stat) {
        this.stat = stat;
    }
}
