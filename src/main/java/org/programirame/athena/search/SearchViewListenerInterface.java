package org.programirame.athena.search;


public interface SearchViewListenerInterface {

    void search(String searchQuery);

    void viewInitialized(SearchViewInterface searchViewInterface);
}
