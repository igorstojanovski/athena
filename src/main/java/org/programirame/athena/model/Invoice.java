package org.programirame.athena.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
        "invoiceId",
        "invoiceDate",
        "invoiceDueDate",
        "invoiceAmount",
        "invoicePayedAmount"
})
public class Invoice {

    @JsonProperty("invoiceId")
    private String invoiceId;
    @JsonProperty("invoiceDate")
    private Date invoiceDate;
    @JsonProperty("invoiceDueDate")
    private Date invoiceDueDate;
    @JsonProperty("invoiceAmount")
    private BigDecimal invoiceAmount;
    @JsonProperty("invoicePayedAmount")
    private BigDecimal invoicePayedAmount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The invoiceId
     */
    @JsonProperty("invoiceId")
    public String getInvoiceId() {
        return invoiceId;
    }

    /**
     *
     * @param invoiceId
     * The invoiceId
     */
    @JsonProperty("invoiceId")
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     *
     * @return
     * The invoiceDate
     */
    @JsonProperty("invoiceDate")
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    /**
     *
     * @param invoiceDate
     * The invoiceDate
     */
    @JsonProperty("invoiceDate")
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     *
     * @return
     * The invoiceDueDate
     */
    @JsonProperty("invoiceDueDate")
    public Date getInvoiceDueDate() {
        return invoiceDueDate;
    }

    /**
     *
     * @param invoiceDueDate
     * The invoiceDueDate
     */
    @JsonProperty("invoiceDueDate")
    public void setInvoiceDueDate(Date invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
    }

    /**
     *
     * @return
     * The invoiceAmount
     */
    @JsonProperty("invoiceAmount")
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     *
     * @param invoiceAmount
     * The invoiceAmount
     */
    @JsonProperty("invoiceAmount")
    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     *
     * @return
     * The invoicePayedAmount
     */
    @JsonProperty("invoicePayedAmount")
    public BigDecimal getInvoicePayedAmount() {
        return invoicePayedAmount;
    }

    /**
     *
     * @param invoicePayedAmount
     * The invoicePayedAmount
     */
    @JsonProperty("invoicePayedAmount")
    public void setInvoicePayedAmount(BigDecimal invoicePayedAmount) {
        this.invoicePayedAmount = invoicePayedAmount;
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
