package org.programirame.athena.search;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.programirame.athena.model.Clients;
import org.programirame.athena.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@UIScope
@SpringComponent
public class SearchViewPresenter implements SearchViewListener {

    private SearchViewInterface searchViewInterface;

    @Autowired
    ClientService clientService;


    @Override
    public void search(String searchQuery) {

        List<Clients> clients = clientService.searchClients(searchQuery);
        searchViewInterface.refreshResultsTable(clients);
    }

    @Override
    public void viewInitialized(SearchViewInterface searchViewInterface) {
        this.searchViewInterface = searchViewInterface;
    }
}
