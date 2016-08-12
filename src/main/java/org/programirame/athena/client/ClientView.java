package org.programirame.athena.client;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import org.programirame.athena.model.Clients;
import org.programirame.athena.model.Invoice;
import org.programirame.athena.models.address.Address;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@SpringView(name = "client")
public class ClientView extends VerticalLayout implements View, ClientViewInterface {

    @Autowired
    List<ClientViewListener> listeners;

    private BeanItemContainer<Invoice> invoicesContainer;
    private Grid invoiceGrid;
    private String clientParameter;
    private ClientInfoSection clientInfo;
    private ClientContactSection clientContact;
    private TabSheet clientTabsheet;

    @PostConstruct
    public void init() throws URISyntaxException {
        initializeGrid();
        clientTabsheet = new TabSheet();
        clientTabsheet.setHeight("300px");

        clientInfo = new ClientInfoSection();
        clientContact = new ClientContactSection();
    }

    private void initializeGrid() {
        setSpacing(true);

        invoiceGrid = new Grid();
        invoicesContainer = new BeanItemContainer<>(Invoice.class, new ArrayList<>());
        invoiceGrid.setContainerDataSource(invoicesContainer);
        invoiceGrid.setWidth("100%");
        invoiceGrid.removeColumn("additionalProperties");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        clientParameter = viewChangeEvent.getParameters();
        listeners.forEach(listener -> listener.viewInitialized(this));

        Panel invoicesPanel = new Panel("Invoices", invoiceGrid);

        clientTabsheet.addTab(clientInfo, "Basic Info");
        clientTabsheet.addTab(clientContact, "Contact");

        addComponent(clientTabsheet);
        addComponent(invoicesPanel);
    }

    @Override
    public void refreshInvoices(List<Invoice> invoices) {
        invoicesContainer.removeAllItems();
        invoicesContainer.addAll(invoices);
    }

    @Override
    public void refreshContactInfo(List<Address> addresses) {

        clientContact.refreshContactInfo(addresses);

    }

    @Override
    public String getClientParameter() {
        return clientParameter;
    }

    @Override
    public void refreshClientInfo(Clients client) {
        clientInfo.refreshClientInfo(client);
    }
}
