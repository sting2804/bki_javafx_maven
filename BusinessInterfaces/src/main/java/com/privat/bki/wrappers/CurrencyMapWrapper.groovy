package com.privat.bki.wrappers
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
/**
 * Created by sting on 3/4/15.
 */
@XmlRootElement(name = "currencies")
class CurrencyMapWrapper {
    Map<String,String> currencies

    CurrencyMapWrapper() {
    }

    CurrencyMapWrapper(Map<String, String> currencies) {
        this.currencies = currencies
    }
    @XmlElement(name = "currency")
    Map<String, String> getCurrencies() {
        return currencies
    }

    void setCurrencies(Map<String, String> currencies) {
        this.currencies = currencies
    }
}
