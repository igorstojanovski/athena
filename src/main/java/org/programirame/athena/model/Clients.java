package org.programirame.athena.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "externalId",
        "name",
        "surname",
        "taxNumber",
        "uid",
        "invoice",
        "payments",
        "type"
})
public class Clients {

    @JsonProperty("externalId")
    private String externalId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("taxNumber")
    private String taxNumber;
    @JsonProperty("uid")
    private String uid;
    @JsonProperty("invoice")
    private List<Invoice> invoice = new ArrayList<Invoice>();
    @JsonProperty("payments")
    private Object payments;
    @JsonProperty("type")
    private Type type;
    @JsonProperty("email")
    private String email;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The externalId
     */
    @JsonProperty("externalId")
    public String getExternalId() {
        return externalId;
    }

    /**
     *
     * @param externalId
     * The externalId
     */
    @JsonProperty("externalId")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
     *
     * @return
     * The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The surname
     */
    @JsonProperty("surname")
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param surname
     * The surname
     */
    @JsonProperty("surname")
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @return
     * The taxNumber
     */
    @JsonProperty("taxNumber")
    public String getTaxNumber() {
        return taxNumber;
    }

    /**
     *
     * @param taxNumber
     * The taxNumber
     */
    @JsonProperty("taxNumber")
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    /**
     *
     * @return
     * The uid
     */
    @JsonProperty("uid")
    public String getUid() {
        return uid;
    }

    /**
     *
     * @param uid
     * The uid
     */
    @JsonProperty("uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     *
     * @return
     * The invoice
     */
    @JsonProperty("invoice")
    public List<Invoice> getInvoice() {
        return invoice;
    }

    /**
     *
     * @param invoice
     * The invoice
     */
    @JsonProperty("invoice")
    public void setInvoice(List<Invoice> invoice) {
        this.invoice = invoice;
    }

    /**
     *
     * @return
     * The payments
     */
    @JsonProperty("payments")
    public Object getPayments() {
        return payments;
    }

    /**
     *
     * @param payments
     * The payments
     */
    @JsonProperty("payments")
    public void setPayments(Object payments) {
        this.payments = payments;
    }

    /**
     *
     * @return
     * The type
     */
    @JsonProperty("type")
    public Type getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    @JsonProperty("type")
    public void setType(Type type) {
        this.type = type;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
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