package com.privat.bki.wrappers
import com.privat.bki.entities.LoanInfo

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlSeeAlso
/**
 * Created by sting on 3/4/15.
 */
@XmlRootElement(name = "summary-information")
@XmlSeeAlso(value = LoanInfo.class)
class LoanInfoListWrapper {

    List<LoanInfo> clients

    LoanInfoListWrapper() {
    }

    LoanInfoListWrapper(List<LoanInfo> clients) {
        this.clients = clients
    }
    @XmlElement(name = "information")
    List<LoanInfo> getClients() {
        return clients
    }

    void setClients(List<LoanInfo> clients) {
        this.clients = clients
    }
}
