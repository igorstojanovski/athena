package org.programirame.athena.search;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.programirame.athena.model.ClientSearchResult;
import org.programirame.athena.model.Clients;
import org.programirame.athena.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UIScope
@SpringComponent
public class SearchViewPresenter implements SearchViewListenerInterface {

    private SearchViewInterface searchViewInterface;

    @Autowired
    ClientService clientService;


    @Override
    public void search(String searchQuery) {

        ClientSearchResult clientSearchResult = clientService.searchClients(searchQuery);
        searchViewInterface.refreshResultsTable(clientSearchResult.getClients());
        searchViewInterface.refreshAggregates(clientSearchResult.getBuckets());
    }

    @Override
    public void viewInitialized(SearchViewInterface searchViewInterface) {
        this.searchViewInterface = searchViewInterface;
    }


}
