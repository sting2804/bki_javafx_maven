package com.privat.bki.entities

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class Bank implements IDirectory{

    private String code
    private String name

    Bank() {
    }

    Bank(String code, String name) {
        this.code=code
        this.name=name
    }

    @Override
    @XmlElement
    String getCode() {
        return code
    }
    @Override
    void setCode(String bankCode) {
        this.code = bankCode
    }
    @Override
    @XmlElement
    String getName() {
        return name
    }
    @Override
    void setName(String bankName) {
        this.name = bankName
    }

    @Override
    String toString() {
        return "Bank { " +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                " }"
    }
}
