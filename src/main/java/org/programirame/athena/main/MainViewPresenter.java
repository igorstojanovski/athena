package org.programirame.athena.main;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;
import org.programirame.athena.models.Client;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UIScope
@SpringComponent
public class MainViewPresenter implements MainViewListenerInterface {

    @Autowired
    MainViewModel model;

    @Override
    public void viewInitialized(MainViewInterface mainView) {
        List<Client> clients = model.getAllClients();
        mainView.refreshClientsCombo(clients);
    }

    @Override
    public void clientSelected(Client client) {
        UI.getCurrent().getNavigator().navigateTo("client/"+client.getId());
    }
}
