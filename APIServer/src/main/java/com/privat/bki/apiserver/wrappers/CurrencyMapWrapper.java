package com.privat.bki.apiserver.wrappers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * Created by sting on 3/4/15.
 */
@XmlRootElement(name = "currencies")
public class CurrencyMapWrapper {
    Map<String,String> currencies;

    public CurrencyMapWrapper() {
    }

    public CurrencyMapWrapper(Map<String, String> currencies) {
        this.currencies = currencies;
    }
    @XmlElement(name = "currency")
    public Map<String, String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String, String> currencies) {
        this.currencies = currencies;
    }
}
