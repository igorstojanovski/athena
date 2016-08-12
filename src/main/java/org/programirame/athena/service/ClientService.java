package org.programirame.athena.service;

import org.programirame.athena.model.ClientSearchResult;
import org.programirame.athena.model.Clients;
import org.programirame.athena.model.Invoice;
import org.programirame.athena.model.SearchQuery;
import org.programirame.athena.models.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClientService {

    public RestTemplate restTemplate = new RestTemplate();

    @Value("${path.clients}")
    private String pathClients;

    @Value("${path.search}")
    private String pathSearch;

    @Value("${url.host}")
    private String hostUrl;

    @Value("${url.search}")
    private String searchUrl;

    public List<Client> getAllClients() {
        Client[] clients = new Client[0];

        try {
            URI url = new URI(hostUrl + pathClients);
            ResponseEntity<Client[]> response = restTemplate.getForEntity(url, Client[].class);
            clients = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Arrays.asList(clients);
    }

    public Clients getClient(String clientId) {

        Clients client = null;

        try {
            System.out.println("URL: "+searchUrl + pathSearch + pathClients+"/"+clientId);
            URI url = new URI(searchUrl + pathSearch + pathClients+"/"+clientId);
            ResponseEntity<Clients> response = restTemplate.getForEntity(url, Clients.class);
            client = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }

    public ClientSearchResult searchClients(String searchQuery) {


        ClientSearchResult result = new ClientSearchResult();
        try {
            URI url = new URI(searchUrl + pathSearch + pathClients);
            SearchQuery query = new SearchQuery();
            query.setSearchQuery(searchQuery);

            RequestEntity<SearchQuery> searchQueryRequestEntity = new RequestEntity<>(query, HttpMethod.POST, url);

            ResponseEntity<ClientSearchResult> response = restTemplate.exchange(searchQueryRequestEntity, ClientSearchResult.class);

            result = response.getBody();

            for(Clients client:result.getClients()) {
                List<Invoice> invoices = client.getInvoice();

                for(Invoice invoice:invoices) {
                    invoice.getAdditionalProperties().put("outstanding",
                            invoice.getInvoiceAmount().subtract(invoice.getInvoicePayedAmount()));
                    invoice.getAdditionalProperties().put("email",
                            client.getEmail());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
}
