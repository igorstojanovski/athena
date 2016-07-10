package org.programirame.athena.client;


import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.programirame.athena.models.Client;
import org.programirame.athena.models.Invoice;
import org.programirame.athena.service.ClientService;
import org.programirame.athena.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
@UIScope
class ClientPresenter implements ClientViewListener {

    @Autowired
    ClientViewAggregate clientAggregate;

    @Autowired
    private InvoiceService invoiceService;

    private ClientViewInterface clientView;

    @Autowired
    private ClientService clientService;

    @Override
    public void viewInitialized(ClientViewInterface clientView) {

        long clientId = Long.valueOf(clientView.getClientParameter().split("/")[0]);
        Client client = clientService.getClient(clientId);

        clientAggregate.setSelectedClient(clientId);

        List<Invoice> invoices = new ArrayList<>();

        try {
            invoices = invoiceService.getAllClientInvoices(clientId);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        clientView.refreshInvoices(invoices);
        clientView.refreshClientInfo(client);
    }
}
