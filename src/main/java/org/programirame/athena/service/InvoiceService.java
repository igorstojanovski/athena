package org.programirame.athena.service;


import org.programirame.athena.models.Invoice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Service
public class InvoiceService {

    @Value("${path.invoices}")
    private String urlInvoices;

    @Value("${url.host}")
    private String hostUrl;

    public List<Invoice> getAllClientInvoices(long id) throws URISyntaxException {

        System.out.println("URL : "+hostUrl + urlInvoices + "?clientId=" + id);

        URI url = new URI(hostUrl + urlInvoices + "?clientId=" + id);

        RestTemplate restTemplate = new RestTemplate();
        Invoice[] response = restTemplate.getForObject(url, Invoice[].class);

        return Arrays.asList(response);
    }
}
