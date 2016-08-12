package org.programirame.athena.client;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.programirame.athena.model.Clients;
import org.programirame.athena.models.Client;
import org.programirame.athena.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
public class ClientViewAggregate {

    @Autowired
    ClientService clientService;
    private Clients selectedClient;

    public void setSelectedClient(String clientId) {

        selectedClient = clientService.getClient(clientId);
    }

    public Clients getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Clients selectedClient) {
        this.selectedClient = selectedClient;
    }
}
