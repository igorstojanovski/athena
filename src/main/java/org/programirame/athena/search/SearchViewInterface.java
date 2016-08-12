package org.programirame.athena.search;

import org.programirame.athena.model.Bucket;
import org.programirame.athena.model.Clients;

import java.util.List;

public interface SearchViewInterface {

    void refreshResultsTable(List<Clients> clientList);

    void refreshAggregates(List<Bucket> buckets);
}
