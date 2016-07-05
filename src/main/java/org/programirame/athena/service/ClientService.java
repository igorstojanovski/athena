package org.programirame.athena.service;

import org.programirame.athena.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Service
public class ClientService {

    public List<Client> getAllClients() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        URI url = new URI("http://localhost:4348/clients");

        ResponseEntity<Client[]> response = restTemplate.getForEntity(url, Client[].class);
        System.out.println("Status code: "+response.getStatusCode().toString());
        Client[] clients = response.getBody();

        return Arrays.asList(clients);
    }

}
