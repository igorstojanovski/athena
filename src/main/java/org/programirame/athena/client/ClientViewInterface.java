package org.programirame.athena.client;


import org.programirame.athena.models.Client;
import org.programirame.athena.models.ClientType;
import org.programirame.athena.models.Invoice;

import java.util.List;

public interface ClientViewInterface {

    void refreshInvoices(List<Invoice> invoices);

    String getClientParameter();

    void refreshClientInfo(Client client);
}
