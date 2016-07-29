package org.programirame.athena.search;

import org.programirame.athena.model.Clients;

import java.util.List;

public interface SearchViewInterface {

    void refreshResultsTable(List<Clients> clientList);

}
