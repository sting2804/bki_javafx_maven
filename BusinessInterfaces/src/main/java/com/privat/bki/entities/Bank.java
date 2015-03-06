package com.privat.bki.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Bank implements IDirectory{

    private String code;
    private String name;

    public Bank() {
    }

    public Bank(String code, String name) {
        this.code=code;
        this.name=name;
    }

    @Override
    @XmlElement
    public String getCode() {
        return code;
    }
    @Override
    public void setCode(String bankCode) {
        this.code = bankCode;
    }
    @Override
    @XmlElement
    public String getName() {
        return name;
    }
    @Override
    public void setName(String bankName) {
        this.name = bankName;
    }

    @Override
    public String toString() {
        return "Bank { " +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                " }";
    }
}
