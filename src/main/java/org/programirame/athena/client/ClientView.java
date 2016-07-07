package org.programirame.athena.client;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.programirame.athena.models.Client;
import org.programirame.athena.models.Invoice;
import org.programirame.athena.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringView(name = "client")
public class ClientView extends VerticalLayout implements View, ClientViewInterface {

    @Autowired
    ClientService clientService;

    @Autowired
    List<ClientViewListener> listeners;

    private BeanItemContainer<Invoice> invoicesContainer;
    private Grid invoiceGrid;
    private ComboBox clientCombo;

    @PostConstruct
    public void init() throws URISyntaxException {

        initializeGrid();

        initializeClientCombo();

        addComponent(clientCombo);
        addComponent(invoiceGrid);

    }

    private void initializeClientCombo() {
        clientCombo = new ComboBox("Clients", new ArrayList<>());
        clientCombo.setItemCaptionPropertyId("name");

        clientCombo.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Client client = (Client) valueChangeEvent.getProperty().getValue();
                System.out.println("NUmber of listeners: " + listeners.size());
                for (ClientViewListener listener : listeners) {
                    System.out.println("Notify Listeners!");
                    listener.clientSelected(client);
                }
            }
        });
    }

    private void initializeGrid() {
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
        listeners.forEach(listener -> listener.viewInitialized(this));
    }

    public void addListener(ClientViewListener listener) {
        listeners.add(listener);
    }

    @Override
    public void refreshInvoices(List<Invoice> invoices) {
        invoicesContainer.removeAllItems();
        invoicesContainer.addAll(invoices);
    }

    @Override
    public void refreshClient(List<Client> clients) {
        BeanItemContainer<Client> clientsContainer = new BeanItemContainer<>(Client.class, clients);

        clientCombo.removeAllItems();
        clientCombo.setContainerDataSource(clientsContainer);
    }
}
