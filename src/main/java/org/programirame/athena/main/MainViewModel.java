package org.programirame.athena.main;

import com.vaadin.spring.annotation.SpringComponent;
import org.programirame.athena.models.Client;
import org.programirame.athena.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringComponent
public class MainViewModel {


    @Autowired
    ClientService clientService;

    private List<Client> allClients;

    @PostConstruct
    public void init() {

    }

    public List<Client> getAllClients() {
        setAllClients(clientService.getAllClients());
        return allClients;
    }

    public void setAllClients(List<Client> allClients) {
        this.allClients = allClients;
    }
}
