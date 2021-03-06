package com.privat.bki.wrappers;
import com.privat.bki.entities.Person;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * Created by sting on 3/4/15.
 */
@XmlRootElement(name = "clients")
@XmlSeeAlso(value = {Person.class})
public class ClientListWrapper {

    List <Person> clients;

    public ClientListWrapper() {
    }

    public ClientListWrapper(List<Person> clients) {
        this.clients = clients;
    }

    @XmlElement(name = "client")
    public List<Person> getClients() {
        return clients;
    }

    public void setClients(List<Person> clients) {
        this.clients = clients;
    }
}
