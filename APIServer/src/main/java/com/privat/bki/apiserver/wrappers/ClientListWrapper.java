package com.privat.bki.apiserver.wrappers;

import com.privat.bki.apiserver.entities.Client;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * Created by sting on 3/4/15.
 */
@XmlRootElement(name = "clients")
@XmlSeeAlso(value = {Client.class})
public class ClientListWrapper<Client> {
    int count;
    List <Client> clients;

    public ClientListWrapper() {
    }

    public ClientListWrapper(List<Client> clients) {
        this.clients = clients;
        count = clients.size();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    @XmlElement(name = "client")
    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
