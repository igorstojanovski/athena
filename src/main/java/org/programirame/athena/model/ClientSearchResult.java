package org.programirame.athena.model;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "clients",
        "buckets"
})
public class ClientSearchResult {

    @JsonProperty("clients")
    private List<Clients> clients = new ArrayList<Clients>();
    @JsonProperty("buckets")
    private List<Bucket> buckets = new ArrayList<Bucket>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public ClientSearchResult() {
    }

    /**
     *
     * @param buckets
     * @param clients
     */
    public ClientSearchResult(List<Clients> clients, List<Bucket> buckets) {
        this.clients = clients;
        this.buckets = buckets;
    }

    /**
     *
     * @return
     * The clients
     */
    @JsonProperty("clients")
    public List<Clients> getClients() {
        return clients;
    }

    /**
     *
     * @param clients
     * The clients
     */
    @JsonProperty("clients")
    public void setClientss(List<Clients> clients) {
        this.clients = clients;
    }

    /**
     *
     * @return
     * The buckets
     */
    @JsonProperty("buckets")
    public List<Bucket> getBuckets() {
        return buckets;
    }

    /**
     *
     * @param buckets
     * The buckets
     */
    @JsonProperty("buckets")
    public void setBuckets(List<Bucket> buckets) {
        this.buckets = buckets;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}