package com.privat.bki.wrappers
import com.privat.bki.entities.Person

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlSeeAlso
/**
 * Created by sting on 3/4/15.
 */
@XmlRootElement(name = "clients")
@XmlSeeAlso(value = Person.class)
class ClientListWrapper {

    List <Person> clients

    ClientListWrapper() {
    }

    ClientListWrapper(List<Person> clients) {
        this.clients = clients
    }

    @XmlElement(name = "client")
    List<Person> getClients() {
        return clients
    }

    void setClients(List<Person> clients) {
        this.clients = clients
    }
}
