package com.privat.bki.business.wrappers
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
/**
 * Created by sting on 3/4/15.
 */
@XmlRootElement(name = "banks")
class BankMapWrapper {

    Map<String, String> banks

    BankMapWrapper() {
    }

    BankMapWrapper(Map<String, String> banks) {
        this.banks = banks
    }

    @XmlElement(name = "bank")
    Map<String, String> getBanks() {
        return banks
    }

    void setBanks(Map<String, String> banks) {
        this.banks = banks
    }
}
