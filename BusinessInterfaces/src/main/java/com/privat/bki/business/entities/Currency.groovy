package com.privat.bki.business.entities
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class Currency implements IDirectory{

    private String name
    private String code

    Currency() {
    }

    Currency(String code, String name) {
        this.code = code
        this.name = name
    }

    @Override
    @XmlElement
    String getName() {
        return name
    }
    @Override
    void setName(String name) {
        this.name = name
    }
    @Override
    @XmlElement
    String getCode() {
        return code
    }
    @Override
    void setCode(String code) {
        this.code = code
    }

    @Override
    String toString() {
        return "Currency{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}'
    }
}
