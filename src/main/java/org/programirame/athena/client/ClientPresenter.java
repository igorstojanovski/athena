package org.programirame.athena.client;


import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.programirame.athena.model.Clients;
import org.programirame.athena.model.Invoice;
import org.programirame.athena.models.address.Address;
import org.programirame.athena.service.AddressService;
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

    @Autowired
    AddressService addressService;

    @Override
    public void viewInitialized(ClientViewInterface clientView) {

        String clientId = clientView.getClientParameter().split("/")[0];
        Clients client = clientService.getClient(clientId);

        clientAggregate.setSelectedClient(clientId);


        List<Invoice> invoices = clientAggregate.getSelectedClient().getInvoice();

        clientView.refreshInvoices(invoices);
        clientView.refreshClientInfo(client);

        List<Address> addresses = new ArrayList<>();

//        addresses = addressService.getAddresses(clientId);



        System.out.println("Number of addresses: "+addresses.size());

        clientView.refreshContactInfo(addresses);
    }
}
