package org.programirame.athena.client;


import org.programirame.athena.models.Client;
import org.programirame.athena.models.Invoice;

import java.util.List;

public interface ClientViewInterface {

    void refreshInvoices(List<Invoice> invoices);

    void refreshClient(List<Client> clients);
}
