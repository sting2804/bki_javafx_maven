package com.privat.bki.wrappers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * Created by sting on 3/4/15.
 */
@XmlRootElement(name = "banks")
public class BankMapWrapper {

    Map<String, String> banks;

    public BankMapWrapper() {
    }

    public BankMapWrapper(Map<String, String> banks) {
        this.banks = banks;
    }

    @XmlElement(name = "bank")
    public Map<String, String> getBanks() {
        return banks;
    }

    public void setBanks(Map<String, String> banks) {
        this.banks = banks;
    }
}
