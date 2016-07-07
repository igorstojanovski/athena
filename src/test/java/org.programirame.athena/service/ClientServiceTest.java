package org.programirame.athena.service;


import com.sun.jndi.toolkit.url.Uri;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;

public class ClientServiceTest {

    ClientService clientService;
    private Uri clientsUrl;
    private RestTemplate restTemplate;

    @Before
    public void init() throws MalformedURLException {
        clientsUrl = new Uri("http://localhost:4348/clients");


    }

    @Test
    public void getInvoicesByClient() {

    }

}
