package org.programirame.athena.service;


import org.programirame.athena.models.address.Address;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
public class AddressService {

    public RestTemplate restTemplate = new RestTemplate();

    @Value("${path.clients}")
    private String pathClients;
    @Value("${url.host}")
    private String hostUrl;

    public List<Address> getAddresses(long clientId) {
        List<Address> addresses = null;
        try {
            URI url = new URI(hostUrl + pathClients+"/"+clientId+"/addresses");
            ResponseEntity<Address[]> response = restTemplate.getForEntity(url, Address[].class);
            addresses = Arrays.asList(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return addresses;
    }
}
