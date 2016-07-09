package org.programirame.athena.service;

import org.programirame.athena.models.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Service
public class ClientService {

    public RestTemplate restTemplate = new RestTemplate();
    @Value("${path.clients}")
    private String pathClients;
    @Value("${url.host}")
    private String hostUrl;

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

    public Client getClient(long clientId) {

        Client client = null;

        try {
            URI url = new URI(hostUrl + pathClients+"/"+clientId);
            ResponseEntity<Client> response = restTemplate.getForEntity(url, Client.class);
            client = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }
}
