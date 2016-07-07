package org.programirame.athena.service;

import org.programirame.athena.models.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public List<Client> getAllClients() throws URISyntaxException {

        URI url = new URI(hostUrl + pathClients);
        ResponseEntity<Client[]> response = restTemplate.getForEntity(url, Client[].class);
        Client[] clients = response.getBody();

        return Arrays.asList(clients);
    }
}
