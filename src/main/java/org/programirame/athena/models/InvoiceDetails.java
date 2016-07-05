package org.programirame.athena.models;

import javax.persistence.*;

public class InvoiceDetails {
    private long id;
    private Invoice invoiceId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Invoice getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Invoice invoiceId) {
        this.invoiceId = invoiceId;
    }
}
