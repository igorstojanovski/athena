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
    private InvoiceService invoiceService;

    private ClientViewInterface clientView;

    @Autowired
    private ClientService clientService;

    @Override
    public void clientSelected(Client client) {
        List<Invoice> invoices = new ArrayList<>();
        try {
            invoices = invoiceService.getAllClientInvoices(client.getId());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        clientView.refreshInvoices(invoices);
    }

    @Override
    public void viewInitialized(ClientView clientView) {
        this.clientView = clientView;
        List<Client> clients = new ArrayList<>();
        try {
            clients = clientService.getAllClients();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        clientView.refreshClient(clients);
    }

    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }
}
