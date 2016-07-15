package org.programirame.athena.client;


import org.programirame.athena.models.Client;
import org.programirame.athena.models.Invoice;
import org.programirame.athena.models.address.Address;

import java.util.List;

public interface ClientViewInterface {

    void refreshInvoices(List<Invoice> invoices);

    void refreshContactInfo(List<Address> addresses);

    String getClientParameter();

    void refreshClientInfo(Client client);
}
