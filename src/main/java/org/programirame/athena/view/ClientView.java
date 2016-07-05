package org.programirame.athena.view;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;
import org.programirame.athena.models.Client;
import org.programirame.athena.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;
import java.util.List;

@SpringView(name = "client")
public class ClientView extends VerticalLayout implements View {

    @Autowired
    ClientService clientService;

    @PostConstruct
    public void init() throws URISyntaxException {

        List<Client> clients = clientService.getAllClients();
        BeanItemContainer<Client> clientsContainer = new BeanItemContainer<>(Client.class, clients);

        ComboBox clientCombo = new ComboBox("Clients", clientsContainer);
        clientCombo.setItemCaptionPropertyId("name");

        addComponent(clientCombo);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        System.out.println("Entered.");
    }
}
