package org.programirame.athena.main;

import org.programirame.athena.models.Client;

public interface MainViewListenerInterface {
    void viewInitialized(MainViewInterface mainView);

    void clientSelected(Client client);
}
