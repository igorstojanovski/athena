package org.programirame.athena.client;


import org.programirame.athena.model.Clients;
import org.programirame.athena.model.Invoice;
import org.programirame.athena.models.address.Address;

import java.util.List;

public interface ClientViewInterface {

    void refreshInvoices(List<Invoice> invoices);

    void refreshContactInfo(List<Address> addresses);

    String getClientParameter();

    void refreshClientInfo(Clients client);
}
