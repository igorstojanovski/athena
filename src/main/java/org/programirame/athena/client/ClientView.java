package org.programirame.athena.client;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.programirame.athena.models.Client;
import org.programirame.athena.models.ClientType;
import org.programirame.athena.models.Invoice;
import org.programirame.athena.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringView(name = "client")
public class ClientView extends VerticalLayout implements View, ClientViewInterface {

    @Autowired
    List<ClientViewListener> listeners;

    private BeanItemContainer<Invoice> invoicesContainer;
    private Grid invoiceGrid;
    private String clientParameter;
    private ClientInfoSection clientInfo;

    @PostConstruct
    public void init() throws URISyntaxException {
        initializeGrid();
        clientInfo = new ClientInfoSection();

    }

    private void initializeGrid() {
        setSpacing(true);

        invoiceGrid = new Grid();
        invoicesContainer = new BeanItemContainer<>(Invoice.class, new ArrayList<>());
        invoiceGrid.setContainerDataSource(invoicesContainer);
        invoiceGrid.setWidth("100%");
        invoiceGrid.getColumn("client").setConverter(new Converter<String, Client>() {
            @Override
            public Client convertToModel(String s, Class<? extends Client> aClass, Locale locale) throws ConversionException {
                return null;
            }

            @Override
            public String convertToPresentation(Client client, Class<? extends String> aClass, Locale locale) throws ConversionException {
                return client.getName();
            }

            @Override
            public Class<Client> getModelType() {
                return Client.class;
            }

            @Override
            public Class<String> getPresentationType() {
                return String.class;
            }
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        clientParameter = viewChangeEvent.getParameters();
        listeners.forEach(listener -> listener.viewInitialized(this));

        addComponent(clientInfo);
        addComponent(invoiceGrid);
    }

    @Override
    public void refreshInvoices(List<Invoice> invoices) {
        invoicesContainer.removeAllItems();
        invoicesContainer.addAll(invoices);
    }

    @Override
    public String getClientParameter() {
        return clientParameter;
    }

    @Override
    public void refreshClientInfo(Client client) {
        clientInfo.refreshClientInfo(client);
    }
}
