package org.programirame.athena.client;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.programirame.athena.models.Client;
import org.programirame.athena.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
public class ClientViewAggregate {

    @Autowired
    ClientService clientService;
    private Client selectedClient;

    public void setSelectedClient(long clientId) {

        selectedClient = clientService.getClient(clientId);
    }

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }
}
