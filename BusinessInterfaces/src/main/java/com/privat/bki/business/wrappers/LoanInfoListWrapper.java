package com.privat.bki.business.wrappers;

import com.privat.bki.business.entities.LoanInfo;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType( XmlAccessType.NONE )
@XmlRootElement(name = "summary-information")
@XmlSeeAlso(value = LoanInfo.class)
public class LoanInfoListWrapper {

    private List<LoanInfo> clients;

    public LoanInfoListWrapper() {
    }

    public LoanInfoListWrapper(List<LoanInfo> clients) {
        this.clients = clients;
    }
    @XmlElement(name = "information")
    public List<LoanInfo> getClients() {
        return clients;
    }

    public void setClients(List<LoanInfo> clients) {
        this.clients = clients;
    }
}
