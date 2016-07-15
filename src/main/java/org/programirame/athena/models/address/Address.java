package org.programirame.athena.models.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.programirame.athena.models.Client;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    private long id;
    private Client client;
    private AddressType addressType;
    private String address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
