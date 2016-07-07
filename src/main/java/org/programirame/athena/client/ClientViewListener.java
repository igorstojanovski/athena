package org.programirame.athena.client;

import org.programirame.athena.models.Client;

public interface ClientViewListener {
    void clientSelected(Client client);

    void viewInitialized(ClientView clientView);
}
